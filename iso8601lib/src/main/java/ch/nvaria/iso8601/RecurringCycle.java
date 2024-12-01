package ch.nvaria.iso8601;


import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.visitors.RecurringVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public final class RecurringCycle {


    private RecurringCycle() {
    }

    //-----------------------------------------------------------------------

    /**
     * Retrieves the number of repetitions Rn of an iso8601 string.
     * Example: R5/<someDate> wil result in 5
     * <p>
     *
     * @param iso8601 iso8601 string
     * @return the number of repetitions, if Rn is unbounded (R-1/<someDate>) Integer.MAX_VALUE
     * @throws NumberFormatException if iso8601 string does not contain Rn part
     * @since 1.0
     */
    public static Integer repetitions(String iso8601) throws NumberFormatException {
        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.recurringCount();
        return Integer.valueOf(new RecurringVisitor().visit(parseTree));
    }

    /**
     * Retrieves start date of cycle iso8601 string.
     * Example: R5/<someDate> wil result in someDate
     *
     * @param iso8601 iso8601 string
     * @param zoneId ZoneId, e.g. ZoneId.of("Z") or ZoneId.of("Europe/Berlin")
     * @return date as a ZonedDateTime
     * @throws DateTimeParseException if iso8601 string does not contain start date part
     * @since 1.0
     */
    public static ZonedDateTime startDate(String iso8601, ZoneId zoneId) throws DateTimeParseException {
        String res = startDate(iso8601);
        return ZonedDateTime.parse(res).withZoneSameInstant(zoneId);
    }

    private static String startDate(String iso8601) {
        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.recurringExtended();
        return new RecurringVisitor().visit(parseTree);

    }

}
