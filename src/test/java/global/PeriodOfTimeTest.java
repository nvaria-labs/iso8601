package global;

import ch.nvaria.iso8601.PeriodOfTime;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PeriodOfTimeTest {


    @ParameterizedTest
    @ValueSource(strings = {"PT10H4M30S", "P3Y6M4DT12H30M50S"})
    void getPeriodOfTime_Duration(String iso8601){
      PeriodDurationRecord result = PeriodOfTime.duration(iso8601);
      assertThat(result.duration().getSeconds(), is(greaterThan(30L)));
    }


    @ParameterizedTest
    @ValueSource(strings = {"PT10H4M30S", "P3Y6M4DT12H30M50S"})
    void getPeriodOfTime_DurationInMillisLDT(String iso8601){
        long result = PeriodOfTime.durationInMillisFrom(iso8601, LocalDateTime.now());
        System.out.println(result);
        assertThat(result, is(greaterThan(40000L)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PT10H4M30S", "P3Y6M4DT12H30M50S"})
    void getPeriodOfTime_DurationInMillisZDT(String iso8601){
        long result = PeriodOfTime.durationInMillisFrom(iso8601, ZonedDateTime.now());
        System.out.println(result);
        assertThat(result, is(greaterThan(40000L)));
    }

    // A leading minus negates the entire interval per ISO 8601. The grammar's `intervalT` rule
    // does not capture the sign, so it is preprocessed in PeriodOfTime.duration; these tests
    // pin that behavior.

    @Test
    void parses_negative_year_month_duration() {
        var r = PeriodOfTime.duration("-P1Y8M");
        assertEquals(Period.of(-1, -8, 0), r.period());
        assertNull(r.duration());
    }

    @Test
    void parses_negative_day_time_duration() {
        var r = PeriodOfTime.duration("-PT2H30M");
        assertNull(r.period());
        assertEquals(Duration.ofMinutes(-150), r.duration());
    }

    @Test
    void parses_negative_combined_duration() {
        var r = PeriodOfTime.duration("-P1DT2H");
        assertEquals(Period.of(0, 0, -1), r.period());
        assertEquals(Duration.ofHours(-2), r.duration());
    }

    @Test
    void positive_duration_unchanged() {
        var r = PeriodOfTime.duration("P3Y6M4DT12H30M50S");
        assertEquals(Period.of(3, 6, 4), r.period());
        assertEquals(Duration.parse("PT12H30M50S"), r.duration());
    }
}
