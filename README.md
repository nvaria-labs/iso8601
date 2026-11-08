# iso8601_lib

Library to parse ISO 8601-1 dates, times, durations, time intervals and recurring time intervals.

Based on the ANTLR v4 grammar by "Student Main" from antlr/grammars-v4, see [License](#license).

## API

All methods throw `java.time.format.DateTimeParseException` on invalid input, including
out-of-range values such as month 13.

| Class | Method | Input | Result |
|---|---|---|---|
| `Iso8601Date` | `date(String, ZoneId)` | date or date-time | `ZonedDateTime`; an offset in the string wins over the zone id |
| `Iso8601Date` | `localDate(String)` | date | `LocalDate` |
| `Iso8601Time` | `time(String)` | time of day | `LocalTime` |
| `Iso8601Time` | `offset(String)` | time of day | `Optional<ZoneOffset>` |
| `PeriodOfTime` | `duration(String)` | duration or interval | calendar part (`Period`) and exact part (`Duration`) |
| `PeriodOfTime` | `interval(String, ZoneId)` | duration or interval | start, end and duration |
| `RecurringCycle` | `repetitions(String)` | recurring interval | `n`, or `UNBOUNDED` for `R` and `R-1` |
| `RecurringCycle` | `interval(String, ZoneId)` | recurring interval | the interval part |
| `RecurringCycle` | `triggerTimesInclusive(String, ZoneId[, int limit])` | recurring interval | the `n` starts of each repetition |
| `RecurringCycle` | `triggerTimesExclusive(String, ZoneId[, int limit])` | recurring interval | the `n` ends of each repetition |
| `RecurringCycle` | `triggersInclusive` / `triggersExclusive` | recurring interval | lazy `Stream`, infinite for unbounded |
| `RecurringCycle` | `startDate(String, ZoneId)` | recurring interval | `Optional` start, empty when the string has none |

The `RecurringCycle` trigger methods also accept a `java.time.Clock` instead of a `ZoneId`, to control the start
of cycles without one (e.g. in tests).

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

## License

Apache License 2.0, see [LICENSE](LICENSE). The ANTLR grammar `src/main/antlr/Iso8601.g4` is based on the
0BSD-licensed grammar by "Student Main" and stays under 0BSD, including our modifications; see [NOTICE](NOTICE)
for its origin and the list of changes.
