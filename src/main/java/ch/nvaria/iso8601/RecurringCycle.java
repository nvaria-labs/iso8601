package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.mappers.TimeIntervalMapper;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import ch.nvaria.iso8601.base.models.TimeIntervalRecord;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Parses ISO 8601-1 recurring time intervals (clause 5.6): {@code Rn/<interval>} where the interval is
 * any of {@code start/end}, {@code start/duration}, {@code duration/end} or {@code duration}.
 * {@code R} and {@code R-1} mean unbounded.
 * <p>
 * Each repetition starts where the previous one ended, so the interval's duration is also the step.
 * When the interval has no start, {@code ZonedDateTime.now(zoneId)} is used as the start of the first
 * repetition. This is a library convention; ISO 8601 leaves the start to the context.
 */
public final class RecurringCycle {

    /**
     * Value returned by {@link #repetitions(String)} for an unbounded cycle ({@code R} or {@code R-1}).
     */
    public static final int UNBOUNDED = Integer.MAX_VALUE;

    private RecurringCycle() {
    }

    //-----------------------------------------------------------------------

    /**
     * Get the calculated trigger times of a repetition cycle (inclusive).
     * The first trigger is the start, the list has n entries.
     * Example: R5/someDate/P1D results in someDate and the four following days.
     * If no start date is given, ZonedDateTime.now(zoneId) is used.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @return List of dates
     * @throws DateTimeParseException   if iso8601 string is not a valid recurring interval
     * @throws IllegalArgumentException if the cycle is unbounded; use {@link #triggerTimesInclusive(String, ZoneId, int)}
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesInclusive(String iso8601, ZoneId zoneId) {
        return triggersInclusive(iso8601, zoneId).limit(boundedRepetitions(iso8601)).toList();
    }

    /**
     * Get the calculated trigger times of a repetition cycle (inclusive), at most {@code limit} entries.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @param limit   maximum number of entries, useful for unbounded cycles
     * @return List of dates
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesInclusive(String iso8601, ZoneId zoneId, int limit) {
        return triggersInclusive(iso8601, zoneId).limit(limit).toList();
    }

    /**
     * Get the calculated trigger times of a repetition cycle (exclusive).
     * The first trigger is the start plus one duration, the list has n entries.
     * If no start date is given, ZonedDateTime.now(zoneId) is used.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @return List of dates
     * @throws DateTimeParseException   if iso8601 string is not a valid recurring interval
     * @throws IllegalArgumentException if the cycle is unbounded; use {@link #triggerTimesExclusive(String, ZoneId, int)}
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesExclusive(String iso8601, ZoneId zoneId) {
        return triggersExclusive(iso8601, zoneId).limit(boundedRepetitions(iso8601)).toList();
    }

    /**
     * Get the calculated trigger times of a repetition cycle (exclusive), at most {@code limit} entries.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @param limit   maximum number of entries, useful for unbounded cycles
     * @return List of dates
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     * @since 1.0
     */
    public static List<ZonedDateTime> triggerTimesExclusive(String iso8601, ZoneId zoneId, int limit) {
        return triggersExclusive(iso8601, zoneId).limit(limit).toList();
    }

    /**
     * Lazy stream of trigger times (inclusive: the first element is the start), n elements long,
     * infinite for an unbounded cycle.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @return stream of dates
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     */
    public static Stream<ZonedDateTime> triggersInclusive(String iso8601, ZoneId zoneId) {
        Parsed p = parse(iso8601, zoneId);
        return Stream.iterate(p.start(), p.duration()::addTo).limit(p.repetitions());
    }

    /**
     * Lazy stream of trigger times (exclusive: the first element is the start plus one duration),
     * n elements long, infinite for an unbounded cycle.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId
     * @return stream of dates
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     */
    public static Stream<ZonedDateTime> triggersExclusive(String iso8601, ZoneId zoneId) {
        Parsed p = parse(iso8601, zoneId);
        return Stream.iterate(p.duration().addTo(p.start()), p.duration()::addTo).limit(p.repetitions());
    }

    /**
     * Retrieves the number of repetitions Rn of an iso8601 string.
     * Example: R5/someDate wil result in 5
     *
     * @param iso8601 iso8601 string
     * @return the number of repetitions, {@link #UNBOUNDED} for R or R-1
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     * @since 1.0
     */
    public static Integer repetitions(String iso8601) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::recurring, ctx -> repetitions(count(ctx)));
    }

    /**
     * Retrieves start date of cycle iso8601 string.
     * Example: R5/someDate/P1D wil result in someDate. For R5/P1D/someDate the start is someDate minus P1D.
     * If the string has no start, ZonedDateTime.now(zoneId) is returned.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  ZoneId, e.g. ZoneId.of("Z") or ZoneId.of("Europe/Berlin")
     * @return date as a ZonedDateTime
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     * @since 1.0
     */
    public static Optional<ZonedDateTime> startDate(String iso8601, ZoneId zoneId) {
        return Optional.of(parse(iso8601, zoneId).start());
    }

    /**
     * Retrieves the period and duration R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M -> P1Y2M10DT2H30M
     * For a start/end interval the exact duration between the two points is returned.
     *
     * @param iso8601 iso8601 string
     * @return the period and duration
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     * @since 1.0
     */
    public static PeriodDurationRecord periodDuration(String iso8601) {
        return interval(iso8601, ZoneId.of("Z")).duration();
    }

    /**
     * Retrieves the interval part of a recurring interval.
     *
     * @param iso8601 iso8601 string
     * @param zoneId  zone for points that carry no offset
     * @return the interval; start and end are {@code null} for a bare duration
     * @throws DateTimeParseException if iso8601 string is not a valid recurring interval
     */
    public static TimeIntervalRecord interval(String iso8601, ZoneId zoneId) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::recurring, ctx -> interval(ctx, zoneId));
    }

    //-----------------------------------------------------------------------

    private record Parsed(int repetitions, ZonedDateTime start, PeriodDurationRecord duration) {
    }

    private static Parsed parse(String iso8601, ZoneId zoneId) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::recurring, ctx -> {
            TimeIntervalRecord interval = interval(ctx, zoneId);
            ZonedDateTime start = interval.start() != null ? interval.start() : ZonedDateTime.now(zoneId);
            return new Parsed(repetitions(count(ctx)), start, interval.duration());
        });
    }

    private static int boundedRepetitions(String iso8601) {
        int reps = repetitions(iso8601);
        if (reps == UNBOUNDED) {
            throw new IllegalArgumentException("Unbounded recurring interval, pass a limit: " + iso8601);
        }
        return reps;
    }

    private static Iso8601Parser.RecurringCountContext count(Iso8601Parser.RecurringContext ctx) {
        return ctx.recurringBasic() != null ? ctx.recurringBasic().recurringCount() : ctx.recurringExtended().recurringCount();
    }

    private static TimeIntervalRecord interval(Iso8601Parser.RecurringContext ctx, ZoneId zoneId) {
        repetitions(count(ctx)); // validates the count, e.g. rejects R-2
        return ctx.recurringBasic() != null
                ? TimeIntervalMapper.toInterval(ctx.recurringBasic().durationBasic(), zoneId)
                : TimeIntervalMapper.toInterval(ctx.recurringExtended().durationExtended(), zoneId);
    }

    private static int repetitions(Iso8601Parser.RecurringCountContext ctx) {
        // R -> unbounded, R-1 -> unbounded, Rn -> n
        if (ctx.int_() == null) {
            return UNBOUNDED;
        }
        boolean negative = ctx.getChild(1).getText().equals("-");
        int n = Integer.parseInt(ctx.int_().getText());
        if (negative) {
            if (n != 1) {
                throw new DateTimeParseException("Only R-1 is allowed as a negative count: " + ctx.getText(), ctx.getText(), 0);
            }
            return UNBOUNDED;
        }
        return n;
    }
}
