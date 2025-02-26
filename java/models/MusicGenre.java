package models;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Перечисление музыкальных жанров.
 * Используется для хранения жанра музыкальной группы.
 */
@XmlEnum
@XmlType(name = "MusicGenre")
public enum MusicGenre {
    /** Прогрессивный рок */
    PROGRESSIVE_ROCK,

    /** Хип-хоп */
    HIP_HOP,

    /** Блюз */
    BLUES;
}