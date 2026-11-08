package ch.nvaria.iso8601.internal;

import ch.nvaria.iso8601.internal.parser.Iso8601Parser;
import ch.nvaria.iso8601.model.PeriodDuration;
import ch.nvaria.iso8601.model.TimeInterval;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

/**
 * Maps ISO 8601-1 clause 5.5 time intervals to a {@link TimeInterval}.
 * <p>
 * The four forms {@code start/end}, {@code start/duration}, {@code duration/end} and {@code duration} are
 * supported. An end that omits higher-order components ({@code 2026-09-15/17}, {@code 2026-09-15/09-17},
 * {@code 2026-09-15T10:00/12:00}) takes them from the start. A start or end given as a date alone means
 * the start of that day. A point without an offset uses the caller's zone, and an end without an offset
 * uses the zone of the start.
 */
public final class TimeIntervalMapper {

    private TimeIntervalMapper() {
    }

    public static TimeInterval toInterval(Iso8601Parser.DurationContext c, ZoneId zone) {
        return c.durationBasic() != null ? toInterval(c.durationBasic(), zone) : toInterval(c.durationExtended(), zone);
    }

    public static TimeInterval toInterval(Iso8601Parser.DurationBasicContext c, ZoneId zone) {
        if (c.timeBeginEndBasic() != null) {
            var be = c.timeBeginEndBasic();
            ZonedDateTime start = point(be.intervalYMDTimeBasic(0), null, zone);
            ZonedDateTime end = point(be.intervalYMDTimeBasic(1), start, zone);
            return between(start, end);
        }
        if (c.timeBeginIntervalBasic() != null) {
            var bi = c.timeBeginIntervalBasic();
            ZonedDateTime start = point(bi.intervalPointBasic(), zone);
            PeriodDuration pd = DurationMapper.toPeriodDuration(bi.intervalBasic());
            return new TimeInterval(start, pd.addTo(start), pd);
        }
        if (c.timeIntervalEndBasic() != null) {
            var ie = c.timeIntervalEndBasic();
            PeriodDuration pd = DurationMapper.toPeriodDuration(ie.intervalBasic());
            ZonedDateTime end = point(ie.intervalPointBasic(), zone);
            return new TimeInterval(pd.subtractFrom(end), end, pd);
        }
        return new TimeInterval(null, null, DurationMapper.toPeriodDuration(c.intervalBasic()));
    }

    public static TimeInterval toInterval(Iso8601Parser.DurationExtendedContext c, ZoneId zone) {
        if (c.timeBeginEndExtended() != null) {
            var be = c.timeBeginEndExtended();
            ZonedDateTime start = point(be.intervalYMDTimeExtended(0), null, zone);
            ZonedDateTime end = point(be.intervalYMDTimeExtended(1), start, zone);
            return between(start, end);
        }
        if (c.timeBeginIntervalExtended() != null) {
            var bi = c.timeBeginIntervalExtended();
            ZonedDateTime start = point(bi.intervalPointExtended(), zone);
            PeriodDuration pd = DurationMapper.toPeriodDuration(bi.intervalExtended());
            return new TimeInterval(start, pd.addTo(start), pd);
        }
        if (c.timeIntervalEndExtended() != null) {
            var ie = c.timeIntervalEndExtended();
            PeriodDuration pd = DurationMapper.toPeriodDuration(ie.intervalExtended());
            ZonedDateTime end = point(ie.intervalPointExtended(), zone);
            return new TimeInterval(pd.subtractFrom(end), end, pd);
        }
        return new TimeInterval(null, null, DurationMapper.toPeriodDuration(c.intervalExtended()));
    }

    // ---------------------------------------------------------------- points

    private static ZonedDateTime point(Iso8601Parser.IntervalPointBasicContext c, ZoneId zone) {
        if (c.datetimeBasic() != null) {
            return DateTimeMapper.toZonedDateTime(c.datetimeBasic(), zone);
        }
        return DateTimeMapper.toLocalDate(c.dateBasic()).atStartOfDay(zone);
    }

    private static ZonedDateTime point(Iso8601Parser.IntervalPointExtendedContext c, ZoneId zone) {
        if (c.datetimeExtended() != null) {
            return DateTimeMapper.toZonedDateTime(c.datetimeExtended(), zone);
        }
        return DateTimeMapper.toLocalDate(c.dateExtended()).atStartOfDay(zone);
    }

    /**
     * @param c    the point
     * @param ref  the start of the interval when {@code c} is the end, else {@code null}
     * @param zone fallback zone for a start without offset
     */
    private static ZonedDateTime point(Iso8601Parser.IntervalYMDTimeBasicContext c, ZonedDateTime ref, ZoneId zone) {
        ZoneId z = ref != null ? ref.getZone() : zone;
        if (c.datetimeBasic() != null) {
            return DateTimeMapper.toZonedDateTime(c.datetimeBasic(), z);
        }
        if (c.dateBasic() != null) {
            return DateTimeMapper.toLocalDate(c.dateBasic()).atStartOfDay(z);
        }
        ZonedDateTime r = requireStart(ref, c.getText());
        if (c.monthDateBasic() != null) {
            var md = c.monthDateBasic();
            return LocalDate.of(r.getYear(), DateTimeMapper.num(md.month()), DateTimeMapper.num(md.day())).atStartOfDay(z);
        }
        if (c.day() != null) {
            return LocalDate.of(r.getYear(), r.getMonthValue(), DateTimeMapper.num(c.day())).atStartOfDay(z);
        }
        return DateTimeMapper.combine(r.toLocalDate(), DateTimeMapper.toTimeOfDay(c.localTimeBasic()), z);
    }

    private static ZonedDateTime point(Iso8601Parser.IntervalYMDTimeExtendedContext c, ZonedDateTime ref, ZoneId zone) {
        ZoneId z = ref != null ? ref.getZone() : zone;
        if (c.datetimeExtended() != null) {
            return DateTimeMapper.toZonedDateTime(c.datetimeExtended(), z);
        }
        if (c.dateExtended() != null) {
            return DateTimeMapper.toLocalDate(c.dateExtended()).atStartOfDay(z);
        }
        ZonedDateTime r = requireStart(ref, c.getText());
        if (c.monthDateExtended() != null) {
            var md = c.monthDateExtended();
            return LocalDate.of(r.getYear(), DateTimeMapper.num(md.month()), DateTimeMapper.num(md.day())).atStartOfDay(z);
        }
        if (c.day() != null) {
            return LocalDate.of(r.getYear(), r.getMonthValue(), DateTimeMapper.num(c.day())).atStartOfDay(z);
        }
        return DateTimeMapper.combine(r.toLocalDate(), DateTimeMapper.toTimeOfDay(c.localTimeExtended()), z);
    }

    private static ZonedDateTime requireStart(ZonedDateTime ref, String text) {
        if (ref == null) {
            throw new DateTimeParseException("An abbreviated representation is only allowed for the end of an interval: " + text, text, 0);
        }
        return ref;
    }

    private static TimeInterval between(ZonedDateTime start, ZonedDateTime end) {
        if (end.isBefore(start)) {
            throw new DateTimeParseException("Interval end " + end + " is before start " + start, start + "/" + end, 0);
        }
        return new TimeInterval(start, end, new PeriodDuration(null, Duration.between(start, end)));
    }
}
