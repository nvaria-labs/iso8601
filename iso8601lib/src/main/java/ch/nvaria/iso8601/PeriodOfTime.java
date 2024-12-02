package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import ch.nvaria.iso8601.base.visitors.PeriodOfTimeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;



public final class PeriodOfTime {

    private PeriodOfTime() {
    }


    /**
     * Get the duration of an iso8601 duration string
     *
     * @param iso8601 the iso 8601 string
     * @return a record with the period and duration
     */
    public static PeriodDurationRecord duration(String iso8601) {

        Iso8601Parser parser = ParseTreeFactory.createParser(iso8601);
        ParseTree parseTree = parser.interval();
        String result = new PeriodOfTimeVisitor().visit(parseTree);
        String[] parts = result.split("T");
        Period period = null;
        if (!Objects.equals(parts[0], "P")) {
            period = Period.parse(parts[0]);
        }

        Duration duration = null;
        if (parts.length != 1){
            duration = Duration.parse("PT" + parts[1]);
        }

        return new PeriodDurationRecord(period, duration);
    }

    /**
     * Calculate the amount of millis of the duration of the iso 8601 string
     *
     * @param iso8601 the iso 8601 duration
     * @param start   the start time from which to calculate (ZDT)
     * @return milliseconds of the duration (long)
     */
    public static long durationInMillisFrom(String iso8601, ZonedDateTime start){
        PeriodDurationRecord pdr = duration(iso8601);
        ZonedDateTime end = calculateEndTime(start, pdr.period(), pdr.duration());
        return start.until(end, ChronoUnit.MILLIS);
    }

    /**
     * Duration in millis from long.
     *
     * @param iso8601 the iso 8601
     * @param start   the start
     * @return the long
     */
    public static long durationInMillisFrom(String iso8601, LocalDateTime start){
        PeriodDurationRecord pdr = duration(iso8601);
        LocalDateTime end = calculateEndTime(start, pdr.period(), pdr.duration());
        return start.until(end, ChronoUnit.MILLIS);
    }

    private static LocalDateTime calculateEndTime(LocalDateTime start, Period period, Duration duration){

        if(period != null){
            start = start.plus(period);
        }

        if(duration != null){
            start = start.plus(duration);
        }
        return start;
    }

    private static ZonedDateTime calculateEndTime(ZonedDateTime start, Period period, Duration duration){

        if(period != null){
            start = start.plus(period);
        }

        if(duration != null){
            start = start.plus(duration);
        }
        return start;
    }

}
