package org.skysongdev.skysongCalendar.events;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DayChangeTest {

    @Test
    void anOrdinaryDayStartsNothingElse() {
        DayChange change = new DayChange(new IcDate(799, Month.IDALIA, 6), new IcDate(799, Month.IDALIA, 7));

        assertFalse(change.seasonChanged());
        assertFalse(change.festivalStarted());
    }

    @Test
    void aNewMonthCanStartASeason() {
        assertTrue(new DayChange(new IcDate(799, Month.IDALIA, 45), new IcDate(799, Month.DASYURUS, 1)).seasonChanged());
        assertFalse(new DayChange(new IcDate(799, Month.CELESTINE, 45), new IcDate(799, Month.IDALIA, 1)).seasonChanged());
    }

    @Test
    void theFestivalStartsWithoutChangingTheWinterSeason() {
        DayChange change = new DayChange(new IcDate(791, Month.LITORIA, 45), new IcDate(791, Festival.FESTIVAL, 1));

        assertTrue(change.festivalStarted());
        assertFalse(change.seasonChanged());
    }

    @Test
    void onlyTheFestivalsFirstDayStartsIt() {
        assertFalse(new DayChange(new IcDate(791, Festival.FESTIVAL, 1), new IcDate(791, Festival.FESTIVAL, 2)).festivalStarted());
    }

    @Test
    void theNewYearStartsSpring() {
        assertTrue(new DayChange(new IcDate(791, Festival.FESTIVAL, 9), new IcDate(792, Month.CELESTINE, 1)).seasonChanged());
    }
}
