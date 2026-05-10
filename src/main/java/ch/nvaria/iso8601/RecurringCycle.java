package ch.nvaria.iso8601;


import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import ch.nvaria.iso8601.base.visitors.RecurringVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RecurringCycle {


    private RecurringCycle() {
    }

    //-----------------------------------------------------------------------


    /**
     * Get the calculated trigger times of a repetition cycle (inclusive)
     * The first trigger is the startDateTime
     *
     * Example: R5/someDate wil result in 5 entries in the list of trigger times
     * If no start date is given, ZonedDateTime.now(zoneId) is used
     *
     *
     * @param iso8601 iso8601 string
     * @param zoneId ZoneId
     * @return List of dates
     * @throws NumberFormatException if iso8601 string does not contain Rn part
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesInclusive(String iso8601, ZoneId zoneId) throws NumberFormatException {

        int reps = RecurringCycle.repetitions(iso8601);
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601, zoneId);
        ZonedDateTime zdt = ZonedDateTime.now(zoneId);
        PeriodDurationRecord pdr = RecurringCycle.periodDuration(iso8601);

        // R3/date/
        if (startDate.isPresent()) {
            zdt = startDate.get();
        }

        ArrayList<ZonedDateTime> triggers = new ArrayList<>();
        triggers.add(zdt);
        for (int i = 1; i < reps; i++) {


            if (pdr.period() != null) {
                zdt = zdt.plus(pdr.period());
            }

            if (pdr.duration() != null) {
                zdt =  zdt.plus(pdr.duration());
            }
            triggers.add(zdt);
        }

        return triggers;
    }

    /**
     * Get the calculated trigger times of a repetition cycle (exclusive)
     * The first trigger is startDateTime + durationOfTrigger
     * Example: R5/someDate wil result in 5 entries in the list of trigger times
     * If no start date is given, ZonedDateTime.now(zoneId) is used
     * @param iso8601 iso8601 string
     * @param zoneId ZoneId
     * @return List of dates
     * @throws NumberFormatException if iso8601 string does not contain Rn part
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesExclusive(String iso8601, ZoneId zoneId) throws NumberFormatException {

        int reps = RecurringCycle.repetitions(iso8601);
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601, zoneId);
        ZonedDateTime zdt = ZonedDateTime.now(zoneId);
        PeriodDurationRecord pdr = RecurringCycle.periodDuration(iso8601);

        // R3/date/
        if (startDate.isPresent()) {
            zdt = startDate.get();
        }

        ArrayList<ZonedDateTime> triggers = new ArrayList<>();
        for (int i = 0; i < reps; i++) {


            if (pdr.period() != null) {
                zdt = zdt.plus(pdr.period());
            }

            if (pdr.duration() != null) {
               zdt =  zdt.plus(pdr.duration());
            }
             triggers.add(zdt);
        }

        return triggers;
    }


    /**
     * Retrieves the number of repetitions Rn of an iso8601 string.
     * Example: R5/someDate wil result in 5
     *
     * @param iso8601 iso8601 string
     * @return the number of repetitions, if Rn is unbounded (R-1/someDate) Integer.MAX_VALUE
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
     * Example: R5/someDate wil result in someDate
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId, e.g. ZoneId.of("Z") or ZoneId.of("Europe/Berlin")
     * @return date as a ZonedDateTime
     * @since 1.0
     */
    public static Optional<ZonedDateTime> startDate(String iso8601, ZoneId zoneId) {
        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.recurringExtended();
        ParseTree selectedTree = parseTree.getChild(2).getChild(0).getChild(0);
        if(selectedTree instanceof Iso8601Parser.IntervalTContext){
            return  Optional.of(ZonedDateTime.now(zoneId));
        }

        String res = parseTree.getChild(2).getChild(0).getChild(0).getText();
        return Optional.ofNullable(Iso8601Date.date(res, zoneId));
    }


    /**
     * Retrieves the period and duration R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M -> P1Y2M10DT2H30M
     *
     * @param iso8601 iso8601 string
     * @return the period and duration
     * @throws NumberFormatException if iso8601 string does not contain Rn part
     * @since 1.0
     */
    public static PeriodDurationRecord periodDuration(String iso8601) throws NumberFormatException {
        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.recurring();
        String result = new RecurringVisitor().visit(parseTree);

        return PeriodOfTime.duration(result);
    }



}
