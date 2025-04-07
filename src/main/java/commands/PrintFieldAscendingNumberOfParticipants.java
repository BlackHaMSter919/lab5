package commands;

import models.MusicBand;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static managers.MusicBandManager.bands;
/**
 * Класс, Выводящий количество участников в группах в порядке возрастания.
 */
@XmlRootElement(name = "PrintFieldAscendingNumberOfParticipant")
public class PrintFieldAscendingNumberOfParticipants implements Command{


    /**
     * Выводит количество участников в группах в порядке возрастания.
     *
     * @return
     */

    @Override
    public void execute(String args){
        List<Integer> sortedParticipants = new ArrayList<>();
        for (MusicBand band : bands) {
            Integer numberOfParticipants = band.getNumberOfParticipants();
            sortedParticipants.add(numberOfParticipants);
        }
        sortedParticipants.sort(null);
        if (sortedParticipants.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            sortedParticipants.forEach(System.out::println);
        }
    }
}
