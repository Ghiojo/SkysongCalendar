package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IcMonthDayTest {

    @Test
    void parsesAMonthAndADayIgnoringCase() {
        assertEquals(new IcMonthDay(Month.RAMPHASTOS, 1), IcMonthDay.parse("Ramphastos 1"));
        assertEquals(new IcMonthDay(Month.LITORIA, 45), IcMonthDay.parse("  litoria   45 "));
    }

    @Test
    void rejectsWhatIsNotADayOfAMonth() {
        assertThrows(IllegalArgumentException.class, () -> IcMonthDay.parse("Ramphastos"));
        assertThrows(IllegalArgumentException.class, () -> IcMonthDay.parse("Smarch 1"));
        assertThrows(IllegalArgumentException.class, () -> IcMonthDay.parse("Rana first"));
        assertThrows(IllegalArgumentException.class, () -> IcMonthDay.parse("Rana 46"));
        assertThrows(IllegalArgumentException.class, () -> IcMonthDay.parse("Rana 0"));
    }

    @Test
    void readsBackAsItIsWritten() {
        assertEquals("Ramphastos 1", new IcMonthDay(Month.RAMPHASTOS, 1).toString());
    }

    @Test
    void countsTheDayOfTheYear() {
        assertEquals(135, new IcMonthDay(Month.RAMPHASTOS, 1).dayOfYear());
    }
}
