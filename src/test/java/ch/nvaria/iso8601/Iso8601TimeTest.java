package ch.nvaria.iso8601;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ISO 8601-1 clause 5.3 (time of day) coverage.
 */
class Iso8601TimeTest {

    @ParameterizedTest
    @CsvSource({
            "10:00:00,      10:00:00",
            "T10:00:00,     10:00:00",
            "100000,        10:00:00",
            "T100000,       10:00:00",
            "10:00,         10:00:00",
            "1000,          10:00:00",
            "10,            10:00:00",
            "10.5,          10:30:00",
            "'10,5',        10:30:00",
            "10:30.5,       10:30:30",
            "1030.5,        10:30:30",
            "10:30:15.25,   10:30:15.25",
            "103015.25,     10:30:15.25",
            "10:00:00Z,     10:00:00",
            "10:00:00+02:00, 10:00:00",
            "100000+0200,   10:00:00",
            "00:00:00,      00:00:00",
            "23:59:59.999999999, 23:59:59.999999999",
    })
    void times(String iso8601, String expected) {
        assertThat(Iso8601Time.time(iso8601), is(LocalTime.parse(expected)));
    }

    @ParameterizedTest
    @CsvSource({
            "10:00:00,       ",
            "10:00:00Z,      Z",
            "10:00:00+02:00, +02:00",
            "10:00:00-05,    -05:00",
            "100000+0200,    +02:00",
            "100000-0530,    -05:30",
    })
    void offsets(String iso8601, String expected) {
        Optional<ZoneOffset> expectedOffset = Optional.ofNullable(expected).map(ZoneOffset::of);
        assertThat(Iso8601Time.offset(iso8601), is(expectedOffset));
    }

    @Test
    void offsetTime() {
        assertThat(Iso8601Time.offsetTime("10:00:00+02:00", ZoneOffset.UTC), is(OffsetTime.parse("10:00:00+02:00")));
        assertThat(Iso8601Time.offsetTime("10:00:00", ZoneOffset.ofHours(1)), is(OffsetTime.parse("10:00:00+01:00")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"24:00:00", "24:00", "25:00", "10:60", "10:00:60", "10:00:00.", "10:00:00 Z", "", "2026-09-15", "P1D", "10:00:00+02:0"})
    void invalidTimes(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> Iso8601Time.time(iso8601));
    }
}
