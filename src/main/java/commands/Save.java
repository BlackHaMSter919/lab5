package commands;

import models.MusicBandCollectionWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;

import static managers.MusicBandManager.bands;
import static managers.MusicBandManager.filePath;

public class Save implements Command{
    /**
     * Сохраняет текущую коллекцию музыкальных групп в файл.
     *
     * @return
     */
    @Override
    public void execute(String args) {
        try {
            JAXBContext context = JAXBContext.newInstance(MusicBandCollectionWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            MusicBandCollectionWrapper wrapper = new MusicBandCollectionWrapper();
            wrapper.setBands(bands);

            marshaller.marshal(wrapper, new File(filePath));
        } catch (Exception e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }

    }
}
