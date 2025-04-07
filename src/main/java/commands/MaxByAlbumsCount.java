package commands;

import models.MusicBand;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Comparator;
import java.util.Optional;

import static managers.MusicBandManager.bands;
/**
 * Класс, отображающий группу с макс кол-вом альбомов.
 */
@XmlRootElement(name = "MaxByAlbumCount")
public class MaxByAlbumsCount implements Command{
    /**
     * Отображает группу с максимальным количеством альбомов.
     *
     * @return
     */

    @Override
    public void execute(String args){
        boolean seen = false;
        MusicBand best = null;
        Comparator<MusicBand> comparator = Comparator.comparing(band1 -> band1.getAlbumsCount());
        for (MusicBand band : bands) {
            if (!seen || comparator.compare(band, best) > 0) {
                seen = true;
                best = band;
            }
        }
        Optional<MusicBand> maxBand = seen ? Optional.of(best) : Optional.empty();
        maxBand.ifPresentOrElse(System.out::println, () -> System.out.println("Коллекция пуста."));
    }
}
