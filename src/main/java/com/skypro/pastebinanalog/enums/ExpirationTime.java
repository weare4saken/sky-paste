package com.skypro.pastebinanalog.enums;

import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public enum ExpirationTime {

    TEN_MIN(10, ChronoUnit.MINUTES),
    HOUR(1, ChronoUnit.HOURS),
    THREE_HOURS(3, ChronoUnit.HOURS),
    DAY(1, ChronoUnit.DAYS),
    WEEK(1, ChronoUnit.WEEKS),
    MONTH(1, ChronoUnit.MONTHS),
    UNLIMITED(Instant.MAX, ChronoUnit.FOREVER);

    private final Instant expirationTime;
    private final ChronoUnit chronoUnit;

    ExpirationTime(Instant expirationTime, ChronoUnit chronoUnit) {
        this.expirationTime = expirationTime;
        this.chronoUnit = chronoUnit;
    }

}

