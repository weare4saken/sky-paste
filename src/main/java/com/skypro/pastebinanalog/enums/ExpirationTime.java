package com.skypro.pastebinanalog.enums;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public enum ExpirationTime {

    TEN_MIN(10L, ChronoUnit.MINUTES),
    HOUR(1L, ChronoUnit.HOURS),
    THREE_HOURS(3L, ChronoUnit.HOURS),
    DAY(1L, ChronoUnit.DAYS),
    WEEK(1L, ChronoUnit.WEEKS),
    MONTH(1L, ChronoUnit.MONTHS),
    UNLIMITED(Long.MAX_VALUE, ChronoUnit.FOREVER);

    private final Long time;
    private final ChronoUnit unit;

    ExpirationTime(Long time, ChronoUnit unit) {
        this.time = time;
        this.unit = unit;
    }

}

