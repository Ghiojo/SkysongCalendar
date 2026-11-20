package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IcTimeOfDayTest {

    private static IcTimeOfDay at(String irlTime) {
        return IcTimeOfDay.of(LocalTime.parse(irlTime));
    }

    @Test
    void everyEightIrlHoursStartAnIcMidnight() {
        assertEquals(new IcTimeOfDay(0), at("00:00"));
        assertEquals(new IcTimeOfDay(0), at("08:00"));
        assertEquals(new IcTimeOfDay(0), at("16:00"));
    }

    @Test
    void theIcClockRunsThreeTimesFaster() {
        assertEquals(new IcTimeOfDay(6 * 3600), at("02:00"));
        assertEquals(new IcTimeOfDay(12 * 3600), at("12:00"));
        assertEquals(new IcTimeOfDay(18 * 3600), at("22:00"));
        assertEquals(new IcTimeOfDay(3), at("16:00:01"));
    }

    @Test
    void theLastIrlSecondOfABlockIsTheLastIcSecondsOfTheDay() {
        assertEquals(new IcTimeOfDay(IcTimeOfDay.SECONDS_PER_DAY - 3), at("07:59:59"));
    }

    @Test
    void splitsIntoHoursMinutesAndSeconds() {
        IcTimeOfDay time = at("16:24:01");

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
