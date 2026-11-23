package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeasonCountTest {
    private final SeasonCount seasons = new SeasonCount(790);

    @Test
    void epochStartsAtZero() {
        assertEquals(0, seasons.of(new IcDate(790, Month.CELESTINE, 1)));
        assertEquals(0, seasons.of(new IcDate(790, Month.IDALIA, 45)));
    }

    @Test
    void goesUpByOneAtEachSeasonChange() {
        assertEquals(1, seasons.of(new IcDate(790, Month.DASYURUS, 1)));
        assertEquals(2, seasons.of(new IcDate(790, Month.LONTRA, 1)));
        assertEquals(3, seasons.of(new IcDate(790, Month.RANA, 1)));
    }

    @Test
    void festivalStaysInVinseEr() {
        assertEquals(3, seasons.of(new IcDate(790, Festival.FESTIVAL, 3)));
    }

    @Test
    void continuesIntoTheNextYear() {
        assertEquals(4, seasons.of(new IcDate(791, Month.CELESTINE, 1)));
        assertEquals(36, seasons.of(new IcDate(799, Month.IDALIA, 6)));
    }
}
