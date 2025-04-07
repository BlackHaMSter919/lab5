package commands;

import javax.xml.bind.annotation.XmlRootElement;

import static managers.MusicBandManager.bands;
/**
 * Класс, yдаляющий первый элемент коллекции.
 */
@XmlRootElement(name = "RemoveFirst")
public class RemoveFirst implements Command{
    /**
     * Удаляет первый элемент коллекции.
     *
     * @return
     */

    @Override
    public void execute(String args) {

        if (!bands.isEmpty()) {
            bands.poll();
            System.out.println("Первый элемент удален.");
        } else {
            System.out.println("Коллекция пуста.");
        }
    }
}
