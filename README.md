# iso8601

[![Maven Central](https://img.shields.io/maven-central/v/ch.nvaria/iso8601)](https://central.sonatype.com/artifact/ch.nvaria/iso8601)
[![javadoc](https://javadoc.io/badge2/ch.nvaria/iso8601/javadoc.svg)](https://javadoc.io/doc/ch.nvaria/iso8601)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue)](LICENSE)
[![Java](https://img.shields.io/badge/java-21%2B-blue)](#installation)

Parses ISO 8601-1 dates, times, durations, time intervals and recurring time intervals into `java.time` types.

`java.time` covers only part of ISO 8601: `Duration.parse` has no years or months, `Period.parse` no time,
date-times in basic format (`20260915T100000Z`) are not supported, and there are no time intervals or
recurring intervals at all. This library handles all of ISO 8601-1, see [Supported representations](#supported-representations).

## Installation

Gradle:

```kotlin
implementation("ch.nvaria:iso8601:1.0.0")
```

Maven:

```xml
<dependency>
  <groupId>ch.nvaria</groupId>
  <artifactId>iso8601</artifactId>
  <version>1.0.0</version>
</dependency>
```

Requires Java 21 or later. The only runtime dependency is `org.antlr:antlr4-runtime`.
With the Java module system, add `requires ch.nvaria.iso8601;`.

## Quick start

```java
// Dates and times
Iso8601Date.date("20260915T1000Z", ZoneId.of("Europe/Berlin"));   // 2026-09-15T10:00Z (the offset wins)
Iso8601Date.date("2026-W38-2T10:00", ZoneId.of("Europe/Berlin")); // 2026-09-15T10:00+02:00[Europe/Berlin]
Iso8601Date.localDate("2026-258");                                // 2026-09-15
Iso8601Time.time("10:30,5");                                      // 10:30:30

// Durations and intervals
PeriodDuration d = PeriodOfTime.duration("P1Y2M3DT4H");           // period P1Y2M3D, duration PT4H
TimeInterval i = PeriodOfTime.interval("2026-09-15/17", ZoneOffset.UTC);
                                                                  // 2026-09-15T00:00Z to 2026-09-17T00:00Z, PT48H

// Recurring intervals
RecurringCycle.triggerTimesInclusive("R3/2026-09-15T10:00:00Z/P1D", ZoneOffset.UTC);
                                                                  // [2026-09-15T10:00Z, 2026-09-16T10:00Z, 2026-09-17T10:00Z]
RecurringCycle.triggersInclusive("R/2026-09-15T10:00:00Z/PT1H", ZoneOffset.UTC)
        .limit(3).toList();                                       // unbounded, so take the first 3
```

## API

All methods are static and throw `java.time.format.DateTimeParseException` on invalid input, including
out-of-range values such as month 13.

| Class | Method | Input | Result |
|---|---|---|---|
| `Iso8601Date` | `date(String, ZoneId)` | date or date-time | `ZonedDateTime`; an offset in the string wins over the zone id |
| `Iso8601Date` | `localDate(String)` | date | `LocalDate` |
| `Iso8601Time` | `time(String)` | time of day | `LocalTime` |
| `Iso8601Time` | `offset(String)` | time of day | `Optional<ZoneOffset>` |
| `Iso8601Time` | `offsetTime(String, ZoneOffset)` | time of day | `OffsetTime`; the given offset when the string has none |
| `PeriodOfTime` | `duration(String)` | duration or interval | `PeriodDuration` |
| `PeriodOfTime` | `interval(String, ZoneId)` | duration or interval | `TimeInterval` |
| `PeriodOfTime` | `durationInMillisFrom(String, ZonedDateTime \| LocalDateTime)` | duration | length in ms when applied at that start; a `ZonedDateTime` start includes daylight saving changes |
| `RecurringCycle` | `repetitions(String)` | recurring interval | `n`, or `UNBOUNDED` for `R` and `R-1` |
| `RecurringCycle` | `startDate(String, ZoneId)` | recurring interval | `Optional` start, empty when the string has none |
| `RecurringCycle` | `periodDuration(String)` | recurring interval | the `PeriodDuration` of one repetition |
| `RecurringCycle` | `interval(String, ZoneId)` | recurring interval | the `TimeInterval` part |
| `RecurringCycle` | `triggerTimesInclusive(String, ZoneId \| Clock[, int limit])` | recurring interval | the `n` starts of each repetition |
| `RecurringCycle` | `triggerTimesExclusive(String, ZoneId \| Clock[, int limit])` | recurring interval | the `n` ends of each repetition |
| `RecurringCycle` | `triggersInclusive` / `triggersExclusive(String, ZoneId \| Clock)` | recurring interval | lazy `Stream`, infinite for unbounded |

Result types in `ch.nvaria.iso8601.model`:

- `PeriodDuration`: the calendar part as `Period` (years, months, days) and the exact part as `Duration`
  (hours, minutes, seconds). Either is `null` when the input has none. `addTo` and `subtractFrom` apply it
  to any `Temporal`.
- `TimeInterval`: `start`, `end` and `duration`. Start and end are `null` for a bare duration such as `P1D`.

The trigger methods taking a `Clock` instead of a `ZoneId` let you control the start of a recurring interval
that has none (e.g. `R5/PT1H`), for example in tests. The clock's zone applies to points without an offset.

## Supported representations

| ISO 8601-1 clause | Representation | Examples |
|---|---|---|
| 5.2.2 | Calendar date, basic and extended, reduced precision | `2026-09-15`, `20260915`, `2026-09`, `2026`, `20` |
| 5.2.2.3 | Expanded years | `+012026-09-15`, `-0001-01-01` |
| 5.2.3 | Ordinal date | `2026-258`, `2026258` |
| 5.2.4 | Week date | `2026-W38-2`, `2026W382`, `2026-W38` |
| 5.3.1 | Time of day, reduced precision, fraction on the smallest component (dot or comma) | `10:00:00`, `T100000`, `10:30`, `10.5`, `10:30,5` |
| 5.3.2 | End of day | `24:00:00` (rolls over to the next day) |
| 5.3.3, 5.3.4 | UTC and offsets | `Z`, `+02`, `+02:00`, `+0200` (basic) |
| 5.4 | Date and time | `2026-09-15T10:00:00Z`, `20260915T100000Z`, `2026-258T10:00Z`, `2026-W38-2T10Z` |
| 5.5.2 | Duration, designator format, fraction on the smallest component | `P1Y2M3DT4H5M6S`, `P2W`, `PT1.5H`, `P0.5D` |
| 5.5.2 | Duration, alternative format | `P0001-02-03T04:05:06`, `P0001-02`, `P02-03` |
| 5.5.3 | Interval: start/end, start/duration, duration/end, duration | `2026-09-15/2026-09-17`, `2026-09-15T10:00:00Z/P1D`, `P1D/2026-09-16`, `P1D` |
| 5.5.4 | Interval with abbreviated end | `2026-09-15/17`, `2026-09-15/10-17`, `2026-09-15T10:00Z/12:00` |
| 5.6 | Recurring interval, all four interval forms | `R5/2026-09-15T10:00:00Z/PT1H`, `R/PT1H`, `R-1/P1D/2026-09-16` |

Extensions beyond ISO 8601-1:

- negative durations `-P1DT2H` (ISO 8601-2)
- zone ids in brackets after a date or date-time, basic or extended format: `2026-09-15T10:00Z[Europe/Berlin]`,
  `20260915T1000[Europe/Berlin]` (java.time). The result is in that zone; an offset fixes the instant,
  otherwise the local time is taken in the bracket zone.

Library conventions:

- A date without time means the start of that day in the given zone.
- Reduced precision means the first day, month, week or century.
- A recurring interval without a start starts at the current time: `ZonedDateTime.now(zoneId)`, or the
  given `Clock` for the overloads taking one. `startDate` returns empty for it.
- Each repetition of a recurring interval starts where the previous one ended.
- Fractional years and months are rejected because they have no exact length. Fractional weeks and days
  are converted to the exact part.

Not supported: ISO 8601-2 (EDTF, uncertain or approximate dates, sets), leap seconds (`23:59:60`).

## Contributing

Issues and pull requests are welcome on [GitHub](https://github.com/nvaria-labs/iso8601). The GitHub
repository is a read-only mirror: accepted pull requests are applied to the main repository and arrive on
GitHub with the next sync, with your authorship preserved.

## License

Apache License 2.0, see [LICENSE](LICENSE). The ANTLR grammar `src/main/antlr/Iso8601.g4` is based on the
0BSD-licensed grammar by "Student Main" from antlr/grammars-v4 and stays under 0BSD, including our
modifications; see [NOTICE](NOTICE) for its origin and the list of changes.

Maintained by [Nvaria GmbH](https://nvaria.ch).
