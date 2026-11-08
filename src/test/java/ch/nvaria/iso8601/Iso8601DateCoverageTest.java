package ch.nvaria.iso8601;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ISO 8601-1 clause 5.2 (dates) and 5.4 (date and time) coverage.
 */
class Iso8601DateCoverageTest {

    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

    // ---------------------------------------------------------------- 5.2 dates

    @ParameterizedTest
    @CsvSource({
            "2026-09-15,   2026-09-15",   // calendar extended
            "20260915,     2026-09-15",   // calendar basic
            "2026-09,      2026-09-01",   // reduced: month
            "2026,         2026-01-01",   // reduced: year
            "20,           2000-01-01",   // reduced: century
            "2026-258,     2026-09-15",   // ordinal extended
            "2026258,      2026-09-15",   // ordinal basic
            "2026-W38-2,   2026-09-15",   // week extended
            "2026W382,     2026-09-15",   // week basic
            "2026-W38,     2026-09-14",   // reduced: week
            "2026W38,      2026-09-14",
            "2027-W01-1,   2027-01-04",   // 2027-01-04 is the Monday of week 1
            "2020-W53-5,   2021-01-01",   // 53-week year spilling into next calendar year
            "+012026-09-15, +12026-09-15", // expanded year
            "-0001-01-01,  -0001-01-01",
            "2024-02-29,   2024-02-29",   // leap day
    })
    void dates(String iso8601, String expected) {
        assertThat(Iso8601Date.localDate(iso8601), is(LocalDate.parse(expected)));
        assertThat(Iso8601Date.date(iso8601, BERLIN), is(LocalDate.parse(expected).atStartOfDay(BERLIN)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-13-01", "2026-00-10", "2026-02-30", "2023-02-29", "2026-367", "2026-W54-1", "2026-W38-0", "2026-W38-8",
            "2026-9-15", "2026-09-15garbage", "26-09-15", "2026/09/15", "", "P1D", "10:00:00"})
    void invalidDates(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> Iso8601Date.localDate(iso8601));
        assertThrows(DateTimeParseException.class, () -> Iso8601Date.date(iso8601, BERLIN));
    }

    @Test
    void nullInput() {
        assertThrows(DateTimeParseException.class, () -> Iso8601Date.date(null, BERLIN));
    }

    // ---------------------------------------------------------------- 5.4 date and time

    @ParameterizedTest
    @CsvSource({
            "2026-09-15T10:00:00Z,        2026-09-15T10:00:00Z",
            "2026-09-15T10:00:00+02:00,   2026-09-15T10:00:00+02:00",
            "2026-09-15T10:00:00-10:00,   2026-09-15T10:00:00-10:00",
            "2026-09-15T10:00:00+02,      2026-09-15T10:00:00+02:00",
            "20260915T100000Z,            2026-09-15T10:00:00Z",           // basic
            "20260915T100000+0200,        2026-09-15T10:00:00+02:00",      // basic with basic offset
            "2026-09-15T10:00Z,           2026-09-15T10:00:00Z",           // reduced: minute
            "2026-09-15T10Z,              2026-09-15T10:00:00Z",           // reduced: hour
            "2026-09-15T10:00:00.5Z,      2026-09-15T10:00:00.5Z",         // fraction dot
            "2026-09-15T10:00:00.123456789Z, 2026-09-15T10:00:00.123456789Z",
            "'2026-09-15T10:00:00,5Z',    2026-09-15T10:00:00.5Z",         // fraction comma (ISO preferred)
            "2026-09-15T10:30.5Z,         2026-09-15T10:30:30Z",           // fraction on minute
            "2026-09-15T10.5Z,            2026-09-15T10:30:00Z",           // fraction on hour
            "2026-09-15T10.25Z,           2026-09-15T10:15:00Z",
            "2026-09-15T24:00:00Z,        2026-09-16T00:00:00Z",           // end of day
            "2026-258T10:00:00Z,          2026-09-15T10:00:00Z",           // ordinal date with time
            "2026-W38-2T10:00:00Z,        2026-09-15T10:00:00Z",           // week date with time
            "2026-w38-2t10:00:00z,         2026-09-15T10:00:00Z",           // lower case designators
    })
    void dateTimesWithOffset(String iso8601, String expected) {
        ZonedDateTime actual = Iso8601Date.date(iso8601, BERLIN);
        assertThat(actual.toInstant(), is(ZonedDateTime.parse(expected).toInstant()));
        assertThat(actual.getOffset(), is(ZonedDateTime.parse(expected).getOffset()));
    }

    @Test
    void offsetInStringWinsOverZoneId() {
        ZonedDateTime actual = Iso8601Date.date("2026-09-15T10:00:00Z", BERLIN);
        assertThat(actual.getZone(), is(ZoneOffset.UTC));
        assertThat(actual.getHour(), is(10));
    }

    @Test
    void noOffsetUsesZoneId() {
        ZonedDateTime actual = Iso8601Date.date("2026-09-15T10:00:00", BERLIN);
        assertThat(actual.getZone(), is(BERLIN));
        assertThat(actual.getHour(), is(10));
        assertThat(actual.getOffset(), is(ZoneOffset.ofHours(2)));
    }

    @Test
    void noOffsetBasicFormatUsesZoneId() {
        ZonedDateTime actual = Iso8601Date.date("20260915T1000", BERLIN);
        assertThat(actual, is(ZonedDateTime.of(2026, 9, 15, 10, 0, 0, 0, BERLIN)));
    }

    // ---------------------------------------------------------------- java.time extension: [zone id]

    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05T14:30Z[Europe/Berlin]", "20070405T1430Z[Europe/Berlin]",
            "2007-04-05T16:30+02:00[Europe/Berlin]", "20070405T1630+0200[Europe/Berlin]", "2007-04-05T15:30+01[Europe/Berlin]"})
    void bracketZoneWithOffsetKeepsInstant(String iso8601) {
        ZonedDateTime actual = Iso8601Date.date(iso8601, ZoneId.of("Z"));
        assertThat(actual, is(ZonedDateTime.of(2007, 4, 5, 16, 30, 0, 0, BERLIN)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05T14:30[Europe/Berlin]", "20070405T1430[Europe/Berlin]", "2007-095T14:30[Europe/Berlin]"})
    void bracketZoneWithoutOffsetIsLocalTimeInThatZone(String iso8601) {
        ZonedDateTime actual = Iso8601Date.date(iso8601, ZoneId.of("Z"));
        assertThat(actual, is(ZonedDateTime.of(2007, 4, 5, 14, 30, 0, 0, BERLIN)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05[Europe/Berlin]", "20070405[Europe/Berlin]"})
    void bracketZoneOnDateIsStartOfDayInThatZone(String iso8601) {
        ZonedDateTime actual = Iso8601Date.date(iso8601, ZoneId.of("Z"));
        assertThat(actual, is(ZonedDateTime.of(2007, 4, 5, 0, 0, 0, 0, BERLIN)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2007-04-05T14:30Z[Mars/Olympus]", "2007-04-05T14:30Z[Europe/Berlin", "2007-04-05T14:30Z[]",
            "2007-04-05T14:30Z[Europe/Berlin]x", "2007-04-05T14:30Z[Europe/Berlin][UTC]", "2007-13-05T14:30Z[Europe/Berlin]",
            "[Europe/Berlin]"})
    void invalidBracketZone(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> Iso8601Date.date(iso8601, BERLIN));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-09-15T24:00:01Z", "2026-09-15T24:01Z", "2026-09-15T25:00Z", "2026-09-15T10:60Z", "2026-09-15T10:00:60Z",
            "2026-09-15T10:00:00+0200", "2026-09-15T10:00:00+2", "2026-09-15T10:00:00Z+02:00", "2026-09-15 10:00:00Z",
            "2026-09-15T", "2026-09-15T10:00:00.Z", "2026-09-15T10:00:00+19:00"})
    void invalidDateTimes(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> Iso8601Date.date(iso8601, BERLIN));
    }
}
