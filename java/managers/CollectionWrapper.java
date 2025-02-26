package managers;

import models.MusicBand;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.PriorityQueue;

/**
 * Класс-обёртка для коллекции музыкальных групп.
 * Содержит коллекцию в виде приоритетной очереди и предоставляет методы для получения и установки коллекции.
 */
@XmlRootElement(name = "CollectionWrapper")
public class CollectionWrapper {

    @XmlElement(name = "MusicBand")
    private PriorityQueue<MusicBand> musicBands = new PriorityQueue<>();

    /**
     * Получает коллекцию музыкальных групп.
     * @return коллекция музыкальных групп в виде приоритетной очереди.
     */
    public PriorityQueue<MusicBand> getMusicBands() {
        return musicBands;
    }

    /**
     * Устанавливает новую коллекцию музыкальных групп.
     * @param musicBands новая коллекция музыкальных групп.
     */
    public void setMusicBands(PriorityQueue<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }
}