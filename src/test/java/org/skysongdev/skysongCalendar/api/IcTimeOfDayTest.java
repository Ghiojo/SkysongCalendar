package org.skysongdev.skysongCalendar.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IcTimeOfDayTest {

    @Test
    void splitsIntoHoursMinutesAndSeconds() {
        IcTimeOfDay time = new IcTimeOfDay(3600 + 12 * 60 + 3);

        assertEquals(1, time.hour());
        assertEquals(12, time.minute());
        assertEquals(3, time.second());
    }

    @Test
    void rejectsSecondsOutsideTheDay() {
        assertThrows(IllegalArgumentException.class, () -> new IcTimeOfDay(-1));
        assertThrows(IllegalArgumentException.class, () -> new IcTimeOfDay(IcTimeOfDay.SECONDS_PER_DAY));
    }
}
