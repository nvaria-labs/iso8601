/**
 * Parser for ISO 8601-1 dates, times, durations, time intervals and recurring time intervals.
 * <p>
 * Entry points are {@link ch.nvaria.iso8601.Iso8601Date}, {@link ch.nvaria.iso8601.Iso8601Time},
 * {@link ch.nvaria.iso8601.PeriodOfTime} and {@link ch.nvaria.iso8601.RecurringCycle}.
 * Everything under {@code ch.nvaria.iso8601.internal} is implementation detail and not exported.
 */
module ch.nvaria.iso8601 {
    requires org.antlr.antlr4.runtime;

    exports ch.nvaria.iso8601;
    exports ch.nvaria.iso8601.model;
}
