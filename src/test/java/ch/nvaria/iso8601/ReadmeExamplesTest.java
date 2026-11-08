package ch.nvaria.iso8601;

import ch.nvaria.iso8601.model.PeriodDuration;
import ch.nvaria.iso8601.model.TimeInterval;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

/**
 * The examples shown in the README's Quick start, with the results the README claims.
 * Keep both in sync.
 */
class ReadmeExamplesTest {

    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

    @Test
    void dates() {
        assertThat(Iso8601Date.date("20260915T1000Z", BERLIN).toString(), is("2026-09-15T10:00Z"));
        assertThat(Iso8601Date.date("2026-W38-2T10:00", BERLIN).toString(), is("2026-09-15T10:00+02:00[Europe/Berlin]"));
        assertThat(Iso8601Date.localDate("2026-258"), is(LocalDate.of(2026, 9, 15)));
        assertThat(Iso8601Time.time("10:30,5"), is(LocalTime.of(10, 30, 30)));
    }

    @Test
    void durationsAndIntervals() {
        PeriodDuration d = PeriodOfTime.duration("P1Y2M3DT4H");
        assertThat(d.period(), is(Period.of(1, 2, 3)));
        assertThat(d.duration(), is(Duration.ofHours(4)));

        TimeInterval i = PeriodOfTime.interval("2026-09-15/17", ZoneOffset.UTC);
        assertThat(i.start().toString(), is("2026-09-15T00:00Z"));
        assertThat(i.end().toString(), is("2026-09-17T00:00Z"));
        assertThat(i.duration().duration(), is(Duration.ofHours(48)));
    }

    @Test
    void recurringIntervals() {
        List<ZonedDateTime> runs = RecurringCycle.triggerTimesInclusive("R3/2026-09-15T10:00:00Z/P1D", ZoneOffset.UTC);
        assertThat(runs.stream().map(ZonedDateTime::toString).toList(),
                contains("2026-09-15T10:00Z", "2026-09-16T10:00Z", "2026-09-17T10:00Z"));

        assertThat(RecurringCycle.repetitions("R/PT1H"), is(RecurringCycle.UNBOUNDED));
        List<ZonedDateTime> firstThree = RecurringCycle.triggersInclusive("R/2026-09-15T10:00:00Z/PT1H", ZoneOffset.UTC)
                .limit(3).toList();
        assertThat(firstThree.stream().map(ZonedDateTime::toString).toList(),
                contains("2026-09-15T10:00Z", "2026-09-15T11:00Z", "2026-09-15T12:00Z"));
    }
}
