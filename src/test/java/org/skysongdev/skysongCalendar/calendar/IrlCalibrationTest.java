package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IrlCalibrationTest {
    private final IrlCalibration calibration = IrlCalibration.SKYSONG;

    private int festivalDays(int icYear) {
        return calibration.layoutOf(icYear).festivalDays();
    }

    @Test
    void theCycleOfTheEpochHasTheLeapFestivalBecause2024HasAFeb29() {
        assertEquals(3, festivalDays(790));
        assertEquals(9, festivalDays(791));
        assertEquals(6, festivalDays(792));
    }

    @Test
    void cyclesWithoutAFeb29SkipTheLeapFestival() {
        assertEquals(0, festivalDays(793));
        assertEquals(9, festivalDays(794));
        assertEquals(0, festivalDays(799));
    }

    @Test
    void theNextLeapCycleStartsWithIcYear802() {
        assertEquals(3, festivalDays(802));
    }

    @Test
    void yearsBeforeTheEpochFollowTheirOwnCycle() {
        assertEquals(6, festivalDays(789));
        assertEquals(0, festivalDays(787));
    }
}
