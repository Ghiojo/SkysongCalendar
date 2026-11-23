package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IcMomentConverterTest {
    private final IcMomentConverter converter = new IcMomentConverter(IrlCalibration.SKYSONG);

    private IcMoment at(String dateTime) {
        return converter.toIcMoment(LocalDateTime.parse(dateTime));
    }

    @Test
    void theEpochIsDayZeroAtMidnight() {
        assertEquals(new IcMoment(0, new IcTimeOfDay(0)), at("2023-11-01T00:00"));
    }

    @Test
    void countsThreeIcDaysPerIrlDay() {
        assertEquals(1, at("2023-11-01T08:00").dayNumber());
        assertEquals(2, at("2023-11-01T23:59").dayNumber());
        assertEquals(3, at("2023-11-02T00:00").dayNumber());
    }

    @Test
    void daysBeforeTheEpochAreNegative() {
        assertEquals(-1, at("2023-10-31T16:00").dayNumber());
    }

    @Test
    void carriesTheTimeOfDay() {
        assertEquals(new IcTimeOfDay(12 * 3600), at("2026-11-20T12:00").timeOfDay());
    }

    /** The IC day number and the IC date must change at the same instant. */
    @ParameterizedTest
    @ValueSource(strings = {"2024-02-29T00:00", "2024-07-01T16:00", "2026-11-20T08:00", "2027-01-30T00:00"})
    void dayNumberAdvancesTogetherWithTheDate(String dateTime) {
        IcDateConverter dates = new IcDateConverter(IrlCalibration.SKYSONG);
        LocalDateTime start = LocalDateTime.parse(dateTime);
        LocalDateTime justBefore = start.minusSeconds(1);

        assertEquals(1, converter.toIcMoment(start).dayNumber() - converter.toIcMoment(justBefore).dayNumber());
        assertEquals(new IcTimeOfDay(0), converter.toIcMoment(start).timeOfDay());
        assertNotEquals(dates.toIcDate(justBefore), dates.toIcDate(start));
    }
}
