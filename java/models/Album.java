package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, представляющий музыкальный альбом.
 */
@XmlRootElement(name = "Album")
public class Album {

    /** Название альбома (не может быть {@code null} или пустым). */
    @XmlElement
    private String name;

    /** Количество продаж альбома (должно быть больше 0). */
    @XmlElement
    private float sales;

    /** Количество треков в альбоме (должно быть больше 0). */
    @XmlElement
    private int tracks;

    /** Пустой конструктор для JAXB. */
    public Album() {}

    /**
     * Создает объект {@code Album} с указанными параметрами.
     *
     * @param name   название альбома (не может быть пустым)
     * @param sales  количество продаж (должно быть > 0)
     * @param tracks количество треков (должно быть > 0)
     * @throws IllegalArgumentException если параметры не соответствуют ограничениям
     */
    public Album(String name, float sales, int tracks) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Имя альбома не может быть пустым!");
        if (sales <= 0) throw new IllegalArgumentException("Продажи должны быть > 0!");
        if (tracks <= 0) throw new IllegalArgumentException("Количество треков должно быть > 0!");

        this.name = name;
        this.sales = sales;
        this.tracks = tracks;
    }

    public String getName() { return name; }
    public int getTracks() { return tracks; }
    public float getSales() { return sales; }

    /**
     * Возвращает строковое представление альбома.
     *
     * @return строка в формате "{name='...', sales=..., tracks=...}"
     */
    @Override
    public String toString() {
        return String.format("{name='%s', sales=%.2f, tracks=%d}",
                name,
                sales,
                tracks
        );
    }
}