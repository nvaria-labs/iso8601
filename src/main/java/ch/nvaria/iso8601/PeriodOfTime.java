package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.mappers.DurationMapper;
import ch.nvaria.iso8601.base.mappers.TimeIntervalMapper;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import ch.nvaria.iso8601.base.models.TimeIntervalRecord;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Parses ISO 8601-1 durations and time intervals (clause 5.5).
 */
public final class PeriodOfTime {

    private PeriodOfTime() {
    }

    /**
     * Retrieves the duration of an iso8601 duration or time interval.
     * <p>
     * Examples: {@code P3Y6M4DT12H30M50S}, {@code P2W}, {@code PT1.5H}, {@code P0001-02-03T04:05:06},
     * {@code -P1DT2H} (negative, an ISO 8601-2 extension),
     * {@code 2026-09-15T10:00:00Z/P1D}, {@code P1D/2026-09-16}, {@code 2026-09-15/2026-09-17}.
     * For a {@code start/end} interval the exact {@link java.time.Duration} between the two points is returned.
     *
     * @param iso8601 the iso 8601 string
     * @return the calendar and exact parts of the duration
     * @throws DateTimeParseException if the string is not a valid ISO 8601 duration or interval
     */
    public static PeriodDurationRecord duration(String iso8601) {
        boolean negative = iso8601 != null && !iso8601.isEmpty() && iso8601.charAt(0) == '-';
        if (negative) {
            return ParseTreeFactory.parse(iso8601.substring(1), Iso8601Parser::interval, DurationMapper::toPeriodDuration).negated();
        }
        return interval(iso8601, ZoneOffset.UTC).duration();
    }

    /**
     * Retrieves a time interval with start, end and duration.
     * <p>
     * Examples: {@code 2026-09-15T10:00:00Z/2026-09-16T10:00:00Z}, {@code 2026-09-15/17},
     * {@code 2026-09-15T10:00:00Z/P1D}, {@code P1D/2026-09-16T10:00:00Z}. For a bare duration
     * ({@code P1D}) start and end are {@code null}.
     *
     * @param iso8601 the iso 8601 string
     * @param zoneId  zone for points that carry no offset
     * @return the interval
     * @throws DateTimeParseException if the string is not a valid ISO 8601 duration or interval
     */
    public static TimeIntervalRecord interval(String iso8601, ZoneId zoneId) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::duration, ctx -> TimeIntervalMapper.toInterval(ctx, zoneId));
    }

    /**
     * Length in milliseconds of a duration applied at {@code start}. Calendar parts (months, days)
     * are resolved against the given start, including daylight saving transitions.
     *
     * @param iso8601 the iso 8601 duration string
     * @param start   the start
     * @return milliseconds between start and start plus duration
     * @throws DateTimeParseException if the string is not a valid ISO 8601 duration
     */
    public static long durationInMillisFrom(String iso8601, ZonedDateTime start) {
        return start.until(duration(iso8601).addTo(start), ChronoUnit.MILLIS);
    }

    /**
     * Length in milliseconds of a duration applied at {@code start}. Calendar parts (months, days)
     * are resolved against the given start.
     *
     * @param iso8601 the iso 8601 duration string
     * @param start   the start
     * @return milliseconds between start and start plus duration
     * @throws DateTimeParseException if the string is not a valid ISO 8601 duration
     */
    public static long durationInMillisFrom(String iso8601, LocalDateTime start) {
        return start.until(duration(iso8601).addTo(start), ChronoUnit.MILLIS);
    }
}
