package ch.nvaria.iso8601.base.models;

import java.time.Duration;
import java.time.Period;

public record PeriodDurationRecord(Period period, Duration duration) {
}
