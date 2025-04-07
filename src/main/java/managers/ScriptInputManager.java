package managers;

import commands.*;
import models.Album;
import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.Map;

import static commands.ExecuteScript.originalOut;

/**
 * Класс, отвечающий за правильную обработку скриптов.
 */
public class ScriptInputManager {
    public static final String OUTPUT_FILE = "C:\\itmo\\s368999\\lab_5\\src\\main\\java\\Main\\ScriptOutput.txt";
    public static final Map<String, Command> commands = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("remove_first", new RemoveFirst()),
            new AbstractMap.SimpleEntry<>("help", new Help()),
            new AbstractMap.SimpleEntry<>("remove_by_id", new RemoveById()),
            new AbstractMap.SimpleEntry<>("remove_head", new RemoveHead()),
            new AbstractMap.SimpleEntry<>("max_by_albums_count", new MaxByAlbumsCount()),
            new AbstractMap.SimpleEntry<>("count_less_than_best_album", new CountLessThanBestAlbum()),
            new AbstractMap.SimpleEntry<>("print_field_ascending_number_of_participants", new PrintFieldAscendingNumberOfParticipants()),
            new AbstractMap.SimpleEntry<>("info", new Info()),
            new AbstractMap.SimpleEntry<>("show", new Show()),
            new AbstractMap.SimpleEntry<>("clear", new Clear()),
            new AbstractMap.SimpleEntry<>("save", new Save()),
            new AbstractMap.SimpleEntry<>("execute_script", new ExecuteScript()),
            new AbstractMap.SimpleEntry<>("exit", new Exit()),
            new AbstractMap.SimpleEntry<>("add", new Add()),
            new AbstractMap.SimpleEntry<>("add_if_min", new AddIfMin())

    );
    public static MusicBand parseMusicBand(String args) {
        try {

            String[] parts = args.split(" ");
            if (parts.length != 10) return null;
            String name = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            Integer numberOfParticipants = Integer.parseInt(parts[3]);
            Integer albumsCount = Integer.parseInt(parts[4]);
            String description = parts[5];
            MusicGenre genre = MusicGenre.valueOf(parts[6].toUpperCase());
            String albumName = parts[7];
            int sales = Integer.parseInt(parts[8]);
            int tracks = Integer.parseInt(parts[9]);
            Coordinates coordinates = new Coordinates(x, y);
            Album bestAlbum = new Album(albumName, sales, tracks);
            return new MusicBand(name, coordinates, numberOfParticipants, albumsCount, description, genre, bestAlbum);
        } catch (Exception e) {
            return null;
        }
    }

    public static void clearOutputFile() {
        try (PrintWriter writer = new PrintWriter(OUTPUT_FILE)) {
            writer.print("");
        } catch (IOException ignored) {
        }
    }

    public static void redirectOutputToFile() {
        try {
            if (originalOut == null) {
                originalOut = System.out;
            }
            PrintStream fileOut = new PrintStream(new FileOutputStream(OUTPUT_FILE, true));
            System.setOut(fileOut);
        } catch (IOException ignored) {
        }
    }

    public static void restoreOutput() {
        if (originalOut != null) {
            System.setOut(originalOut);
        }
    }

}

    //execute_script C:\itmo\s368999\lab_5\src\main\java\Main\Script.txt