package ch.nvaria.iso8601.internal;

import ch.nvaria.iso8601.internal.parser.Iso8601Parser;
import ch.nvaria.iso8601.model.PeriodDuration;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeParseException;

/**
 * Maps ISO 8601-1 clause 5.5.2 durations to a {@link PeriodDuration}.
 * <ul>
 *   <li>Designator format {@code P1Y2M3DT4H5M6S}, {@code P2W}. The smallest component may carry a
 *       fraction ({@code PT1.5H}, {@code P0.5D}). Fractional years or months have no exact length and are rejected.
 *       Fractional weeks and days are converted to the exact part.</li>
 *   <li>Alternative format {@code P0001-02-03T04:05:06}, {@code P0001-02}, {@code P02-03}, {@code P03}.</li>
 * </ul>
 * The calendar part is {@code null} when the input has no years, months or (whole) days; the exact part
 * is {@code null} when it has no time component. This mirrors what callers relied on before.
 */
public final class DurationMapper {

    private static final BigDecimal NANOS_PER_SECOND = BigDecimal.valueOf(1_000_000_000L);

    private DurationMapper() {
    }

    public static PeriodDuration toPeriodDuration(Iso8601Parser.IntervalContext c) {
        return c.intervalT() != null ? designator(c.intervalT()) : alternative(c.intervalYMD());
    }

    public static PeriodDuration toPeriodDuration(Iso8601Parser.IntervalBasicContext c) {
        return c.intervalT() != null ? designator(c.intervalT()) : alternative(c.intervalYMDBasic());
    }

    public static PeriodDuration toPeriodDuration(Iso8601Parser.IntervalExtendedContext c) {
        return c.intervalT() != null ? designator(c.intervalT()) : alternative(c.intervalYMDExtended());
    }

    // ---------------------------------------------------------------- P1Y2M3DT4H5M6S

    private static PeriodDuration designator(Iso8601Parser.IntervalTContext c) {
        BigDecimal years = null, months = null, weeks = null, days = null, hours = null, minutes = null, seconds = null;
        boolean afterT = false;
        BigDecimal pending = null;

        for (int i = 0; i < c.getChildCount(); i++) {
            ParseTree child = c.getChild(i);
            if (child instanceof TerminalNode) {
                switch (child.getText().toUpperCase()) {
                    case "P" -> { }
                    case "T" -> afterT = true;
                    case "Y" -> years = pending;
                    case "M" -> { if (afterT) minutes = pending; else months = pending; }
                    case "W" -> weeks = pending;
                    case "D" -> days = pending;
                    case "H" -> hours = pending;
                    case "S" -> seconds = pending;
                    default -> throw new DateTimeParseException("Unexpected designator " + child.getText(), c.getText(), 0);
                }
            } else {
                pending = DateTimeMapper.decimal((org.antlr.v4.runtime.ParserRuleContext) child);
            }
        }

        if (!isWhole(years) || !isWhole(months)) {
            throw new DateTimeParseException("Fractional years or months have no exact length: " + c.getText(), c.getText(), 0);
        }

        boolean hasPeriod = years != null || months != null || (weeks != null && isWhole(weeks)) || (days != null && isWhole(days));
        Period period = null;
        if (hasPeriod) {
            int wholeDays = (days != null && isWhole(days) ? days.intValueExact() : 0)
                    + (weeks != null && isWhole(weeks) ? weeks.intValueExact() * 7 : 0);
            period = Period.of(intOrZero(years), intOrZero(months), wholeDays);
        }

        BigDecimal exactSeconds = BigDecimal.ZERO;
        boolean hasDuration = hours != null || minutes != null || seconds != null;
        if (weeks != null && !isWhole(weeks)) {
            exactSeconds = exactSeconds.add(weeks.multiply(BigDecimal.valueOf(7L * 86400)));
            hasDuration = true;
        }
        if (days != null && !isWhole(days)) {
            exactSeconds = exactSeconds.add(days.multiply(BigDecimal.valueOf(86400)));
            hasDuration = true;
        }
        if (hours != null) {
            exactSeconds = exactSeconds.add(hours.multiply(BigDecimal.valueOf(3600)));
        }
        if (minutes != null) {
            exactSeconds = exactSeconds.add(minutes.multiply(BigDecimal.valueOf(60)));
        }
        if (seconds != null) {
            exactSeconds = exactSeconds.add(seconds);
        }
        Duration duration = hasDuration ? toDuration(exactSeconds) : null;

        return new PeriodDuration(period, duration);
    }

    // ---------------------------------------------------------------- P0001-02-03T04:05:06

    private static PeriodDuration alternative(Iso8601Parser.IntervalYMDContext c) {
        return c.intervalYMDBasic() != null ? alternative(c.intervalYMDBasic()) : alternative(c.intervalYMDExtended());
    }

    private static PeriodDuration alternative(Iso8601Parser.IntervalYMDBasicContext c) {
        var t = c.intervalYMDTimeBasic();
        if (t.datetimeBasic() != null) {
            var dt = t.datetimeBasic();
            if (dt.timeZoneBasic() != null) {
                throw new DateTimeParseException("A duration cannot carry a time zone: " + c.getText(), c.getText(), 0);
            }
            return build(DateTimeMapper.rawDate(dt.datePreciseBasic()), DateTimeMapper.rawTime(dt.localTimeBasic()), c.getText());
        }
        if (t.dateBasic() != null) {
            return build(DateTimeMapper.rawDate(t.dateBasic()), null, c.getText());
        }
        if (t.monthDateBasic() != null) {
            var md = t.monthDateBasic();
            return build(new DateTimeMapper.RawDate(0, DateTimeMapper.num(md.month()), DateTimeMapper.num(md.day())), null, c.getText());
        }
        if (t.day() != null) {
            return build(new DateTimeMapper.RawDate(0, 0, DateTimeMapper.num(t.day())), null, c.getText());
        }
        return build(null, DateTimeMapper.rawTime(t.localTimeBasic()), c.getText());
    }

    private static PeriodDuration alternative(Iso8601Parser.IntervalYMDExtendedContext c) {
        var t = c.intervalYMDTimeExtended();
        if (t.datetimeExtended() != null) {
            var dt = t.datetimeExtended();
            if (dt.timeZoneExtended() != null) {
                throw new DateTimeParseException("A duration cannot carry a time zone: " + c.getText(), c.getText(), 0);
            }
            return build(DateTimeMapper.rawDate(dt.datePreciseExtended()), DateTimeMapper.rawTime(dt.localTimeExtended()), c.getText());
        }
        if (t.dateExtended() != null) {
            return build(DateTimeMapper.rawDate(t.dateExtended()), null, c.getText());
        }
        if (t.monthDateExtended() != null) {
            var md = t.monthDateExtended();
            return build(new DateTimeMapper.RawDate(0, DateTimeMapper.num(md.month()), DateTimeMapper.num(md.day())), null, c.getText());
        }
        if (t.day() != null) {
            return build(new DateTimeMapper.RawDate(0, 0, DateTimeMapper.num(t.day())), null, c.getText());
        }
        return build(null, DateTimeMapper.rawTime(t.localTimeExtended()), c.getText());
    }

    private static PeriodDuration build(DateTimeMapper.RawDate date, DateTimeMapper.RawTime time, String text) {
        // 5.5.2.4: components of the alternative format must stay within the range of the
        // corresponding date and time components (P0000-13-00 is not 13 months)
        if (date != null && (date.month() > 12 || date.day() > 31)) {
            throw new DateTimeParseException("Month or day out of range in alternative format: " + text, text, 0);
        }
        if (time != null && (time.hour() > 24 || time.minute() > 59 || time.second().compareTo(BigDecimal.valueOf(60)) >= 0)) {
            throw new DateTimeParseException("Time component out of range in alternative format: " + text, text, 0);
        }
        Period period = date == null ? null : Period.of(date.year(), date.month(), date.day());
        Duration duration = time == null ? null : toDuration(time.totalSeconds());
        if (period == null && duration == null) {
            throw new DateTimeParseException("Empty duration: " + text, text, 0);
        }
        return new PeriodDuration(period, duration);
    }

    // ---------------------------------------------------------------- helpers

    private static Duration toDuration(BigDecimal seconds) {
        BigDecimal nanos = seconds.multiply(NANOS_PER_SECOND).setScale(0, RoundingMode.DOWN);
        BigDecimal[] parts = nanos.divideAndRemainder(NANOS_PER_SECOND);
        return Duration.ofSeconds(parts[0].longValueExact(), parts[1].longValueExact());
    }

    private static boolean isWhole(BigDecimal value) {
        return value == null || value.stripTrailingZeros().scale() <= 0;
    }

    private static int intOrZero(BigDecimal value) {
        return value == null ? 0 : value.intValueExact();
    }
}
