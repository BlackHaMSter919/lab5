package commands;

import models.Album;
import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;
import static managers.MusicBandManager.*;

public class AddIfMin implements Command {

    /**
     * Добавляет группу, если она имеет минимальное количество альбомов.
     * @return
     */
    @Override
    public void execute(String args) {
        System.out.println("\n=== Добавление группы с минимальным количеством альбомов ===");


        int minAlbums = Integer.MAX_VALUE;
        for (MusicBand musicBand : bands) {
            int count = musicBand.getAlbumsCount();
            if (count < minAlbums) {
                minAlbums = count;
            }
        }

        String name = getInputString("Введите название группы: ", ERROR_EMPTY_NAME);
        Coordinates coordinates = getCoordinates();
        Integer numberOfParticipants = getInputIntForNullableField("Введите количество участников: ", ERROR_INVALID_NUMBER);
        Integer albumsCount = getInputIntForNullableField("Введите количество альбомов: ", ERROR_INVALID_NUMBER);
        String description = getInputString("Введите описание группы: ", ERROR_EMPTY_NAME);
        MusicGenre genre = getMusicGenre();
        Album bestAlbum = getAlbumDetails();

        if (albumsCount < minAlbums) {
            MusicBand band = new MusicBand(name, coordinates, numberOfParticipants, albumsCount, description, genre, bestAlbum);
            bands.add(band);
            bandsById.put(band.getId(), band);
            System.out.println(SUCCESS_BAND_ADDED);
        } else {
            System.out.println("Группа не добавлена, так как не имеет минимальное количество альбомов.");
        }
    }
}
