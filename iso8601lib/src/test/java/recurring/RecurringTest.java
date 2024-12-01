package recurring;

import ch.nvaria.iso8601.RecurringCycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecurringTest {

    @ParameterizedTest
    @ValueSource(strings = {"R/PT30S", "R/2008-03-01T13:00:00Z/P1Y2M10DT2H30M", "R0/PT30S", "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleRepetitions_0(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(0));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M", "R12/PT30S", "R12/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleRepetitions_12(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(12));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R-1/PT30S"})
    void getCycleRepetitions_unbound(String iso8601){
        Integer reps = RecurringCycle.repetitions(iso8601);
        assertThat(reps, is(Integer.MAX_VALUE));
    }


    @ParameterizedTest
    @ValueSource(strings = {"PT30S"})
    void getCycleRepetitions_fail(String iso8601){

        assertThrows(NumberFormatException.class,
                ()-> RecurringCycle.repetitions(iso8601));
    }


    @ParameterizedTest
    @ValueSource(strings = {"R1/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleStartDate_0(String iso8601){
        ZonedDateTime startDate = RecurringCycle.startDate(iso8601,  ZoneId.of("Z"));
        System.out.println(startDate);
        assertThat(startDate, is(notNullValue()));
        assertThat(startDate.getYear(), is(2008));
    }



    @ParameterizedTest
    @ValueSource(strings = {"R1/2008-03-01T13:00:00Z/P1Y2M10DT2H30M",  "R0/2008-03-01T13:00:00Z/P1Y2M10DT2H30M"})
    void getCycleStartDate_ZoneIdBerlin(String iso8601){
        ZonedDateTime startDate = RecurringCycle.startDate(iso8601,  ZoneId.of("Europe/Berlin"));
        System.out.println(startDate);
        assertThat(startDate, is(notNullValue()));
        assertThat(startDate.getYear(), is(2008));
    }

    @ParameterizedTest
    @ValueSource(strings = {"R1/P1Y2M10DT2H30M"})
    void getCycleStartDate_Null(String iso8601){

        assertThrows(DateTimeParseException.class,
                ()-> RecurringCycle.startDate(iso8601,  ZoneId.of("Z")));
    }

}
