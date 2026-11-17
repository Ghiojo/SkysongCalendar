package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IcDateTest {

    @Test
    void weekRestartsEveryNineDays() {
        assertEquals(Weekday.MANAESDAY, new IcDate(790, Month.RANA, 1).weekday());
        assertEquals(Weekday.PERDISDAY, new IcDate(790, Month.RANA, 9).weekday());
        assertEquals(Weekday.MANAESDAY, new IcDate(790, Month.RANA, 10).weekday());
        assertEquals(Weekday.PERDISDAY, new IcDate(790, Month.RANA, 45).weekday());
    }

    @Test
    void seasonComesFromTheMonth() {
        assertEquals(Season.FLORESCIA, new IcDate(790, Month.IDALIA, 3).season());
        assertEquals(Season.SU_UNTAH, new IcDate(790, Month.DASYURUS, 3).season());
        assertEquals(Season.ALTRUE, new IcDate(790, Month.SAIMIRI, 3).season());
        assertEquals(Season.VINSE_ER, new IcDate(790, Festival.FESTIVAL, 3).season());
    }
}
