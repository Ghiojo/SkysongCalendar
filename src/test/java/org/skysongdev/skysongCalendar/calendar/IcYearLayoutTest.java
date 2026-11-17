package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IcYearLayoutTest {
    private final IcYearLayout year = new IcYearLayout(9);

    @Test
    void lengthIsEightMonthsPlusTheFestival() {
        assertEquals(369, year.length());
        assertEquals(360, new IcYearLayout(0).length());
    }

    @Test
    void firstDayIsCelestineFirst() {
        assertEquals(new IcDate(800, Month.CELESTINE, 1), year.dateAt(800, 0));
    }

    @Test
    void monthsFollowEachOtherEvery45Days() {
        assertEquals(new IcDate(800, Month.CELESTINE, 45), year.dateAt(800, 44));
        assertEquals(new IcDate(800, Month.IDALIA, 1), year.dateAt(800, 45));
        assertEquals(new IcDate(800, Month.LITORIA, 45), year.dateAt(800, 359));
    }

    @Test
    void festivalComesAfterLitoria() {
        assertEquals(new IcDate(800, Festival.FESTIVAL, 1), year.dateAt(800, 360));
        assertEquals(new IcDate(800, Festival.FESTIVAL, 9), year.dateAt(800, 368));
    }

    @Test
    void refusesDaysOutsideTheYear() {
        assertThrows(IllegalArgumentException.class, () -> year.dateAt(800, -1));
        assertThrows(IllegalArgumentException.class, () -> year.dateAt(800, 369));
    }
}
