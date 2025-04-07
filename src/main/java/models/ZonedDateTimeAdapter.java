package models;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Адаптер для преобразования ZonedDateTime в строку и обратно при работе с XML.
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    /**
     * Преобразует строку из XML в объект ZonedDateTime.
     *
     * @param v строка в формате ISO_ZONED_DATE_TIME
     * @return объект ZonedDateTime
     * @throws Exception если строка не может быть преобразована
     */
    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * Преобразует объект ZonedDateTime в строку для сохранения в XML.
     *
     * @param v объект ZonedDateTime
     * @return строка в формате ISO_ZONED_DATE_TIME
     * @throws Exception если объект не может быть преобразован
     */
    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return v.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}