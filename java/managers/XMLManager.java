package managers;

import models.MusicBand;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.PriorityQueue;

/**
 * Класс, отвечающий за сохранение и загрузку коллекции объектов {@link MusicBand} в/из XML файла.
 * Использует JAXB для маршалинга и анмаршалинга коллекции.
 */
public class XMLManager {
    private final String fileName;

    /**
     * Конструктор класса XMLManager.
     * @param fileName имя файла, с которым будет работать менеджер.
     */
    public XMLManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Сохраняет коллекцию {@link MusicBand} в XML файл.
     * Использует JAXB для преобразования коллекции в XML формат и записи в файл.
     * @param collection коллекция объектов {@link MusicBand}, которую необходимо сохранить.
     */
    public void saveToFile(PriorityQueue<MusicBand> collection) {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.setMusicBands(collection);

            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))) {
                marshaller.marshal(wrapper, bos);
            }
            System.out.println("Коллекция успешно сохранена.");
        } catch (JAXBException | IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    /**
     * Загружает коллекцию {@link MusicBand} из XML файла.
     * Если файл не существует или пуст, создается новая коллекция.
     * @return коллекция объектов {@link MusicBand}, загруженная из файла.
     */
    public PriorityQueue<MusicBand> loadFromFile() {
        PriorityQueue<MusicBand> collection = new PriorityQueue<>();
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            System.out.println("Файл не найден или пуст. Создаётся новая коллекция.");
            return collection;
        }

        try {
            JAXBContext context = JAXBContext.newInstance(CollectionWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                CollectionWrapper wrapper = (CollectionWrapper) unmarshaller.unmarshal(br);
                collection.addAll(wrapper.getMusicBands());
                System.out.println("Коллекция успешно загружена.");
            }
        } catch (JAXBException | IOException e) {
            System.err.println("Ошибка при загрузке файла: " + e.getMessage());
        }

        return collection;
    }
}