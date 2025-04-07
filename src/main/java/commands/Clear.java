package commands;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.bandsById;
public class Clear implements Command{
    /**
     * Очищает коллекцию групп.
     *
     * @return
     */
    @Override
    public void execute(String args) {
        bands.clear();
        bandsById.clear();
        System.out.println("Коллекция очищена.");
    }
}
