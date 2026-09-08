package global;

import ch.nvaria.iso8601.RecurringCycle;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import org.exparity.hamcrest.date.ZonedDateTimeMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecurringCycleTest {

    @ParameterizedTest
    @ValueSource(strings = {"R0/PT30S", "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleRepetitions_0(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R/PT30S", "R-1/PT30S","R/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleRepetitions_MAX(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(Integer.MAX_VALUE));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M", "R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleRepetitions_12(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(12));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M", "R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getTriggerDates_12(String iso8601){
        ZonedDateTime now = ZonedDateTime.now();
        List<ZonedDateTime> reps = RecurringCycle.triggerTimesExclusive(iso8601, ZoneId.of("Z"));
        assertThat(reps.getFirst(), is( not(ZonedDateTimeMatchers.within(25, ChronoUnit.SECONDS, now))));
        assertThat(reps.size(), is(12));
    }

    @ParameterizedTest
    @ValueSource(strings = { "R12/PT30S" })
    void getTriggerDatesInclusive_12(String iso8601){
        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        List<ZonedDateTime> reps = RecurringCycle.triggerTimesInclusive(iso8601, zoneId);
        assertThat(reps.getFirst(), is( ZonedDateTimeMatchers.within(1, ChronoUnit.SECONDS, now)));
        assertThat(reps.size(), is(12));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R4/2016-08-23T04:00:00Z/P1D"})
    void getTriggerDatesInclusive_4(String iso8601){
        List<ZonedDateTime> reps = RecurringCycle.triggerTimesInclusive(iso8601, ZoneId.of("Z"));
        assertThat(reps.size(), is(4));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R4/2016-08-23T04:00:00Z/P1D"})
    void getTriggerDatesExclusive_4(String iso8601){
        List<ZonedDateTime> reps = RecurringCycle.triggerTimesExclusive(iso8601, ZoneId.of("Z"));
        assertThat(reps.size(), is(4));
    }




    @ParameterizedTest
    @ValueSource(strings = {"PT30S"})
    void getCycleRepetitions_fail(String iso8601){

        assertThrows(DateTimeParseException.class,
                ()-> RecurringCycle.repetitions(iso8601));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleDuration_(String iso8601){
        PeriodDurationRecord reps = RecurringCycle.periodDuration(iso8601);
        assertThat(reps, is(notNullValue()));
        assertThat(reps.duration(), is(greaterThan(Duration.ofSeconds(25))));

    }


    @ParameterizedTest
    @ValueSource(strings = {"R1/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleStartDate_0(String iso8601){
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601,  ZoneId.of("Z"));
        System.out.println(startDate);
        assertThat(startDate, is(notNullValue()));
        assertThat(startDate.get().getYear(), is(2008));
    }



    @ParameterizedTest
    @ValueSource(strings = {"R1/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleStartDate_ZoneIdBerlin(String iso8601){
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601,  ZoneId.of("Europe/Berlin"));
        System.out.println(startDate.get());
        assertThat(startDate, is(notNullValue()));
        assertThat(startDate.get().getYear(), is(2008));
    }



    @ParameterizedTest
    @ValueSource(strings = {"R1/PT30S","R1/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R1/2008-03-01T13:00:00Z/PT30S"})
    void getCycle_getAll(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601,  ZoneId.of("Europe/Berlin"));
        PeriodDurationRecord pdr = RecurringCycle.periodDuration(iso8601);
        System.out.println(reps);
        System.out.println(startDate);
        System.out.println(pdr.period() + " " + pdr.duration());

        assertThat(reps, is(1));



        assertThat(pdr.duration(), is(notNullValue()));

    }


    // Regression: the grammar once had a '-1' literal token which swallowed the "-1" in any
    // date with month 10-12 or day 10-19 (e.g. 2026-09-15), so those never parsed.

    @ParameterizedTest
    @ValueSource(strings = {
            "R/2026-09-15T10:00:00Z/PT1H",
            "R-1/2026-09-15T10:00:00Z/PT1H",
            "R/2026-10-05T10:00:00Z/PT1H",
            "R/2026-11-19T10:00:00Z/PT1H",
            "R/2026-12-10T10:00:00-10:00/PT1H"})
    void dashOneDates_unbounded(String iso8601) {
        assertThat(RecurringCycle.repetitions(iso8601), is(Integer.MAX_VALUE));
        Optional<ZonedDateTime> startDate = RecurringCycle.startDate(iso8601, ZoneId.of("Z"));
        assertThat(startDate.isPresent(), is(true));
        assertThat(startDate.get().getYear(), is(2026));
        PeriodDurationRecord pdr = RecurringCycle.periodDuration(iso8601);
        assertThat(pdr.duration(), is(Duration.ofHours(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R3/2026-09-15T10:00:00Z/PT1H", "R3/2026-11-19T10:00:00Z/PT1H"})
    void dashOneDates_triggerTimes(String iso8601) {
        ZonedDateTime start = RecurringCycle.startDate(iso8601, ZoneId.of("Z")).orElseThrow();
        assertThat(start.getMonthValue() * 100 + start.getDayOfMonth(), is(anyOf(is(915), is(1119))));
        assertThat(start.getHour(), is(10));

        List<ZonedDateTime> inclusive = RecurringCycle.triggerTimesInclusive(iso8601, ZoneId.of("Z"));
        assertThat(inclusive.size(), is(3));
        assertThat(inclusive.get(0), is(start));
        assertThat(inclusive.get(1), is(start.plusHours(1)));
        assertThat(inclusive.get(2), is(start.plusHours(2)));

        List<ZonedDateTime> exclusive = RecurringCycle.triggerTimesExclusive(iso8601, ZoneId.of("Z"));
        assertThat(exclusive.size(), is(3));
        assertThat(exclusive.get(0), is(start.plusHours(1)));
        assertThat(exclusive.get(2), is(start.plusHours(3)));
    }

    @Test
    void negativeOffset_keepsOffset() {
        ZonedDateTime start = RecurringCycle.startDate("R2/2026-12-10T10:00:00-10:00/PT1H", ZoneId.of("Z")).orElseThrow();
        assertThat(start.getOffset().getTotalSeconds(), is(-10 * 3600));
        assertThat(start.getDayOfMonth(), is(10));
    }

}
