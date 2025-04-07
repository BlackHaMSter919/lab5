package managers;

import models.MusicBand;
import models.MusicBandCollectionWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.PriorityQueue;

import static managers.MusicBandManager.*;


/**
 * Класс, отвечающий за сохранение и загрузку коллекции объектов {@link MusicBand} в/из XML файла.
 * Использует JAXB для маршалинга и анмаршалинга коллекции.
 */
public class XMLManager {


    /**
     * Конструктор класса XMLManager.
     * @param fileName имя файла, с которым будет работать менеджер.
     */
    public XMLManager(String fileName) {
    }
    /**
     * Загружает коллекцию музыкальных групп из файла.
     */
    public static void loadFromFile() {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) return;

            JAXBContext context = JAXBContext.newInstance(MusicBandCollectionWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MusicBandCollectionWrapper wrapper = (MusicBandCollectionWrapper) unmarshaller.unmarshal(file);

            bands = new PriorityQueue<>(wrapper.getBands());
            bandsById.clear();
            for (MusicBand band : bands) {
                bandsById.put(band.getId(), band);
            }

            System.out.println("Коллекция загружена из файла.");
        } catch (Exception e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }
}