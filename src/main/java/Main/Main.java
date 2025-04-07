/**
 Главный класс приложения для управления музыкальными группами.
 Загружает данные из файла и запускает командный интерфейс пользователя. */
package Main;

import managers.MusicBandManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static managers.InputManager.startCommandLoop;


/**
 * Main - класс.
 */
@XmlRootElement(name = "Main")
public class Main {


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
            while (reader.readLine() != null) {
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }
        MusicBandManager.initializeManager(filePath);

        startCommandLoop();
    }

}