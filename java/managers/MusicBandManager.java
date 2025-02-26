package managers;

import models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, управляющий коллекцией музыкальных групп {@link MusicBand}.
 * Обеспечивает возможность добавления, удаления, отображения и сохранения/загрузки данных в файл.
 */
public class MusicBandManager {
    private static final Scanner scanner = new Scanner(System.in);
    public static MusicBandManager manager;
    private Queue<MusicBand> bands;
    private Map<Integer, MusicBand> bandsById;
    private final String filePath;
    private ZonedDateTime initializationDate;

    private static final String ERROR_EMPTY_NAME = "Ошибка! Название не может быть пустым.";
    private static final String ERROR_INVALID_NUMBER = "Ошибка! Введите целое число.";
    private static final String ERROR_INVALID_FLOAT = "Ошибка! Введите число с плавающей запятой.";
    private static final String SUCCESS_BAND_ADDED = "Группа успешно добавлена в коллекцию!\n";

    /**
     * Инициализирует менеджер музыкальных групп, если он ещё не был инициализирован.
     * @param filePath путь к файлу, в который/из которого будет происходить загрузка/сохранение данных.
     */
    public static void initializeManager(String filePath) {
        if (manager == null) {
            manager = new MusicBandManager(filePath);
        }
    }

    /**
     * Конструктор класса MusicBandManager.
     * @param filePath путь к файлу для сохранения и загрузки коллекции.
     */
    public MusicBandManager(String filePath) {
        this.filePath = filePath;
        this.bands = new PriorityQueue<>();
        this.bandsById = new HashMap<>();
        this.initializationDate = ZonedDateTime.now();
        loadFromFile();
    }

    /**
     * Добавляет новую музыкальную группу в коллекцию.
     * @param suppressOutput если true, сообщение о добавлении группы не будет выведено.
     * @return объект {@link MusicBand} добавленной группы.
     */
    public static MusicBand addBand(boolean suppressOutput) {
        System.out.println("\n=== Добавление новой музыкальной группы ===");
        String name = getInputString("Введите название группы: ", ERROR_EMPTY_NAME);
        Coordinates coordinates = getCoordinates();
        Integer numberOfParticipants = getInputIntForNullableField("Введите количество участников: ", ERROR_INVALID_NUMBER);
        Integer albumsCount = getInputIntForNullableField("Введите количество альбомов: ", ERROR_INVALID_NUMBER);
        String description = getInputString("Введите описание группы: ", ERROR_EMPTY_NAME);
        MusicGenre genre = getMusicGenre();
        Album bestAlbum = getAlbumDetails();
        MusicBand band = new MusicBand(name, coordinates, numberOfParticipants, albumsCount, description, genre, bestAlbum);
        manager.bands.add(band);
        manager.bandsById.put(band.getId(), band);
        if (!suppressOutput) {
            System.out.println(SUCCESS_BAND_ADDED);
        }
        return band;
    }

    /**
     * Запрашивает ввод строки у пользователя.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если ввод пуст.
     * @return введенная строка.
     */
    private static String getInputString(String prompt, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) break;
            System.out.println(errorMessage);
        }
        return input;
    }

    /**
     * Запрашивает ввод целого числа у пользователя.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если введено нецелое число.

    Чат для кода, [22.02.2025 21:00]
     * @return введённое целое число.
     */
    private static Integer getInputIntForNullableField(String prompt, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    private static int getInputInt(String prompt, String errorMessage) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input > 0) break;
                System.out.println("Ошибка! Число должно быть больше 0.");
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    /**
     * Запрашивает ввод числа с плавающей запятой.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если введено нечисловое значение.
     * @return введённое число с плавающей запятой.
     */
    private static float getInputFloat(String prompt, String errorMessage) {
        float input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Float.parseFloat(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    /**
     * Запрашивает ввод координат группы.
     * @return объект {@link Coordinates} с введенными значениями.
     */
    private static Coordinates getCoordinates() {
        int x = getInputInt("Введите координату X (целое число): ", ERROR_INVALID_NUMBER);
        int y = getInputInt("Введите координату Y (целое число): ", ERROR_INVALID_NUMBER);
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает выбор жанра музыки у пользователя.
     * @return выбранный жанр {@link MusicGenre}.
     */
    private static MusicGenre getMusicGenre() {
        MusicGenre genre;
        while (true) {
            System.out.println("Выберите жанр музыки:");
            for (MusicGenre g : MusicGenre.values()) {
                System.out.println("- " + g);
            }
            System.out.print("Введите жанр (как указано выше): ");
            try {
                genre = MusicGenre.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка! Введите один из доступных жанров.");
            }
        }
        return genre;
    }

    /**
     * Запрашивает данные об альбоме у пользователя.
     * @return объект {@link Album} с введёнными данными.
     */
    private static Album getAlbumDetails() {
        String name = getInputString("Введите название альбома: ", ERROR_EMPTY_NAME);
        float sales = getInputFloat("Введите скидку альбома: ", ERROR_INVALID_FLOAT);
        int tracks = getInputInt("Введите количество треков в альбоме: ", ERROR_INVALID_NUMBER);
        return new Album(name, sales, tracks);
    }

    /**
     * Загружает коллекцию музыкальных групп из файла.
     */
    private void loadFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) return;

            JAXBContext context = JAXBContext.newInstance(MusicBandCollectionWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MusicBandCollectionWrapper wrapper = (MusicBandCollectionWrapper) unmarshaller.unmarshal(file);

            this.bands = new PriorityQueue<>(wrapper.getBands());
            this.bandsById.clear();
            for (MusicBand band : bands) {
                bandsById.put(band.getId(), band);
            }

            System.out.println("Коллекция загружена из файла.");
        } catch (Exception e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }

    /**
     * Сохраняет текущую коллекцию музыкальных групп в файл.

     Чат для кода, [22.02.2025 21:00]
     */
    public void saveToFile() {
        try {
            JAXBContext context = JAXBContext.newInstance(MusicBandCollectionWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            MusicBandCollectionWrapper wrapper = new MusicBandCollectionWrapper();
            wrapper.setBands(bands);

            marshaller.marshal(wrapper, new File(filePath));
        } catch (Exception e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    /**
     * Удаляет группу из коллекции по её ID.
     * @param id идентификатор группы.
     * @return true, если группа была удалена, иначе false.
     */
    public boolean removeById(int id) {
        MusicBand band = bandsById.remove(id);
        if (band != null) {
            bands.remove(band);
            System.out.println("Группа удалена.");
            return true;
        }
        System.out.println("Группа с таким ID не найдена.");
        return false;
    }

    /**
     * Отображает все группы из коллекции.
     */
    public void showAll() {
        if (bands.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            bands.forEach(System.out::println);
        }
    }

    /**
     * Очищает коллекцию групп.
     */
    public void clear() {
        bands.clear();
        bandsById.clear();
        System.out.println("Коллекция очищена.");
    }

    /**
     * Отображает информацию о коллекции (тип, дата инициализации, количество элементов).
     */
    public void info() {
        System.out.println("Тип коллекции: " + bands.getClass().getSimpleName());
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + bands.size());
    }

    /**
     * Удаляет первый элемент коллекции.
     */
    public void removeFirst() {
        if (!bands.isEmpty()) {
            bands.poll();
            System.out.println("Первый элемент удален.");
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    /**
     * Удаляет и отображает первый элемент коллекции.
     */
    public void removeHead() {
        if (!bands.isEmpty()) {
            System.out.println("Удаленный элемент: " + bands.poll());
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    /**
     * Добавляет группу, если она имеет минимальное количество альбомов.
     */
    public void addIfMin() {
        int minAlbums = bands.stream()
                .mapToInt(MusicBand::getAlbumsCount)
                .min()
                .orElse(Integer.MAX_VALUE);
        MusicBand newBand = addBand(true);
        if (newBand.getAlbumsCount() < minAlbums) {
            System.out.println(SUCCESS_BAND_ADDED);
        } else {
            bands.remove(newBand);
            saveToFile();
            System.out.println("Группа не добавлена, так как не является минимальной.");
        }
    }

    /**
     * Отображает группу с максимальным количеством альбомов.
     */
    public void maxByAlbumsCount() {
        Optional<MusicBand> maxBand = bands.stream().max(Comparator.comparing(MusicBand::getAlbumsCount));
        maxBand.ifPresentOrElse(System.out::println, () -> System.out.println("Коллекция пуста."));
    }

    /**
     * Выводит количество групп с альбомами, у которых количество треков или продажи меньше заданного значения.
     */
    public void countLessThanBestAlbum() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите параметр сравнения: \n1 - Количество треков (tracks) \n2 - Продажи альбома (sales)");
        int choice = scanner.nextInt();

        if (choice != 1 && choice != 2) {
            System.out.println("Неверный ввод. Выберите 1 или 2.");
            return;
        }

        System.out.println("Введите число для сравнения:");
        int userValue = scanner.nextInt();

        long count = bands.stream()
                .filter(band -> band.getBestAlbum() != null &&
                        (choice == 1 ? band.getBestAlbum().getTracks() : band.getBestAlbum().getSales()) < userValue)
                .count();

        System.out.println("Количество элементов с bestAlbum < " + userValue + ": " + count);
    }

    /**
     * Выводит количество участников в группах в порядке возрастания.
     */
    public void printFieldAscendingNumberOfParticipants() {
        List<Integer> sortedParticipants = bands.stream()
                .map(MusicBand::getNumberOfParticipants)
                .sorted()
                .collect(Collectors.toList());
        if (sortedParticipants.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            sortedParticipants.forEach(System.out::println);
        }
    }
}