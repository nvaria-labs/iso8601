package ch.nvaria.iso8601.model;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * A time interval as defined in ISO 8601-1 clause 5.5.
 * <p>
 * Depending on the input, not every part is known:
 * <ul>
 *   <li>{@code start/end}: all three parts are set, the duration is the exact {@link java.time.Duration} between them</li>
 *   <li>{@code start/duration} and {@code duration/end}: all three parts are set</li>
 *   <li>{@code duration} alone: only {@code duration} is set, {@code start} and {@code end} are {@code null}</li>
 * </ul>
 *
 * @param start    the start of the interval, or {@code null} for a bare duration
 * @param end      the end of the interval, or {@code null} for a bare duration
 * @param duration the length of the interval, never {@code null}
 */
public record TimeInterval(ZonedDateTime start, ZonedDateTime end, PeriodDuration duration) {

    public Optional<ZonedDateTime> optionalStart() {
        return Optional.ofNullable(start);
    }

    public Optional<ZonedDateTime> optionalEnd() {
        return Optional.ofNullable(end);
    }
}
