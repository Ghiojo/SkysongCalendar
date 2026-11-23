package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IrlIcClockTest {

    @Test
    void readsTheIrlTimeInTheClocksZone() {
        Clock clock = Clock.fixed(Instant.parse("2024-02-29T23:30:00Z"), ZoneId.of("Europe/Madrid"));
        IcClock icClock = new IrlIcClock(clock, new IcDateConverter(IrlCalibration.SKYSONG));

        assertEquals(new IcDate(791, Month.CELESTINE, 1), icClock.today());
    }
}
