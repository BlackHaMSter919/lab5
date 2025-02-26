package managers;

import models.Album;
import models.MusicBand;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * Класс для управления коллекцией музыкальных групп.
 * Предоставляет различные методы для добавления, удаления и изменения групп,
 * а также для получения информации о коллекции.
 */
public class CollectionManager {
    private PriorityQueue<MusicBand> musicBands;
    private ZonedDateTime initializationTime;
    private final XMLManager xmlManager;

    /**
     * Конструктор класса.
     * Инициализирует менеджер коллекции, загружая данные из файла.
     *
     * @param fileName путь к файлу, из которого загружается коллекция.
     */
    public CollectionManager(String fileName) {
        this.xmlManager = new XMLManager(fileName);
        this.musicBands = xmlManager.loadFromFile();
        this.initializationTime = ZonedDateTime.now();
    }

    /**
     * Получает информацию о коллекции.
     *
     * @return строковое представление информации о коллекции.
     */
    public String getInfo() {
        return String.format("Тип: %s\nДата инициализации: %s\nКоличество элементов: %d",
                musicBands.getClass().getSimpleName(), initializationTime, musicBands.size());
    }

    /**
     * Добавляет музыкальную группу в коллекцию.
     *
     * @param band музыкальная группа, которую нужно добавить.
     */
    public void add(MusicBand band) {
        musicBands.add(band);
    }

    /**
     * Обновляет существующую музыкальную группу в коллекции по её ID.
     *
     * @param id ID музыкальной группы, которую нужно обновить.
     * @param newBand новая музыкальная группа.
     * @return true, если группа была успешно обновлена, иначе false.
     */
    public boolean update(int id, MusicBand newBand) {
        Optional<MusicBand> existingBand = getById(id);
        if (existingBand.isPresent()) {
            musicBands.remove(existingBand.get());
            musicBands.add(newBand);
            return true;
        }
        return false;
    }

    /**
     * Удаляет музыкальную группу из коллекции по её ID.
     *
     * @param id ID музыкальной группы, которую нужно удалить.
     * @return true, если группа была успешно удалена, иначе false.
     */
    public boolean removeById(int id) {
        Optional<MusicBand> band = getById(id);
        if (band.isPresent()) {
            musicBands.remove(band.get());
            return true;
        }
        System.out.println("Группа с таким ID не найдена.");
        return false;
    }

    /**
     * Очищает коллекцию, удаляя все элементы.
     */
    public void clear() {
        musicBands.clear();
    }

    /**
     * Удаляет и возвращает первый элемент из коллекции.
     *
     * @return первый элемент из коллекции, если коллекция не пуста.
     */
    public Optional<MusicBand> removeHead() {
        return Optional.ofNullable(musicBands.poll());
    }

    /**
     * Удаляет первый элемент из коллекции, если она не пуста.
     *
     * @return true, если элемент был удалён, иначе false.
     */
    public boolean removeFirst() {
        return musicBands.poll() != null;
    }

    /**
     * Добавляет музыкальную группу в коллекцию, если её количество альбомов меньше, чем у минимальной группы.
     *
     * @param band музыкальная группа, которую нужно добавить.
     * @return true, если группа была добавлена, иначе false.
     */
    public boolean addIfMin(MusicBand band) {
        if (musicBands.isEmpty() || band.compareTo(musicBands.peek()) < 0) {
            musicBands.add(band);
            return true;
        }
        return false;
    }

    /**
     * Находит музыкальную группу с максимальным количеством альбомов.
     *
     * @return группа с максимальным количеством альбомов, если коллекция не пуста.
     */
    public Optional<MusicBand> maxByAlbumsCount() {
        return musicBands.stream()
                .max(Comparator.comparingInt(b -> Optional.ofNullable(b.getAlbumsCount()).orElse(0)));
    }


    /**
     * Подсчитывает количество музыкальных групп с альбомами, в которых количество треков меньше, чем в переданном альбоме.
     *
     * @param bestAlbum альбом, с которым будет произведено сравнение.
     * @return количество групп, чьи альбомы имеют меньшее количество треков.
     */
    public long countLessThanBestAlbum(Album bestAlbum) {
        return musicBands.stream()
                .filter(b -> b.getBestAlbum().getTracks() < bestAlbum.getTracks())
                .count();
    }

    /**
     * Выводит количество участников музыкальных групп в коллекции в порядке возрастания.
     */
    public void printFieldAscendingNumberOfParticipants() {
        musicBands.stream()
                .map(MusicBand::getNumberOfParticipants)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * Показывает все элементы коллекции.
     */
    public void show() {
        if (musicBands.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            musicBands.forEach(System.out::println);
        }
    }

    /**
     * Находит музыкальную группу по её ID.
     *
     * @param id ID музыкальной группы.
     * @return музыкальная группа с указанным ID.
     */
    private Optional<MusicBand> getById(int id) {
        return musicBands.stream().filter(band -> band.getId() == id).findFirst();
    }

    /**
     * Сохраняет текущую коллекцию в файл.
     */
    public void save() {
        xmlManager.saveToFile(musicBands);
    }
}