
/**

 Главный класс приложения для управления музыкальными группами.

 Загружает данные из файла и запускает командный интерфейс пользователя. */

import managers.MusicBandManager;

import java.io.*;
import java.util.Scanner;

import static managers.MusicBandManager.addBand;

public class Main {
    private static final Scanner scanner = new Scanner(System.in); private static MusicBandManager manager;

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки, где первый аргумент - путь к файлу с данными.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Пожалуйста, укажите путь к файлу.");
            return;
        }
        String filePath = args[0];
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Файл не существует: " + filePath);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        MusicBandManager.initializeManager(filePath);
        manager = MusicBandManager.manager;

        startCommandLoop();
    }

    /**
     * Запускает цикл обработки команд пользователя.
     */
    private static void startCommandLoop() {
        String command;

        while (true) {
            System.out.print("Введите команду: ");
            command = scanner.nextLine().trim();

            if (command.isEmpty())
            continue;

            String[] commandParts = command.split(" ", 2);
            String cmd = commandParts[0].toLowerCase();
            String args = (commandParts.length > 1) ? commandParts[1] : "";

            switch (cmd) {
                case "help":
                    showHelp();
                    break;
                case "info":
                    manager.info();
                    break;
                case "show":
                    manager.showAll();
                    break;
                case "add":
                    addBand(false);
                    break;
                case "remove_by_id":
                    removeById(args);
                    break;
                case "clear":
                    manager.clear();
                    break;
                case "save":
                    manager.saveToFile();
                    break;
                case "execute_script":
                    executeScript(args);
                    break;
                case "remove_first":
                    manager.removeFirst();
                    break;
                case "remove_head":
                    manager.removeHead();
                    break;
                case "add_if_min":
                    manager.addIfMin();
                    break;
                case "max_by_albums_count":
                    manager.maxByAlbumsCount();
                    break;
                case "count_less_than_best_album":
                    manager.countLessThanBestAlbum();
                    break;
                case "print_field_ascending_number_of_participants":
                    manager.printFieldAscendingNumberOfParticipants();
                    break;
                case "exit":
                    exit();
                    return;
                default:
                    System.out.println("Неизвестная команда. Введите 'help' для справки.");
            }
        }
    }

    /**
     * Выводит справку по доступным командам.
     */
    private static void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help : вывести справку");
        System.out.println("info : информация о коллекции");
        System.out.println("show : показать все элементы коллекции");
        System.out.println("add {element} : добавить новый элемент");
        System.out.println("remove_by_id {id} : удалить элемент по ID");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script {file_name} : выполнить команды из файла");
        System.out.println("remove_first : удалить первый элемент из коллекции");
        System.out.println("remove_head : вывести и удалить первый элемент");
        System.out.println("add_if_min {element} : добавить элемент, если его значение минимально");
        System.out.println("max_by_albums_count : вывести элемент с максимальным albumsCount");
        System.out.println("count_less_than_best_album bestAlbum : вывести количество элементов, меньше заданного bestAlbum");
        System.out.println("print_field_ascending_number_of_participants : вывести количество участников в порядке возрастания");
        System.out.println("exit : завершить программу");
    }

    /**
     * Удаляет музыкальную группу по её ID.
     *
     * @param args строка с ID группы для удаления.
     */
    private static void removeById(String args) {
        try {
            int id = Integer.parseInt(args);
            manager.removeById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
        }
    }

    /**
     * Выполняет команды из указанного файла.
     *
     * @param fileName путь к файлу со скриптом команд.
     */
    private static void executeScript(String fileName) {
        try {
            File scriptFile = new File(fileName);
            if (!scriptFile.exists()) {
                System.out.println("Файл не найден: " + fileName);
                return;
            }

            Scanner fileScanner = new Scanner(scriptFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    processCommand(line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает и выполняет введённую команду.
     *
     * @param command строка с командой и её аргументами.
     */
    private static void processCommand(String command) {
        String[] commandParts = command.split(" ", 2);
        String cmd = commandParts[0].toLowerCase();
        String args = (commandParts.length > 1) ? commandParts[1] : "";

        switch (cmd) {
            case "help":
                showHelp();
                break;
            case "info":
                manager.info();
                break;
            case "show":
                manager.showAll();
                break;
            case "add":
                addBand(false);
                break;
            case "remove_by_id":
                removeById(args);
                break;
            case "clear":
                manager.clear();
                break;
            case "save":
                manager.saveToFile();
                break;
            case "remove_first":
                manager.removeFirst();
                break;
            case "remove_head":
                manager.removeHead();
                break;
            case "add_if_min":
                manager.addIfMin();
                break;
            case "exit":
                exit();
                return;
            default:
                System.out.println("Неизвестная команда в скрипте.");
        }
    }

    /**
     * Завершает выполнение программы.
     */
    private static void exit() {
        System.out.println("Завершаем программу...");
    }

}