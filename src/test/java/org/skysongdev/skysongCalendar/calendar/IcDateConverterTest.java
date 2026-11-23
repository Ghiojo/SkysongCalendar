package org.skysongdev.skysongCalendar.calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;
import org.skysongdev.skysongCalendar.api.Season;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IcDateConverterTest {
    private final IcDateConverter converter = new IcDateConverter(IrlCalibration.SKYSONG);

    private IcDate at(String dateTime) {
        return converter.toIcDate(LocalDateTime.parse(dateTime));
    }

    @Test
    void epochIsCelestineFirst790() {
        assertEquals(new IcDate(790, Month.CELESTINE, 1), at("2023-11-01T00:00"));
    }

    @Test
    void everyIrlDayHoldsThreeIcDaysOfEightHours() {
        assertEquals(new IcDate(790, Month.CELESTINE, 1), at("2023-11-01T07:59"));
        assertEquals(new IcDate(790, Month.CELESTINE, 2), at("2023-11-01T08:00"));
        assertEquals(new IcDate(790, Month.CELESTINE, 3), at("2023-11-01T23:59"));
        assertEquals(new IcDate(790, Month.CELESTINE, 4), at("2023-11-02T00:00"));
    }

    @Test
    void leapFestivalFallsOnFeb29() {
        assertEquals(new IcDate(790, Month.LITORIA, 45), at("2024-02-28T16:00"));
        assertEquals(new IcDate(790, Festival.FESTIVAL, 1), at("2024-02-29T00:00"));
        assertEquals(new IcDate(790, Festival.FESTIVAL, 3), at("2024-02-29T16:00"));
        assertEquals(new IcDate(791, Month.CELESTINE, 1), at("2024-03-01T00:00"));
    }

    @Test
    void withoutFeb29TheFirstYearHasNoFestival() {
        assertEquals(new IcDate(793, Month.LITORIA, 45), at("2025-02-28T16:00"));
        assertEquals(new IcDate(794, Month.CELESTINE, 1), at("2025-03-01T00:00"));
    }

    @Test
    void secondAndThirdFestivalsCloseTheIrlYear() {
        assertEquals(new IcDate(791, Festival.FESTIVAL, 1), at("2024-06-29T00:00"));
        assertEquals(new IcDate(791, Festival.FESTIVAL, 9), at("2024-07-01T16:00"));
        assertEquals(new IcDate(792, Festival.FESTIVAL, 1), at("2024-10-30T00:00"));
        assertEquals(new IcDate(792, Festival.FESTIVAL, 6), at("2024-10-31T16:00"));
        assertEquals(new IcDate(793, Month.CELESTINE, 1), at("2024-11-01T00:00"));
    }

    @Test
    void seasonChangesEvery30IrlDays() {
        assertEquals(Season.FLORESCIA, at("2026-11-30T16:00").season());
        assertEquals(Season.SU_UNTAH, at("2026-12-01T00:00").season());
        assertEquals(Season.ALTRUE, at("2026-12-31T00:00").season());
        assertEquals(Season.VINSE_ER, at("2027-01-30T00:00").season());
    }

    @Test
    void worksBeforeTheEpoch() {
        assertEquals(new IcDate(789, Festival.FESTIVAL, 6), at("2023-10-31T16:00"));
    }

    @Test
    void findsTheDateOfAnIcDayNumber() {
        assertEquals(new IcDate(790, Month.CELESTINE, 1), converter.dateOfDay(0));
        assertEquals(new IcDate(790, Month.CELESTINE, 4), converter.dateOfDay(3));
        assertEquals(new IcDate(789, Festival.FESTIVAL, 6), converter.dateOfDay(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {2023, 2024, 2099, 2100})
    void everyCycleLastsExactlyOneIrlYear(int cycleYear) {
        IrlCalibration calibration = IrlCalibration.SKYSONG;
        int icDays = AtmosCycle.years(calibration.hasLeapFestival(cycleYear)).stream()
                .mapToInt(IcYearLayout::length).sum();
        LocalDate start = calibration.cycleStart(cycleYear);
        assertEquals(LocalDate.of(cycleYear, 11, 1), start);
        assertEquals(start.plusDays(icDays / IrlCalibration.IC_DAYS_PER_IRL_DAY), calibration.cycleStart(cycleYear + 1));
        assertEquals(Year.of(cycleYear + 1).length() * 3, icDays);
    }
}
