package commands;

import static managers.MusicBandManager.bands;

public class Show implements Command{
    /**
     * Отображает все группы из коллекции.
     *
     * @return
     */
    @Override
    public void execute(String args) {
        if (bands.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            bands.forEach(System.out::println);
        }
    }
}
