package ch.nvaria.iso8601;

import ch.nvaria.iso8601.model.PeriodDuration;
import ch.nvaria.iso8601.model.TimeInterval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * ISO 8601-1 clause 5.5 (durations and time intervals) coverage.
 */
class PeriodOfTimeCoverageTest {

    private static final ZoneId UTC = ZoneId.of("Z");
    private static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

    // ---------------------------------------------------------------- 5.5.2 durations, designator format

    @ParameterizedTest
    @CsvSource({
            // input,                period,   duration
            "P1Y2M3DT4H5M6S,         P1Y2M3D,  PT4H5M6S",
            "P1Y,                    P1Y,      ",
            "P1M,                    P1M,      ",
            "P1D,                    P1D,      ",
            "P1W,                    P7D,      ",
            "P2W,                    P14D,     ",
            "PT1H,                   ,         PT1H",
            "PT1M,                   ,         PT1M",
            "PT1S,                   ,         PT1S",
            "PT36H,                  ,         PT36H",
            "PT1.5H,                 ,         PT1H30M",
            "PT1.5M,                 ,         PT1M30S",
            "PT1.5S,                 ,         PT1.5S",
            "'PT1,5S',               ,         PT1.5S",
            "PT0.000000001S,         ,         PT0.000000001S",
            "P1.5D,                  ,         PT36H",
            "P0.5W,                  ,         PT84H",
            "P1DT12H,                P1D,      PT12H",
            "P1DT1.5H,               P1D,      PT1H30M",
            "p1dt1h,                 P1D,      PT1H",
            // alternative format
            "P0001-02-03T04:05:06,   P1Y2M3D,  PT4H5M6S",
            "P00010203T040506,       P1Y2M3D,  PT4H5M6S",
            "P0001-02-03,            P1Y2M3D,  ",
            "P0001-02,               P1Y2M,    ",
            "P0001,                  P1Y,      ",
            "P02-03,                 P2M3D,    ",
            "P03,                    P3D,      ",
            "P0000-00-31T24:00:00,   P31D,     PT24H",
            // negative (ISO 8601-2 extension)
            "-P1D,                   P-1D,     ",
            "-PT2H30M,               ,         PT-2H-30M",
            "-P1DT2H,                P-1D,     PT-2H",
    })
    void durations(String iso8601, String period, String duration) {
        PeriodDuration actual = PeriodOfTime.duration(iso8601);
        assertThat(actual.period(), is(period == null ? null : Period.parse(period)));
        assertThat(actual.duration(), is(duration == null ? null : Duration.parse(duration)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"P", "PT", "P1DT", "P1M2Y", "P1Y2M3D4H", "PT1H2S3M", "P1.5Y", "P0.5M", "P1.5DT1H", "P1D2", "1D", "P1X", "",
            "P0001-02-03T04:05:06Z", "P2026-258", "P2026-W38-2", "P1.5.5D", "PT1H garbage",
            // alternative format components out of range (5.5.2.4)
            "P0000-13-00", "P0000-00-32", "P13-01", "P45", "P0000-00-00T25:00:00", "P0000-00-00T00:60:00", "P0000-00-00T00:00:60"})
    void invalidDurations(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> PeriodOfTime.duration(iso8601));
    }

    @Test
    void nullDuration() {
        assertThrows(DateTimeParseException.class, () -> PeriodOfTime.duration(null));
    }

    // ---------------------------------------------------------------- 5.5.3 / 5.5.4 time intervals

    @ParameterizedTest
    @CsvSource({
            // input,                                           start,                     end,                       duration
            "2026-09-15T10:00:00Z/2026-09-16T10:00:00Z,         2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      PT24H",
            "2026-09-15T10:00:00Z/2026-09-15T11:30:00Z,         2026-09-15T10:00:00Z,      2026-09-15T11:30:00Z,      PT1H30M",
            "20260915T100000Z/20260916T100000Z,                 2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      PT24H",
            "2026-09-15/2026-09-17,                             2026-09-15T00:00:00Z,      2026-09-17T00:00:00Z,      PT48H",
            "2026-09/2026-10,                                   2026-09-01T00:00:00Z,      2026-10-01T00:00:00Z,      PT720H",
            // abbreviated end (5.5.4.2)
            "2026-09-15/17,                                     2026-09-15T00:00:00Z,      2026-09-17T00:00:00Z,      PT48H",
            "2026-09-15/10-17,                                  2026-09-15T00:00:00Z,      2026-10-17T00:00:00Z,      PT768H",
            "2026-09-15T10:00:00Z/12:00:00,                     2026-09-15T10:00:00Z,      2026-09-15T12:00:00Z,      PT2H",
            "2026-09-15T10:00:00Z/12:00,                        2026-09-15T10:00:00Z,      2026-09-15T12:00:00Z,      PT2H",
            "2026-09-15T10:00:00Z/24:00:00,                     2026-09-15T10:00:00Z,      2026-09-16T00:00:00Z,      PT14H",
            "2026-09-15T10:00:00Z/2026-09-16T10:00:00,          2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      PT24H",
            "2026-09-15T10:00:00+02:00/2026-09-16T10:00:00,     2026-09-15T10:00:00+02:00, 2026-09-16T10:00:00+02:00, PT24H",
            // start/duration
            "2026-09-15T10:00:00Z/P1D,                          2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      P1D",
            "2026-09-15T10:00:00Z/PT1H,                         2026-09-15T10:00:00Z,      2026-09-15T11:00:00Z,      PT1H",
            "2026-09-15/P1D,                                    2026-09-15T00:00:00Z,      2026-09-16T00:00:00Z,      P1D",
            "2026-09-15/P1M,                                    2026-09-15T00:00:00Z,      2026-10-15T00:00:00Z,      P1M",
            "20260915T100000Z/P1D,                              2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      P1D",
            "2026-09-15T10:00:00Z/P0000-00-01,                  2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      P1D",
            "2026-11-19T10:00:00Z/P1M2DT3H,                     2026-11-19T10:00:00Z,      2026-12-21T13:00:00Z,      P1M2DT3H",
            // duration/end
            "P1D/2026-09-16T10:00:00Z,                          2026-09-15T10:00:00Z,      2026-09-16T10:00:00Z,      P1D",
            "PT1H/2026-09-15T11:00:00Z,                         2026-09-15T10:00:00Z,      2026-09-15T11:00:00Z,      PT1H",
            "P1D/2026-09-16,                                    2026-09-15T00:00:00Z,      2026-09-16T00:00:00Z,      P1D",
            "P1M/2026-03-31,                                    2026-02-28T00:00:00Z,      2026-03-31T00:00:00Z,      P1M",
    })
    void intervals(String iso8601, String start, String end, String duration) {
        TimeInterval actual = PeriodOfTime.interval(iso8601, UTC);
        assertThat(actual.start(), is(ZonedDateTime.parse(start)));
        assertThat(actual.end(), is(ZonedDateTime.parse(end)));
        assertThat(actual.start().getOffset(), is(ZonedDateTime.parse(start).getOffset()));
        assertThat(actual.duration(), is(parse(duration)));
        assertThat(PeriodOfTime.duration(iso8601), is(parse(duration)));
    }

    @Test
    void bareDurationHasNoStartOrEnd() {
        TimeInterval actual = PeriodOfTime.interval("P1D", UTC);
        assertThat(actual.start(), is(nullValue()));
        assertThat(actual.end(), is(nullValue()));
        assertThat(actual.optionalStart().isPresent(), is(false));
        assertThat(actual.duration(), is(new PeriodDuration(Period.ofDays(1), null)));
    }

    @Test
    void intervalWithoutOffsetUsesZoneId() {
        TimeInterval actual = PeriodOfTime.interval("2026-09-15T10:00:00/PT1H", BERLIN);
        assertThat(actual.start(), is(ZonedDateTime.of(2026, 9, 15, 10, 0, 0, 0, BERLIN)));
        assertThat(actual.end(), is(ZonedDateTime.of(2026, 9, 15, 11, 0, 0, 0, BERLIN)));
    }

    @Test
    void intervalAcrossDaylightSavingIsCalendarBased() {
        // 2026-10-25 02:00 CEST -> 01:00 CET, the day has 25 hours
        TimeInterval actual = PeriodOfTime.interval("2026-10-25/P1D", BERLIN);
        assertThat(Duration.between(actual.start(), actual.end()), is(Duration.ofHours(25)));
        assertThat(PeriodOfTime.durationInMillisFrom("P1D", actual.start()), is(25L * 3600 * 1000));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-09-16T10:00:00Z/2026-09-15T10:00:00Z", "17/2026-09-15", "2026-09-15/P", "P1D/", "/P1D",
            "2026-09-15/2026-09-17/2026-09-18", "2026-09-15T10:00:00Z/P1D/", "2026-09-15/P1.5Y", "2026-13-01/P1D"})
    void invalidIntervals(String iso8601) {
        assertThrows(DateTimeParseException.class, () -> PeriodOfTime.interval(iso8601, UTC));
    }

    // ---------------------------------------------------------------- helpers

    private static PeriodDuration parse(String duration) {
        // "P1M2DT3H" -> period P1M2D, duration PT3H; "PT1H" -> duration only; "P1D" -> period only
        String[] parts = duration.split("T");
        Period period = parts[0].equals("P") ? null : Period.parse(parts[0]);
        Duration d = parts.length == 1 ? null : Duration.parse("PT" + parts[1]);
        return new PeriodDuration(period, d);
    }
}
