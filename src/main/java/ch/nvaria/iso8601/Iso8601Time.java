package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.mappers.DateTimeMapper;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Parses ISO 8601-1 times of day without a date (clause 5.3).
 * <p>
 * Accepts basic and extended format, an optional leading {@code T}, reduced precision, a fraction on the
 * smallest component with dot or comma, and an optional offset: {@code 10:00:00}, {@code T100000},
 * {@code 10:30}, {@code 10.5}, {@code 10:30:15,25+01:00}.
 */
public final class Iso8601Time {

    private Iso8601Time() {
    }

    /**
     * Retrieve the time of day, ignoring any offset in the string.
     *
     * @param iso8601 the iso 8601 time string
     * @return the local time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 time, or is {@code 24:00}
     *                                which only has a meaning together with a date
     */
    public static LocalTime time(String iso8601) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::time, ctx -> {
            DateTimeMapper.TimeOfDay t = DateTimeMapper.toTimeOfDay(ctx.localTime());
            if (t.extraDays() != 0) {
                throw new DateTimeParseException("24:00 is only valid together with a date: " + iso8601, iso8601, 0);
            }
            return t.time();
        });
    }

    /**
     * Retrieve the offset of a time string, if it has one.
     *
     * @param iso8601 the iso 8601 time string
     * @return the offset, or empty for a local time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 time
     */
    public static Optional<ZoneOffset> offset(String iso8601) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::time,
                ctx -> Optional.ofNullable(ctx.timeZone()).map(DateTimeMapper::toOffset));
    }

    /**
     * Retrieve the time of day with its offset.
     *
     * @param iso8601 the iso 8601 time string
     * @param fallback offset to use when the string has none
     * @return the offset time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 time
     */
    public static OffsetTime offsetTime(String iso8601, ZoneOffset fallback) {
        return time(iso8601).atOffset(offset(iso8601).orElse(fallback));
    }
}
