package commands;

import javax.xml.bind.annotation.XmlRootElement;

import static managers.MusicBandManager.bands;
/**
 * Класс, yдаляющий и отображающий первый элемент коллекции.
 */
@XmlRootElement(name = "RemoveHead")
public class RemoveHead implements Command{
    /**
     * Удаляет и отображает первый элемент коллекции.
     *
     * @return
     */

    @Override
    public void execute(String args) {
        if (!bands.isEmpty()) {
            System.out.println("Удаленный элемент: " + bands.poll());
        } else {
            System.out.println("Коллекция пуста.");
        }
    }
}
