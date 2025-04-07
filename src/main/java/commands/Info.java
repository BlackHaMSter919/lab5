package commands;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.initializationDate;

public class Info implements Command{
    /**
     * Отображает информацию о коллекции (тип, дата инициализации, количество элементов).
     *
     * @return
     */
    @Override
    public void execute(String args) {
        System.out.println("Тип коллекции: " + bands.getClass().getSimpleName());
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + bands.size());
    }
}
