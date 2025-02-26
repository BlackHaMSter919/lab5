package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Обертка для коллекции музыкальных групп, используемая для сериализации в XML.
 */
@XmlRootElement(name = "MusicBandCollectionWrapper")
public class MusicBandCollectionWrapper {
    /** Коллекция музыкальных групп, хранящаяся в виде приоритетной очереди. */
    private Queue<MusicBand> bands = new PriorityQueue<>();

    /**
     * Возвращает коллекцию музыкальных групп.
     * @return коллекция музыкальных групп.
     */
    @XmlElement(name = "MusicBand")
    public Queue<MusicBand> getBands() {
        return bands;
    }

    /**
     * Устанавливает коллекцию музыкальных групп.
     * @param bands новая коллекция музыкальных групп.
     */
    public void setBands(Queue<MusicBand> bands) {
        this.bands = bands;
    }
}