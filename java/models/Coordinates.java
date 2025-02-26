package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, представляющий координаты музыкальной группы.
 */
@XmlRootElement(name = "Coordinates")
public class Coordinates {

    /** Координата X (не может быть больше 406). */
    @XmlElement
    private float x;

    /** Координата Y. */
    @XmlElement
    private float y;

    /** Пустой конструктор для JAXB. */
    public Coordinates() {}

    /**
     * Создает объект {@code Coordinates} с указанными значениями.
     *
     * @param x координата X (должна быть ≤ 406)
     * @param y координата Y
     * @throws IllegalArgumentException если x > 406
     */
    public Coordinates(float x, float y) {
        if (x > 406) throw new IllegalArgumentException("Координата x не может быть > 406!");
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    /**
     * Возвращает строковое представление координат.
     *
     * @return строка в формате "(x, y)"
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}