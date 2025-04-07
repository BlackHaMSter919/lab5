package managers;

import models.*;

import java.time.ZonedDateTime;
import java.util.*;

import static managers.XMLManager.loadFromFile;

/**
 * Класс, управляющий коллекцией музыкальных групп {@link MusicBand}.
 * Обеспечивает возможность добавления, удаления, отображения и сохранения/загрузки данных в файл.
 */
public class MusicBandManager {
    public static final Scanner scanner = new Scanner(System.in);
    public static MusicBandManager manager;
    public static Queue<MusicBand> bands;
    public static Map<Integer, MusicBand> bandsById;
    public static String filePath;
    public static ZonedDateTime initializationDate;
    public static final String ERROR_EMPTY_NAME = "Ошибка! Название не может быть пустым.";
    public static final String ERROR_INVALID_NUMBER = "Ошибка! Введите целое число.";
    public static final String ERROR_INVALID_FLOAT = "Ошибка! Введите число с плавающей запятой.";
    public static final String SUCCESS_BAND_ADDED = "Группа успешно добавлена в коллекцию!\n";


    /**
     * Инициализирует менеджер музыкальных групп, если он ещё не был инициализирован.
     * @param filePath путь к файлу, в который/из которого будет происходить загрузка/сохранение данных.
     */
    public static void initializeManager(String filePath) {
        if (manager == null) {
            manager = new MusicBandManager(filePath);
        }
    }

    /**
     * Конструктор класса MusicBandManager.
     * @param filePath путь к файлу для сохранения и загрузки коллекции.
     */
    public MusicBandManager(String filePath) {
        MusicBandManager.filePath = filePath;
        MusicBandCollectionWrapper.bands = bands;
        bandsById = new HashMap<>();
        initializationDate = ZonedDateTime.now();
        loadFromFile();
    }
    /**
     * Запрашивает ввод строки у пользователя.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если ввод пуст.
     * @return введенная строка.
     */
    public static String getInputString(String prompt, String errorMessage) {
        String input;
        while (true) {

            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) break;
            System.out.println(errorMessage);
        }
        return input;
    }
    /**
     * Запрашивает ввод целого числа у пользователя.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если введено нецелое число.
     * @return введённое целое число.
     */
    public static Integer getInputIntForNullableField(String prompt, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Ошибка! Число не может быть отрицательным.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static int getInputInt(String prompt, String errorMessage) {
        int input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input <= 0) {
                    System.out.println("Ошибка! Число должно быть больше 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    /**
     * Запрашивает ввод числа с плавающей запятой.
     * @param prompt сообщение для пользователя.
     * @param errorMessage сообщение об ошибке, если введено нечисловое значение.
     * @return введённое число с плавающей запятой.
     */
    public static float getInputFloat(String prompt, String errorMessage) {
        float input;
        while (true) {
            System.out.print(prompt);
            try {
                input = Float.parseFloat(scanner.nextLine().trim());
                if (input <= 0) {
                    System.out.println("Ошибка! Число должно быть больше 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return input;
    }


    /**
     * Запрашивает ввод координат группы.
     *
     * @return объект {@link Coordinates} с введенными значениями.
     */
    public static Coordinates getCoordinates() {
        int x = getInputInt("Введите координату X (целое число): ", ERROR_INVALID_NUMBER);
        int y = getInputInt("Введите координату Y (целое число): ", ERROR_INVALID_NUMBER);
        return new Coordinates(x, y);
    }

    /**
     * Запрашивает выбор жанра музыки у пользователя.
     * @return выбранный жанр {@link MusicGenre}.
     */
    public static MusicGenre getMusicGenre() {
        MusicGenre genre;
        while (true) {
            System.out.println("Выберите жанр музыки:");
            for (MusicGenre g : MusicGenre.values()) {
                System.out.println("- " + g);
            }
            System.out.print("Введите жанр (как указано выше): ");
            try {
                genre = MusicGenre.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка! Введите один из доступных жанров.");
            }
        }
        return genre;
    }

    /**
     * Запрашивает данные об альбоме у пользователя.
     * @return объект {@link Album} с введёнными данными.
     */
    public static Album getAlbumDetails() {
        String name = getInputString("Введите название альбома: ",ERROR_EMPTY_NAME);
        float sales = getInputFloat("Введите скидку альбома: ",ERROR_INVALID_FLOAT);
        int tracks = getInputInt("Введите количество треков в альбоме: ",ERROR_INVALID_NUMBER);
        return new Album(name, sales, tracks);
    }


}