package ch.nvaria.iso8601.base.mappers;

import ch.nvaria.generated.Iso8601Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;

/**
 * Maps date, time and time zone parse trees to {@code java.time} values.
 * <p>
 * Reduced precision dates ({@code 2026-09}, {@code 2026}, {@code 20}, {@code 2026-W38}) resolve to the
 * first day of the month, year, century or week. Fractions on the smallest time component are honoured
 * ({@code 10:30.5} is 10:30:30). The end-of-day representation {@code 24:00:00} rolls over to
 * 00:00:00 of the following day.
 */
public final class DateTimeMapper {

    private static final BigDecimal SIXTY = BigDecimal.valueOf(60);
    private static final BigDecimal NANOS_PER_SECOND = BigDecimal.valueOf(1_000_000_000L);

    private DateTimeMapper() {
    }

    // ---------------------------------------------------------------- raw fields

    /**
     * Raw calendar fields of a date without range validation. Used for the alternative
     * duration format {@code P0001-02-03}, where 00 is a legal month.
     *
     * @param year  years
     * @param month months
     * @param day   days
     */
    public record RawDate(int year, int month, int day) {
    }

    /**
     * Raw fields of a time of day with fractions expanded into the lower components.
     * {@code 10.5} becomes hour 10, minute 30; {@code 10:30.5} becomes 10:30:30.
     *
     * @param hour   hours
     * @param minute minutes
     * @param second seconds including any fraction
     */
    public record RawTime(int hour, int minute, BigDecimal second) {

        public BigDecimal totalSeconds() {
            return BigDecimal.valueOf(hour * 3600L + minute * 60L).add(second);
        }
    }

    public static RawDate rawDate(Iso8601Parser.DateBasicContext c) {
        if (c.datePreciseBasic() != null) {
            return rawDate(c.datePreciseBasic());
        }
        if (c.specificMonthCalendarDate() != null) {
            return new RawDate(year(c.specificMonthCalendarDate().year()), num(c.specificMonthCalendarDate().month()), 0);
        }
        if (c.specificYearCalendarDate() != null) {
            return new RawDate(year(c.specificYearCalendarDate().year()), 0, 0);
        }
        throw new DateTimeParseException("Only calendar dates are allowed here: " + c.getText(), c.getText(), 0);
    }

    public static RawDate rawDate(Iso8601Parser.DateExtendedContext c) {
        if (c.datePreciseExtended() != null) {
            return rawDate(c.datePreciseExtended());
        }
        if (c.specificMonthCalendarDate() != null) {
            return new RawDate(year(c.specificMonthCalendarDate().year()), num(c.specificMonthCalendarDate().month()), 0);
        }
        if (c.specificYearCalendarDate() != null) {
            return new RawDate(year(c.specificYearCalendarDate().year()), 0, 0);
        }
        throw new DateTimeParseException("Only calendar dates are allowed here: " + c.getText(), c.getText(), 0);
    }

    public static RawDate rawDate(Iso8601Parser.DatePreciseBasicContext c) {
        if (c.calendarDateBasic() != null) {
            var d = c.calendarDateBasic();
            return new RawDate(year(d.year()), num(d.month()), num(d.day()));
        }
        throw new DateTimeParseException("Only calendar dates are allowed here: " + c.getText(), c.getText(), 0);
    }

    public static RawDate rawDate(Iso8601Parser.DatePreciseExtendedContext c) {
        if (c.calendarDateExtended() != null) {
            var d = c.calendarDateExtended();
            return new RawDate(year(d.year()), num(d.month()), num(d.day()));
        }
        throw new DateTimeParseException("Only calendar dates are allowed here: " + c.getText(), c.getText(), 0);
    }

    public static RawTime rawTime(Iso8601Parser.LocalTimeContext c) {
        return c.localTimeBasic() != null ? rawTime(c.localTimeBasic()) : rawTime(c.localTimeExtended());
    }

    public static RawTime rawTime(Iso8601Parser.LocalTimeBasicContext c) {
        if (c.localTimePreciseBasic() != null) {
            var t = c.localTimePreciseBasic();
            return new RawTime(num(t.hour()), num(t.minute()), decimal(t.secondFraction()));
        }
        if (c.specificMinuteLocalTimeBasic() != null) {
            var t = c.specificMinuteLocalTimeBasic();
            return hourMinuteFraction(num(t.hour()), decimal(t.minuteFraction()));
        }
        return hourFraction(decimal(c.specificHourLocalTime().hourFraction()));
    }

    public static RawTime rawTime(Iso8601Parser.LocalTimeExtendedContext c) {
        if (c.localTimePreciseExtended() != null) {
            var t = c.localTimePreciseExtended();
            return new RawTime(num(t.hour()), num(t.minute()), decimal(t.secondFraction()));
        }
        if (c.specificMinuteLocalTimeExtended() != null) {
            var t = c.specificMinuteLocalTimeExtended();
            return hourMinuteFraction(num(t.hour()), decimal(t.minuteFraction()));
        }
        return hourFraction(decimal(c.specificHourLocalTime().hourFraction()));
    }

    private static RawTime hourMinuteFraction(int hour, BigDecimal minute) {
        int wholeMinutes = minute.intValue();
        BigDecimal seconds = minute.subtract(BigDecimal.valueOf(wholeMinutes)).multiply(SIXTY);
        return new RawTime(hour, wholeMinutes, seconds);
    }

    private static RawTime hourFraction(BigDecimal hour) {
        int wholeHours = hour.intValue();
        BigDecimal minutes = hour.subtract(BigDecimal.valueOf(wholeHours)).multiply(SIXTY);
        return hourMinuteFraction(wholeHours, minutes);
    }

    // ---------------------------------------------------------------- dates

    public static LocalDate toLocalDate(Iso8601Parser.DateContext c) {
        return c.dateBasic() != null ? toLocalDate(c.dateBasic()) : toLocalDate(c.dateExtended());
    }

    public static LocalDate toLocalDate(Iso8601Parser.DateBasicContext c) {
        if (c.datePreciseBasic() != null) {
            return toLocalDate(c.datePreciseBasic());
        }
        if (c.specificWeekWeekDateBasic() != null) {
            var w = c.specificWeekWeekDateBasic();
            return weekDate(year(w.year()), num(w.week()), 1);
        }
        return reducedPrecision(c.specificMonthCalendarDate(), c.specificYearCalendarDate(), c.specificCenturyCalendarDate());
    }

    public static LocalDate toLocalDate(Iso8601Parser.DateExtendedContext c) {
        if (c.datePreciseExtended() != null) {
            return toLocalDate(c.datePreciseExtended());
        }
        if (c.specificWeekWeekDateExtended() != null) {
            var w = c.specificWeekWeekDateExtended();
            return weekDate(year(w.year()), num(w.week()), 1);
        }
        return reducedPrecision(c.specificMonthCalendarDate(), c.specificYearCalendarDate(), c.specificCenturyCalendarDate());
    }

    public static LocalDate toLocalDate(Iso8601Parser.DatePreciseBasicContext c) {
        if (c.calendarDateBasic() != null) {
            var d = c.calendarDateBasic();
            return LocalDate.of(year(d.year()), num(d.month()), num(d.day()));
        }
        if (c.ordinalDateBasic() != null) {
            var d = c.ordinalDateBasic();
            return LocalDate.ofYearDay(year(d.year()), num(d.ordinalDay()));
        }
        var w = c.weekDateBasic();
        return weekDate(year(w.year()), num(w.week()), num(w.weekDay()));
    }

    public static LocalDate toLocalDate(Iso8601Parser.DatePreciseExtendedContext c) {
        if (c.calendarDateExtended() != null) {
            var d = c.calendarDateExtended();
            return LocalDate.of(year(d.year()), num(d.month()), num(d.day()));
        }
        if (c.ordinalDateExtended() != null) {
            var d = c.ordinalDateExtended();
            return LocalDate.ofYearDay(year(d.year()), num(d.ordinalDay()));
        }
        var w = c.weekDateExtended();
        return weekDate(year(w.year()), num(w.week()), num(w.weekDay()));
    }

    private static LocalDate reducedPrecision(Iso8601Parser.SpecificMonthCalendarDateContext month,
                                              Iso8601Parser.SpecificYearCalendarDateContext year,
                                              Iso8601Parser.SpecificCenturyCalendarDateContext century) {
        if (month != null) {
            return LocalDate.of(year(month.year()), num(month.month()), 1);
        }
        if (year != null) {
            return LocalDate.of(year(year.year()), 1, 1);
        }
        return LocalDate.of(Integer.parseInt(century.getText()) * 100, 1, 1);
    }

    private static LocalDate weekDate(int year, int week, int weekDay) {
        // Jan 4th is always in week 1 of its week-based year
        return LocalDate.of(year, 1, 4)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                .with(ChronoField.DAY_OF_WEEK, weekDay);
    }

    // ---------------------------------------------------------------- times

    /**
     * A validated time of day. {@code 24:00:00} maps to midnight with {@code extraDays == 1}.
     *
     * @param time      the time of day
     * @param extraDays 1 for the end-of-day representation, else 0
     */
    public record TimeOfDay(LocalTime time, int extraDays) {
    }

    public static TimeOfDay toTimeOfDay(RawTime raw) {
        boolean endOfDay = raw.hour() == 24;
        if (raw.hour() < 0 || raw.hour() > 24 || raw.minute() < 0 || raw.minute() > 59
                || raw.second().signum() < 0 || raw.second().compareTo(SIXTY) >= 0
                || (endOfDay && (raw.minute() != 0 || raw.second().signum() != 0))) {
            throw new DateTimeParseException("Invalid time of day " + raw.hour() + ":" + raw.minute() + ":" + raw.second(), "", 0);
        }
        if (endOfDay) {
            return new TimeOfDay(LocalTime.MIDNIGHT, 1);
        }
        long nanos = raw.totalSeconds().multiply(NANOS_PER_SECOND).setScale(0, RoundingMode.DOWN).longValueExact();
        return new TimeOfDay(LocalTime.ofNanoOfDay(nanos), 0);
    }

    public static TimeOfDay toTimeOfDay(Iso8601Parser.LocalTimeContext c) {
        return toTimeOfDay(rawTime(c));
    }

    public static TimeOfDay toTimeOfDay(Iso8601Parser.LocalTimeBasicContext c) {
        return toTimeOfDay(rawTime(c));
    }

    public static TimeOfDay toTimeOfDay(Iso8601Parser.LocalTimeExtendedContext c) {
        return toTimeOfDay(rawTime(c));
    }

    // ---------------------------------------------------------------- zones

    public static ZoneOffset toOffset(Iso8601Parser.TimeZoneContext c) {
        return c.timeZoneBasic() != null ? toOffset(c.timeZoneBasic()) : toOffset(c.timeZoneExtended());
    }

    public static ZoneOffset toOffset(Iso8601Parser.TimeZoneBasicContext c) {
        if (c.timeZoneUtc() != null) {
            return ZoneOffset.UTC;
        }
        return offset(c, num(c.hour()), c.minute() == null ? 0 : num(c.minute()));
    }

    public static ZoneOffset toOffset(Iso8601Parser.TimeZoneExtendedContext c) {
        if (c.timeZoneUtc() != null) {
            return ZoneOffset.UTC;
        }
        return offset(c, num(c.hour()), c.minute() == null ? 0 : num(c.minute()));
    }

    private static ZoneOffset offset(ParserRuleContext c, int hours, int minutes) {
        int sign = c.getChild(0).getText().equals("-") ? -1 : 1;
        return ZoneOffset.ofHoursMinutes(sign * hours, sign * minutes);
    }

    // ---------------------------------------------------------------- date + time

    /**
     * Combines date, time and zone. An offset in the input wins over {@code fallback}.
     *
     * @param c        the parse tree
     * @param fallback zone to apply when the input carries no offset
     * @return the zoned date time
     */
    public static ZonedDateTime toZonedDateTime(Iso8601Parser.DatetimeContext c, ZoneId fallback) {
        return c.datetimeBasic() != null ? toZonedDateTime(c.datetimeBasic(), fallback) : toZonedDateTime(c.datetimeExtended(), fallback);
    }

    public static ZonedDateTime toZonedDateTime(Iso8601Parser.DatetimeBasicContext c, ZoneId fallback) {
        LocalDate date = toLocalDate(c.datePreciseBasic());
        TimeOfDay time = toTimeOfDay(c.localTimeBasic());
        ZoneId zone = c.timeZoneBasic() != null ? toOffset(c.timeZoneBasic()) : fallback;
        return combine(date, time, zone);
    }

    public static ZonedDateTime toZonedDateTime(Iso8601Parser.DatetimeExtendedContext c, ZoneId fallback) {
        LocalDate date = toLocalDate(c.datePreciseExtended());
        TimeOfDay time = toTimeOfDay(c.localTimeExtended());
        ZoneId zone = c.timeZoneExtended() != null ? toOffset(c.timeZoneExtended()) : fallback;
        return combine(date, time, zone);
    }

    public static ZonedDateTime combine(LocalDate date, TimeOfDay time, ZoneId zone) {
        return LocalDateTime.of(date.plusDays(time.extraDays()), time.time()).atZone(zone);
    }

    // ---------------------------------------------------------------- numbers

    public static int year(Iso8601Parser.YearContext c) {
        // "+012026" and "-0001" are expanded representations, Integer.parseInt handles the sign
        return Integer.parseInt(c.getText());
    }

    public static int num(ParserRuleContext c) {
        return Integer.parseInt(c.getText());
    }

    public static BigDecimal decimal(ParserRuleContext c) {
        return new BigDecimal(c.getText().replace(',', '.'));
    }
}
