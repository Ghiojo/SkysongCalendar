package org.skysongdev.skysongCalendar.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void countsDaysOfTheYearFromCelestineFirst() {
        assertEquals(0, new IcDate(790, Month.CELESTINE, 1).dayOfYear());
        assertEquals(46, new IcDate(790, Month.IDALIA, 2).dayOfYear());
        assertEquals(359, new IcDate(790, Month.LITORIA, 45).dayOfYear());
        assertEquals(360, new IcDate(790, Festival.FESTIVAL, 1).dayOfYear());
    }

    @Test
    void knowsFestivalDaysFromMonthDays() {
        assertTrue(new IcDate(790, Festival.FESTIVAL, 1).isFestival());
        assertFalse(new IcDate(790, Month.LITORIA, 45).isFestival());
    }
}
