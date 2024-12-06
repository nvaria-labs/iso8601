package ch.nvaria.iso8601;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class Iso8601Date {

    private Iso8601Date() {
    }


    /**
     * Retrieve a ZonedDateTime from an iso8601 date string.
     * If no time is provided in the iso8601 string, startOfDay(zoneId) is used
     *
     * @param iso8601 the iso 8601
     * @param zoneId  the zone id as a string
     * @return the zoned date time
     */
    public static ZonedDateTime date(String iso8601, String zoneId) {
        return date(iso8601, ZoneId.of(zoneId));
    }

    /**
     * Retrieve a ZonedDateTime from an iso8601 date string.
     * If no time is provided in the iso8601 string, startOfDay(zoneId) is used
     * The iso8601 String needs to contain a timezone e.g. "Z"
     *
     * @param iso8601 the iso 8601
     * @param zoneId  the zone id as ZoneId
     * @return the zoned date time
     */
    public static ZonedDateTime date(String iso8601, ZoneId zoneId) {
        String[] parts = iso8601.split("T");

        if(parts.length == 2){
            return ZonedDateTime.parse(iso8601);
        }
        return LocalDate.parse(iso8601).atStartOfDay().atZone(zoneId);
    }
}
