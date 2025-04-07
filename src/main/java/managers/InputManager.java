package managers;

import commands.*;

import java.util.AbstractMap;
import java.util.Map;

import static managers.MusicBandManager.scanner;


/**
 * Класс, отвечающий за взаимодействие пользователя с консолью.
 */
public class InputManager {

    public static final Map<String, Command> commands = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("remove_first", new RemoveFirst()),
            new AbstractMap.SimpleEntry<>("help", new Help()),
            new AbstractMap.SimpleEntry<>("remove_by_id", new RemoveById()),
            new AbstractMap.SimpleEntry<>("remove_head", new RemoveHead()),
            new AbstractMap.SimpleEntry<>("add_if_min", new AddIfMin()),
            new AbstractMap.SimpleEntry<>("max_by_albums_count", new MaxByAlbumsCount()),
            new AbstractMap.SimpleEntry<>("count_less_than_best_album", new CountLessThanBestAlbum()),
            new AbstractMap.SimpleEntry<>("print_field_ascending_number_of_participants", new PrintFieldAscendingNumberOfParticipants()),
            new AbstractMap.SimpleEntry<>("info", new Info()),
            new AbstractMap.SimpleEntry<>("show", new Show()),
            new AbstractMap.SimpleEntry<>("add", new Add()),
            new AbstractMap.SimpleEntry<>("clear", new Clear()),
            new AbstractMap.SimpleEntry<>("save", new Save()),
            new AbstractMap.SimpleEntry<>("execute_script", new ExecuteScript()),
            new AbstractMap.SimpleEntry<>("exit", new Exit())
    );

    /**
     * Запускает цикл обработки команд пользователя.
     */
    public static void startCommandLoop() {

        while (true) {
            System.out.print("Введите команду: ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty())
                continue;

            String[] commandParts = command.split(" ", 2);  // Разделяем команду и аргументы
            String cmdName = commandParts[0];
            String args = commandParts.length > 1 ? commandParts[1].trim() : null;

            Command cmd = commands.get(cmdName);
            if (cmd != null) {
                try {
                    cmd.execute(args);
                } catch (Exception e) {
                    System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                }
            } else {
                System.out.println("Неизвестная команда. Введите 'help' для справки.");
            }
        }
    }
}




