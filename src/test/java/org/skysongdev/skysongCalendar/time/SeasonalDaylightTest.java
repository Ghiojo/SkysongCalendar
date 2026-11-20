package org.skysongdev.skysongCalendar.time;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.calendar.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;
import org.skysongdev.skysongCalendar.calendar.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeasonalDaylightTest {
    private static final double TOLERANCE_SECONDS = 60;

    private final IcDateConverter dates = new IcDateConverter(IrlCalibration.SKYSONG);
    private final SeasonalDaylight daylight = new SeasonalDaylight(dates, new DaylightSettings(10, 14));

    /** The first IC day number on or after day 0 that falls on this date in IC year 790. */
    private long dayNumberOf(Month month, int day) {
        IcDate wanted = new IcDate(790, month, day);
        for (long number = 0; number < 400; number++) {
            if (dates.dateOfDay(number).equals(wanted)) {
                return number;
            }
        }
        throw new AssertionError("Not found: " + wanted);
    }

    private double hoursOn(Month month, int day) {
        return daylight.daylightSeconds(dayNumberOf(month, day)) / 3600.0;
    }

    @Test
    void midSummerIsLongestAndMidWinterShortest() {
        assertEquals(14, hoursOn(Month.DASYURUS, 45), TOLERANCE_SECONDS / 3600);
        assertEquals(14, hoursOn(Month.RAMPHASTOS, 1), TOLERANCE_SECONDS / 3600);
        assertEquals(10, hoursOn(Month.RANA, 45), TOLERANCE_SECONDS / 3600);
        assertEquals(10, hoursOn(Month.LITORIA, 1), TOLERANCE_SECONDS / 3600);
    }

    @Test
    void springAndAutumnAreEven() {
        assertEquals(12, hoursOn(Month.CELESTINE, 45), 0.05);
        assertEquals(12, hoursOn(Month.LONTRA, 45), 0.05);
    }

    @Test
    void changesByAboutTwoIcMinutesAtMostFromOneDayToTheNext() {
        for (long day = 0; day < 400; day++) {
            int change = Math.abs(daylight.daylightSeconds(day + 1) - daylight.daylightSeconds(day));
            assertTrue(change <= 126 || isAcrossNewYear(day), "changed " + change + " s after day " + day);
        }
    }

    private boolean isAcrossNewYear(long day) {
        return dates.dateOfDay(day + 1).dayOfYear() == 0;
    }

    @Test
    void evenSettingsGiveTwelveHoursAllYear() {
        SeasonalDaylight even = new SeasonalDaylight(dates, DaylightSettings.EVEN);

        assertEquals(12 * 3600, even.daylightSeconds(0));
        assertEquals(12 * 3600, even.daylightSeconds(135));
    }

    @Test
    void rejectsImpossibleSettings() {
        assertThrows(IllegalArgumentException.class, () -> new DaylightSettings(14, 10));
        assertThrows(IllegalArgumentException.class, () -> new DaylightSettings(0, 12));
        assertThrows(IllegalArgumentException.class, () -> new DaylightSettings(12, 24));
    }
}
