package com.skypro.pastebinanalog.enums;

import lombok.Getter;

import java.time.temporal.ChronoUnit;

@Getter
public enum ExpirationTime {

    TEN_MIN(10, ChronoUnit.MINUTES),
    HOUR(1, ChronoUnit.HOURS),
    THREE_HOURS(3, ChronoUnit.HOURS),
    DAY(1, ChronoUnit.DAYS),
    WEEK(7, ChronoUnit.DAYS),
    MONTH(30, ChronoUnit.DAYS),
    UNLIMITED(73_000, ChronoUnit.DAYS);

    private final Integer time;
    private final ChronoUnit unit;

    ExpirationTime(Integer time, ChronoUnit unit) {
        this.time = time;
        this.unit = unit;
    }

}

