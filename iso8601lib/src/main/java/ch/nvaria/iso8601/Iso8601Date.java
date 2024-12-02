package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import org.antlr.v4.runtime.tree.ParseTree;

import java.time.LocalDate;
import java.time.LocalTime;
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
     *
     * @param iso8601 the iso 8601
     * @param zoneId  the zone id as ZoneId
     * @return the zoned date time
     */
    public static ZonedDateTime date(String iso8601, ZoneId zoneId) {

        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.iso();

        int childCount = parseTree.getChild(0).getChild(0).getChildCount();

        if(childCount >= 3){
            String date = parseTree.getChild(0).getChild(0).getChild(0).getText();
            String time = parseTree.getChild(0).getChild(0).getChild(2).getText();
            LocalTime ltime = LocalTime.parse(time);
            LocalDate ldate = LocalDate.parse(date);
            return ldate.atTime(ltime).atZone(zoneId);

        }

        if(childCount == 1){
            LocalDate ldate = LocalDate.parse(iso8601);
            return ldate.atStartOfDay(zoneId);
         }
        return null;
    }
}
