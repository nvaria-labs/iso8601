package global;

import ch.nvaria.iso8601.PeriodOfTime;
import ch.nvaria.iso8601.base.models.PeriodDurationRecord;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

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

}
