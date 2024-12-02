package global;

import ch.nvaria.iso8601.Iso8601Date;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class Iso8601DateTest {


    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05T14:30", "2007-04-05"})
    void getDate(String iso8601){

        ZonedDateTime dateTime = Iso8601Date.date(iso8601, ZoneId.of("Europe/Berlin"));
        assertThat(dateTime, is(notNullValue()));
        assertThat(dateTime.getHour(), is(notNullValue()));

    }


    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05T14:30", "2007-04-05"})
    void getDate_ZoneIdString(String iso8601){

        ZonedDateTime dateTime = Iso8601Date.date(iso8601, "Europe/Berlin");
        assertThat(dateTime, is(notNullValue()));
        assertThat(dateTime.getHour(), is(notNullValue()));

    }
}
