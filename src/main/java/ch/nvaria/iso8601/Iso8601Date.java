package ch.nvaria.iso8601;

import ch.nvaria.generated.Iso8601Parser;
import ch.nvaria.iso8601.base.factories.ParseTreeFactory;
import ch.nvaria.iso8601.base.mappers.DateTimeMapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * Parses ISO 8601-1 dates and date-times (clauses 5.2 and 5.4).
 * <p>
 * Supported: calendar, ordinal and week dates in basic and extended format, reduced precision
 * ({@code 2026-09}, {@code 2026}, {@code 20}, {@code 2026-W38}), expanded years ({@code +012026-09-15}),
 * times with reduced precision or a fraction on the smallest component ({@code T10}, {@code T10.5},
 * {@code T10:30,5}), {@code 24:00:00} as end of day, and offsets {@code Z}, {@code ±hh}, {@code ±hh:mm},
 * {@code ±hhmm} (basic format).
 * <p>
 * As an extension, a zone id in brackets ({@code 2026-09-15T10:00Z[Europe/Berlin]}) is accepted and
 * handled by {@link ZonedDateTime#parse(CharSequence)}.
 */
public final class Iso8601Date {

    private Iso8601Date() {
    }

    /**
     * Retrieve a ZonedDateTime from an iso8601 date or date-time string.
     * If no time is provided, the start of that day in {@code zoneId} is used.
     * If no offset is provided, {@code zoneId} is used. An offset in the string always wins.
     *
     * @param iso8601 the iso 8601 string
     * @param zoneId  the zone id as a string
     * @return the zoned date time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 date or date-time
     */
    public static ZonedDateTime date(String iso8601, String zoneId) {
        return date(iso8601, ZoneId.of(zoneId));
    }

    /**
     * Retrieve a ZonedDateTime from an iso8601 date or date-time string.
     * If no time is provided, the start of that day in {@code zoneId} is used.
     * If no offset is provided, {@code zoneId} is used. An offset in the string always wins.
     *
     * @param iso8601 the iso 8601 string
     * @param zoneId  the zone id
     * @return the zoned date time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 date or date-time
     */
    public static ZonedDateTime date(String iso8601, ZoneId zoneId) {
        if (iso8601 != null && iso8601.indexOf('[') >= 0) {
            // java.time extension "2026-09-15T10:00Z[Europe/Berlin]", not part of ISO 8601
            return ZonedDateTime.parse(iso8601);
        }
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::dateOrDatetime, ctx -> {
            if (ctx.datetime() != null) {
                return DateTimeMapper.toZonedDateTime(ctx.datetime(), zoneId);
            }
            return DateTimeMapper.toLocalDate(ctx.date()).atStartOfDay(zoneId);
        });
    }

    /**
     * Retrieve a LocalDate from an iso8601 date string (no time part).
     * Reduced precision resolves to the first day of the month, year, century or week.
     *
     * @param iso8601 the iso 8601 date string
     * @return the local date
     * @throws DateTimeParseException if the string is not a valid ISO 8601 date
     */
    public static LocalDate localDate(String iso8601) {
        return ParseTreeFactory.parse(iso8601, Iso8601Parser::date, DateTimeMapper::toLocalDate);
    }
}
