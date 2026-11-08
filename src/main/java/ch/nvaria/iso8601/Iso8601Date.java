package ch.nvaria.iso8601;

import ch.nvaria.iso8601.internal.DateTimeMapper;
import ch.nvaria.iso8601.internal.ParseTreeFactory;
import ch.nvaria.iso8601.internal.parser.Iso8601Parser;

import java.time.DateTimeException;
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
 * As an extension borrowed from java.time, a zone id in brackets may follow the date or date-time
 * ({@code 2026-09-15T10:00Z[Europe/Berlin]}, {@code 20260915T1000[Europe/Berlin]}). The result is in that
 * zone: an offset in the string fixes the instant, otherwise the local time is taken in the bracket zone.
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
     * A bracket zone id ({@code ...[Europe/Berlin]}) replaces {@code zoneId}.
     *
     * @param iso8601 the iso 8601 string
     * @param zoneId  the zone id
     * @return the zoned date time
     * @throws DateTimeParseException if the string is not a valid ISO 8601 date or date-time
     */
    public static ZonedDateTime date(String iso8601, ZoneId zoneId) {
        int bracket = iso8601 == null ? -1 : iso8601.indexOf('[');
        if (bracket >= 0) {
            // java.time extension "2026-09-15T10:00Z[Europe/Berlin]", not part of ISO 8601
            ZoneId zone = bracketZone(iso8601, bracket);
            return date(iso8601.substring(0, bracket), zone).withZoneSameInstant(zone);
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

    private static ZoneId bracketZone(String iso8601, int bracket) {
        if (!iso8601.endsWith("]")) {
            throw new DateTimeParseException("Unclosed zone id in '" + iso8601 + "'", iso8601, bracket);
        }
        try {
            return ZoneId.of(iso8601.substring(bracket + 1, iso8601.length() - 1));
        } catch (DateTimeException e) {
            throw new DateTimeParseException(e.getMessage() + " in '" + iso8601 + "'", iso8601, bracket + 1, e);
        }
    }
}
