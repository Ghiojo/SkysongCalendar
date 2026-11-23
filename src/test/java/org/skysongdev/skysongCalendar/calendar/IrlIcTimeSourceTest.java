package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;

import java.time.Clock;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IrlIcTimeSourceTest {

    private static IcMoment nowAt(String instant) {
        Clock clock = Clock.fixed(Instant.parse(instant), IrlCalibration.ZONE);
        return new IrlIcTimeSource(clock, new IcMomentConverter(IrlCalibration.SKYSONG)).now();
    }

    @Test
    void readsTheIrlTimeAtUtcPlusOne() {
        assertEquals(new IcTimeOfDay(0), nowAt("2026-11-20T07:00:00Z").timeOfDay());
    }

    @Test
    void summerTimeDoesNotShiftTheIcDay() {
        // 01:00 UTC+1 in July (03:00 in Paris summer time) is still 3 IC hours into the day.
        assertEquals(new IcTimeOfDay(3 * 3600), nowAt("2026-07-01T00:00:00Z").timeOfDay());
    }
}
