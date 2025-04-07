package commands;

import models.MusicBand;

import javax.xml.bind.annotation.XmlRootElement;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.bandsById;
/**
 * Класс, yдаляющий группу из коллекции по её ID.
 */
@XmlRootElement(name = "RemoveById")
public class RemoveById implements Command {
    /**
     * Удаляет группу из коллекции по её ID.
     *
     * @return
     */

    @Override
    public void execute(String args) {
        try {
            int id = Integer.parseInt(args);


            MusicBand band = bandsById.remove(id);
            if (band != null) {
                bands.remove(band);
                System.out.println("Группа удалена.");
            } else {
                System.out.println("Группа с таким ID не найдена.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Пожалуйста, введите числовой ID.");
        }
    }
}
