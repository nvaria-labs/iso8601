package global;

import ch.nvaria.iso8601.RecurringCycle;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import ch.nvaria.iso8601.base.models.TimeIntervalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ISO 8601-1 clause 5.6 (recurring time intervals) coverage.
 */
class RecurringCycleCoverageTest {

    private static final ZoneId UTC = ZoneId.of("Z");
    private static final ZonedDateTime START = ZonedDateTime.parse("2026-09-15T10:00:00Z");

    @ParameterizedTest
    @CsvSource({
            // all four interval forms, extended and basic, with an explicit or derivable start
            "R3/2026-09-15T10:00:00Z/PT1H",
            "R3/20260915T100000Z/PT1H",
            "R3/2026-09-15T10:00:00Z/2026-09-15T11:00:00Z",
            "R3/20260915T100000Z/20260915T110000Z",
            "R3/2026-09-15T10:00:00Z/11:00:00",
            "R3/PT1H/2026-09-15T11:00:00Z",
            "R3/PT1H/20260915T110000Z",
            "r3/2026-09-15t10:00:00z/pt1h",
    })
    void allFormsYieldSameTriggers(String iso8601) {
        assertThat(RecurringCycle.repetitions(iso8601), is(3));
        assertThat(RecurringCycle.startDate(iso8601, UTC).orElseThrow(), is(START));
        assertThat(RecurringCycle.periodDuration(iso8601).duration(), is(Duration.ofHours(1)));
        assertThat(RecurringCycle.triggerTimesInclusive(iso8601, UTC),
                contains(START, START.plusHours(1), START.plusHours(2)));
        assertThat(RecurringCycle.triggerTimesExclusive(iso8601, UTC),
                contains(START.plusHours(1), START.plusHours(2), START.plusHours(3)));
    }

    @Test
    void dateOnlyStartMeansStartOfDay() {
        String iso8601 = "R2/2026-09-15/P1D";
        ZonedDateTime midnight = ZonedDateTime.parse("2026-09-15T00:00:00Z");
        assertThat(RecurringCycle.startDate(iso8601, UTC).orElseThrow(), is(midnight));
        assertThat(RecurringCycle.triggerTimesInclusive(iso8601, UTC), contains(midnight, midnight.plusDays(1)));
    }

    @Test
    void calendarStepFollowsMonthLengths() {
        List<ZonedDateTime> triggers = RecurringCycle.triggerTimesInclusive("R4/2026-01-31T00:00:00Z/P1M", UTC);
        assertThat(triggers, contains(
                ZonedDateTime.parse("2026-01-31T00:00:00Z"),
                ZonedDateTime.parse("2026-02-28T00:00:00Z"),
                ZonedDateTime.parse("2026-03-28T00:00:00Z"),
                ZonedDateTime.parse("2026-04-28T00:00:00Z")));
    }

    @Test
    void mixedPeriodAndDurationStep() {
        PeriodDurationRecord pd = RecurringCycle.periodDuration("R2/2026-09-15T10:00:00Z/P1DT12H");
        assertThat(pd.period(), is(Period.ofDays(1)));
        assertThat(pd.duration(), is(Duration.ofHours(12)));
        assertThat(RecurringCycle.triggerTimesExclusive("R2/2026-09-15T10:00:00Z/P1DT12H", UTC),
                contains(START.plusHours(36), START.plusHours(72)));
    }

    @Test
    void startEndFormDurationIsExact() {
        TimeIntervalRecord interval = RecurringCycle.interval("R2/2026-09-15T10:00:00Z/2026-09-16T10:30:00Z", UTC);
        assertThat(interval.start(), is(START));
        assertThat(interval.end(), is(START.plusHours(24).plusMinutes(30)));
        assertThat(interval.duration().period(), is(nullValue()));
        assertThat(interval.duration().duration(), is(Duration.ofMinutes(24 * 60 + 30)));
    }

    @Test
    void durationOnlyFormStartsNow() {
        TimeIntervalRecord interval = RecurringCycle.interval("R2/PT1H", UTC);
        assertThat(interval.start(), is(nullValue()));
        ZonedDateTime before = ZonedDateTime.now(UTC);
        ZonedDateTime start = RecurringCycle.startDate("R2/PT1H", UTC).orElseThrow();
        assertThat(start.isBefore(before.minusSeconds(1)) || start.isAfter(before.plusSeconds(1)), is(false));
    }

    @Test
    void zeroRepetitions() {
        assertThat(RecurringCycle.repetitions("R0/2026-09-15T10:00:00Z/PT1H"), is(0));
        assertThat(RecurringCycle.triggerTimesInclusive("R0/2026-09-15T10:00:00Z/PT1H", UTC), is(empty()));
        assertThat(RecurringCycle.triggerTimesExclusive("R0/2026-09-15T10:00:00Z/PT1H", UTC), is(empty()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R/2026-09-15T10:00:00Z/PT1H", "R-1/2026-09-15T10:00:00Z/PT1H"})
    void unboundedNeedsLimit(String iso8601) {
        assertThat(RecurringCycle.repetitions(iso8601), is(RecurringCycle.UNBOUNDED));
        assertThrows(IllegalArgumentException.class, () -> RecurringCycle.triggerTimesInclusive(iso8601, UTC));
        assertThrows(IllegalArgumentException.class, () -> RecurringCycle.triggerTimesExclusive(iso8601, UTC));

        assertThat(RecurringCycle.triggerTimesInclusive(iso8601, UTC, 2), contains(START, START.plusHours(1)));
        assertThat(RecurringCycle.triggerTimesExclusive(iso8601, UTC, 2), contains(START.plusHours(1), START.plusHours(2)));
        assertThat(RecurringCycle.triggersInclusive(iso8601, UTC).skip(1000).findFirst().orElseThrow(), is(START.plusHours(1000)));
    }

    @Test
    void limitDoesNotExceedRepetitions() {
        assertThat(RecurringCycle.triggerTimesInclusive("R2/2026-09-15T10:00:00Z/PT1H", UTC, 10), contains(START, START.plusHours(1)));
    }

    @Test
    void zoneIdAppliesWhenStartHasNoOffset() {
        ZoneId berlin = ZoneId.of("Europe/Berlin");
        ZonedDateTime start = RecurringCycle.startDate("R2/2026-09-15T10:00:00/PT1H", berlin).orElseThrow();
        assertThat(start, is(ZonedDateTime.of(2026, 9, 15, 10, 0, 0, 0, berlin)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PT1H", "R-2/PT1H", "R5", "R5/", "R5/2026-09-15T10:00:00Z", "R5/2026-09-15T10:00:00Z/PT1H/PT1H",
            "R5//PT1H", "R5/2026-13-01T10:00:00Z/PT1H", "R5/2026-09-15T10:00:00Z/P1.5Y", "R5/2026-09-16T10:00:00Z/2026-09-15T10:00:00Z",
            "Rx/PT1H", "5/PT1H", ""})
    void invalidRecurring(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> RecurringCycle.periodDuration(iso8601));
        assertThrows(DateTimeParseException.class, () -> RecurringCycle.triggerTimesInclusive(iso8601, UTC, 1));
    }
}
