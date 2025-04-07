package models;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, представляющий музыкальную группу.
 * Реализует интерфейс {@link Comparable} для сравнения групп по их ID.
 */
@XmlRootElement(name = "MusicBand")
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicBand implements Comparable<MusicBand> {

    /** Счетчик для автоматической генерации уникальных ID. */
    @XmlTransient
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    /** Уникальный идентификатор группы. */
    @XmlElement
    private int id;

    /** Название группы. */
    @XmlElement(required = true)
    private String name;

    /** Координаты группы. */
    @XmlElement
    private Coordinates coordinates;

    /** Дата создания группы. */
    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private ZonedDateTime creationDate;

    /** Количество участников группы. Может быть null. */
    @XmlElement
    private Integer numberOfParticipants;

    /** Количество выпущенных альбомов. Может быть null. */
    @XmlElement
    private Integer albumsCount;

    /** Описание группы. */
    @XmlElement(required = true)
    private String description;

    /** Музыкальный жанр группы. */
    @XmlElement(required = true)
    private MusicGenre genre;

    /** Лучший альбом группы. */
    @XmlElement(required = true)
    private Album bestAlbum;

    /**
     * Пустой конструктор для JAXB.
     * Автоматически присваивает ID и устанавливает текущую дату создания.
     */
    public MusicBand() {
        this.id = idCounter.getAndIncrement();
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Создает новый объект {@code MusicBand} с указанными параметрами.
     *
     * @param name               название группы (не может быть пустым или null)
     * @param coordinates        координаты группы (не могут быть null)
     * @param numberOfParticipants количество участников (должно быть больше 0, если не null)
     * @param albumsCount        количество альбомов (должно быть больше 0, если не null)
     * @param description        описание группы (не может быть null)
     * @param genre              жанр группы (не может быть null)
     * @param bestAlbum          лучший альбом (не может быть null)
     * @throws IllegalArgumentException если переданы некорректные данные
     */
    public MusicBand(String name, Coordinates coordinates, Integer numberOfParticipants,
                     Integer albumsCount, String description, MusicGenre genre, Album bestAlbum) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Имя не может быть пустым!");
        if (coordinates == null) throw new IllegalArgumentException("Координаты не могут быть null!");
        if (description == null) throw new IllegalArgumentException("Описание не может быть null!");
        if (genre == null) throw new IllegalArgumentException("Жанр не может быть null!");
        if (bestAlbum == null) throw new IllegalArgumentException("Лучший альбом не может быть null!");
        if (numberOfParticipants != null && numberOfParticipants <= 0)
            throw new IllegalArgumentException("Число участников должно быть > 0!");
        if (albumsCount != null && albumsCount <= 0)
            throw new IllegalArgumentException("Число альбомов должно быть > 0!");

        this.id = idCounter.getAndIncrement();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.description = description;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public ZonedDateTime getCreationDate() { return creationDate; }
    public Integer getNumberOfParticipants() { return numberOfParticipants; }
    public Integer getAlbumsCount() { return albumsCount; }
    public String getDescription() { return description; }
    public MusicGenre getGenre() { return genre; }
    public Album getBestAlbum() { return bestAlbum; }

    /**
     * Сравнивает текущую группу с другой по их уникальному идентификатору.
     *
     * @param other другая музыкальная группа для сравнения
     * @return отрицательное число, ноль или положительное число, если текущий объект меньше, равен или больше {@code other}
     */
    @Override
    public int compareTo(MusicBand other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Возвращает строковое представление музыкальной группы.
     *
     * @return строка с основными характеристиками группы
     */
    @Override
    public String toString() {
        return String.format(
                "MusicBand{id=%d, name='%s', genre=%s, albums=%d, participants=%d, " +
                        "coordinates=%s, creationDate=%s, description='%s', bestAlbum=%s}",
                id,
                name,
                genre,
                albumsCount,
                numberOfParticipants,
                coordinates,
                creationDate,
                description,
                bestAlbum
        );
    }
}