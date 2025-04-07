package commands;

import models.MusicBand;

import javax.xml.bind.annotation.XmlRootElement;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.scanner;

/**
 * Класс, подсчитывания кол-ва групп с альбомами, у которых количество треков или продажи меньше заданного значения.
 */
@XmlRootElement(name = "CountLessThanBestAlbum")
public class CountLessThanBestAlbum implements Command {
    /**
     * Выводит количество групп с альбомами, у которых количество треков или продажи меньше заданного значения.
     *
     * @return
     */

    @Override
    public void execute(String args) {
        System.out.println("Выберите параметр сравнения: \n1 - Количество треков (tracks) \n2 - Продажи альбома (sales)");

        int choice;
        while (true) {
            choice = scanner.nextInt();
            if (choice == 1 || choice == 2) {
                break;
            }
            System.out.println("Неверный ввод. Выберите 1 или 2.");
        }

        System.out.println("Введите число для сравнения:");

        int userValue;
        while (true) {
            if (scanner.hasNextInt()) {
                userValue = scanner.nextInt();
                if (userValue >= 0) { 
                    break;
                } else {
                    System.out.println("Число должно быть положительным. Попробуйте снова:");
                }
            } else {
                System.out.println("Введено не число. Попробуйте снова:");
                scanner.next(); 
            }
        }

        int count = 0;
        for (MusicBand band : bands) {
            if (band.getBestAlbum() != null &&
                    (choice == 1 ? band.getBestAlbum().getTracks() : band.getBestAlbum().getSales()) < userValue) {
                count++;
            }
        }

        System.out.println("Количество элементов с bestAlbum < " + userValue + ": " + count);
    }
}