package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IcDateToIrlTest {
    private final IcDateConverter converter = new IcDateConverter(IrlCalibration.SKYSONG);

    @Test
    void theEpochStartsAtMidnightOnNovemberFirst() {
        assertEquals(LocalDateTime.parse("2023-11-01T00:00"), converter.irlStartOf(new IcDate(790, Month.CELESTINE, 1)));
        assertEquals(0, converter.dayNumberOf(new IcDate(790, Month.CELESTINE, 1)));
    }

    @Test
    void theLeapFestivalStartsOnFeb29() {
        assertEquals(LocalDateTime.parse("2024-02-29T00:00"), converter.irlStartOf(new IcDate(790, Festival.FESTIVAL, 1)));
    }

    @Test
    void laterYearsOfACycleStartAfterTheEarlierOnes() {
        assertEquals(LocalDateTime.parse("2024-03-01T00:00"), converter.irlStartOf(new IcDate(791, Month.CELESTINE, 1)));
        assertEquals(LocalDateTime.parse("2024-03-01T08:00"), converter.irlStartOf(new IcDate(791, Month.CELESTINE, 2)));
    }

    @Test
    void everyDayRoundTripsToTheStartOfItsBlock() {
        for (long dayNumber = -1200; dayNumber <= 3000; dayNumber++) {
            IcDate date = converter.dateOfDay(dayNumber);
            LocalDateTime start = converter.irlStartOf(date);

            assertEquals(date, converter.toIcDate(start));
            assertEquals(0, start.getHour() % IrlCalibration.IRL_HOURS_PER_IC_DAY);
            assertEquals(0, start.getMinute());
            assertEquals(dayNumber, converter.dayNumberOf(date));
        }
    }

    @Test
    void refusesDatesThatDoNotExist() {
        assertThrows(IllegalArgumentException.class, () -> converter.irlStartOf(new IcDate(799, Month.IDALIA, 46)));
        assertThrows(IllegalArgumentException.class, () -> converter.irlStartOf(new IcDate(799, Month.IDALIA, 0)));
        // 793 starts a cycle whose IRL year, 2025, has no Feb 29, so it has no festival at all.
        assertThrows(IllegalArgumentException.class, () -> converter.irlStartOf(new IcDate(793, Festival.FESTIVAL, 1)));
        assertThrows(IllegalArgumentException.class, () -> converter.irlStartOf(new IcDate(791, Festival.FESTIVAL, 10)));
    }
}
