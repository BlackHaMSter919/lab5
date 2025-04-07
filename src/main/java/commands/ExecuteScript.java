package commands;

import models.MusicBand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.bandsById;
import static managers.ScriptInputManager.*;

/***
 * Класс для выполнения скриптов с командами.
 */
public class ExecuteScript implements Command {
    private static final HashSet<String> executingScripts = new HashSet<>();
    public static PrintStream originalOut;
    private static int scriptDepth = 0; // Глубина вложенности

    /**
     * Выполняет скрипт по указанному пути.
     *
     * @param filePath Путь к файлу скрипта.
     */
    @Override
    public void execute(String filePath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            System.out.println("Ошибка: файл не найден.");
            return;
        }

        if (executingScripts.contains(filePath)) {
            System.out.println("Ошибка: рекурсивный вызов скрипта \"" + filePath + "\" запрещен.");
            return;
        }

        executingScripts.add(filePath);
        scriptDepth++;

        if (scriptDepth == 1) {
            System.out.println("Скрипт запущен.");
        }

        clearOutputFile();
        boolean hasError = false;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] commandParts = line.split(" ", 2);
                String cmd = commandParts[0].toLowerCase();

                if (!commands.containsKey(cmd)) {
                    System.out.println("Ошибка: неизвестная команда \"" + cmd + "\".");
                    hasError = true;
                    break;
                }

                if (cmd.equals("execute_script")) {
                    String newScriptPath = commandParts.length > 1 ? commandParts[1] : "";
                    if (newScriptPath.isEmpty()) {
                        System.out.println("Ошибка: не указан путь к скрипту.");
                        hasError = true;
                        break;
                    }
                    if (executingScripts.contains(newScriptPath)) {
                        System.out.println("Ошибка: рекурсивный вызов скрипта \"" + newScriptPath + "\" запрещен.");
                        hasError = true;
                        break;
                    }
                }

                if (cmd.equals("add") || cmd.equals("add_if_min")) {
                    if (commandParts.length != 2) {
                        System.out.println("Ошибка: команда " + cmd + " требует ровно 1 аргумент.");
                        hasError = true;
                        break;
                    }
                    MusicBand band = parseMusicBand(commandParts[1]);
                    if (band == null) {
                        System.out.println("Ошибка: неверные данные для " + cmd + ".");
                        hasError = true;
                        break;
                    }
                }

                if (cmd.equals("count_less_than_best_album")) {
                    String[] parts = commandParts[1].split(" ");
                    if (parts.length != 2) {
                        System.out.println("Ошибка: команда count_less_than_best_album требует ровно 2 аргумента.");
                        hasError = true;
                        break;
                    }
                    try {
                        int choice = Integer.parseInt(parts[0]);
                        int userValue = Integer.parseInt(parts[1]);
                        if (choice != 1 && choice != 2) {

                            System.out.println("Ошибка: неверный выбор режима для count_less_than_best_album. Используйте 1 или 2.");
                            hasError = true;
                            break;
                        }
                        if (userValue < 0) {
                            System.out.println("Ошибка: число для сравнения должно быть > 0.");
                            hasError = true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: неверный формат данных для count_less_than_best_album.");
                        hasError = true;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден.");
            hasError = true;
        }

        if (hasError) {
            executingScripts.remove(filePath);
            scriptDepth--;
            return;
        }

        redirectOutputToFile();
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] commandParts = line.split(" ", 2);
                String cmd = commandParts[0].toLowerCase();
                String args = (commandParts.length > 1) ? commandParts[1] : "";

                try {
                    if (cmd.equals("execute_script")) {
                        restoreOutput();
                        execute(args);
                        redirectOutputToFile();
                        continue;
                    }

                    if (cmd.equals("add")) {
                        MusicBand band = parseMusicBand(args);
                        if (band != null) {
                            bands.add(band);
                            bandsById.put(band.getId(), band);
                            System.out.println("Музыкальная группа добавлена: " + band.getName());
                        }
                    } else if (cmd.equals("add_if_min")) {
                        int minAlbums = Integer.MAX_VALUE;
                        for (MusicBand musicBand : bands) {
                            int count = musicBand.getAlbumsCount();
                            if (count < minAlbums) {
                                minAlbums = count;
                            }
                        }
                        MusicBand band = parseMusicBand(args);
                        if (band != null && band.getAlbumsCount() < minAlbums) {
                            bands.add(band);
                            bandsById.put(band.getId(), band);
                            System.out.println("Музыкальная группа добавлена (add_if_min): " + band.getName());
                        }
                    } else if (cmd.equals("count_less_than_best_album")) {
                        String[] parts = args.split(" ");
                        int choice = Integer.parseInt(parts[0]);
                        int userValue = Integer.parseInt(parts[1]);

                        int count = 0;
                        for (MusicBand band : bands) {
                            if (band.getBestAlbum() != null &&
                                    (choice == 1 ? band.getBestAlbum().getTracks() : band.getBestAlbum().getSales()) < userValue) {
                                count++;
                            }
                        }
                        System.out.println("Количество групп с bestAlbum " +
                                (choice == 1 ? "по количеству треков" : "по продажам") + " меньше " + userValue + ": " + count);
                    } else {
                        commands.get(cmd).execute(args);
                    }
                } catch (Exception e) {
                    restoreOutput();
                    System.out.println("Ошибка при выполнении команды \"" + cmd + "\": " + e.getMessage());
                    executingScripts.remove(filePath);
                    scriptDepth--;
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            restoreOutput();
            System.out.println("Ошибка: файл не найден.");
            executingScripts.remove(filePath);
            scriptDepth--;
            return;
        }

        restoreOutput();
        executingScripts.remove(filePath);
        scriptDepth--;

        if (scriptDepth == 0) {
            System.out.println("Скрипт выполнен.");
        }
    }
}