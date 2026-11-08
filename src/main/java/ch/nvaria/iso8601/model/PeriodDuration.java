package ch.nvaria.iso8601.model;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;

/**
 * A duration split into its calendar part (years, months, days) and its exact part (hours, minutes, seconds).
 * Either part may be {@code null} when the input did not contain it.
 *
 * @param period   the calendar part, e.g. {@code P1Y2M3D}, or {@code null}
 * @param duration the exact part, e.g. {@code PT4H5M6S}, or {@code null}
 */
public record PeriodDuration(Period period, Duration duration) {

    /**
     * Adds this duration to a temporal, calendar part first.
     *
     * @param temporal the temporal to add to
     * @param <T>      the temporal type
     * @return the result
     */
    @SuppressWarnings("unchecked")
    public <T extends Temporal> T addTo(T temporal) {
        Temporal t = temporal;
        if (period != null) {
            t = t.plus(period);
        }
        if (duration != null) {
            t = t.plus(duration);
        }
        return (T) t;
    }

    /**
     * Subtracts this duration from a temporal, exact part first (the reverse order of {@link #addTo}).
     *
     * @param temporal the temporal to subtract from
     * @param <T>      the temporal type
     * @return the result
     */
    @SuppressWarnings("unchecked")
    public <T extends Temporal> T subtractFrom(T temporal) {
        Temporal t = temporal;
        if (duration != null) {
            t = t.minus(duration);
        }
        if (period != null) {
            t = t.minus(period);
        }
        return (T) t;
    }

    /**
     * @return this duration with both parts negated
     */
    public PeriodDuration negated() {
        return new PeriodDuration(period == null ? null : period.negated(),
                duration == null ? null : duration.negated());
    }
}
