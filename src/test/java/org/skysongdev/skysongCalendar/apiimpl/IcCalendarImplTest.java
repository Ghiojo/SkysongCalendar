package org.skysongdev.skysongCalendar.apiimpl;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcCalendar;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;
import org.skysongdev.skysongCalendar.api.Month;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IcCalendarImplTest {

    private static IcCalendar at(String instant) {
        // The clock's own zone must not matter: the calendar always reads UTC+1.
        Clock clock = Clock.fixed(Instant.parse(instant), ZoneId.of("America/New_York"));
        return new IcCalendarImpl(clock, IrlCalibration.SKYSONG);
    }

    @Test
    void todayReadsTheClockInUtcPlusOne() {
        // 23:00 UTC on Oct 31 is already Nov 1 at midnight in UTC+1.
        assertEquals(new IcDate(790, Month.CELESTINE, 1), at("2023-10-31T23:00:00Z").today());
    }

    @Test
    void nowCarriesTheDayNumberAndTheTime() {
        // 01:00 UTC+1 is IC 03:00 of IC day 0.
        assertEquals(new IcMoment(0, new IcTimeOfDay(3 * 3600)), at("2023-11-01T00:00:00Z").now());
        assertEquals(new IcTimeOfDay(3 * 3600), at("2023-11-01T00:00:00Z").timeOfDay());
    }

    @Test
    void convertsAnyInstant() {
        IcCalendar calendar = at("2026-11-23T12:00:00Z");

        assertEquals(new IcDate(790, Festival.FESTIVAL, 1), calendar.dateAt(Instant.parse("2024-02-28T23:00:00Z")));
        assertEquals(3, calendar.momentAt(Instant.parse("2023-11-01T23:00:00Z")).dayNumber());
    }

    @Test
    void dayNumbersAndInstantsAgree() {
        IcCalendar calendar = at("2026-11-23T12:00:00Z");

        assertEquals(calendar.today(), calendar.dateOfDay(calendar.now().dayNumber()));
    }

    @Test
    void exposesTheCalibrationReadOnly() {
        IcCalendar calendar = at("2026-11-23T12:00:00Z");

        assertEquals(ZoneOffset.ofHours(1), calendar.irlZone());
        assertEquals(3, calendar.icDaysPerIrlDay());
    }

    @Test
    void festivalsFollowTheCycle() {
        IcCalendar calendar = at("2026-11-23T12:00:00Z");

        assertEquals(3, calendar.festivalDays(790));
        assertEquals(9, calendar.festivalDays(791));
        assertEquals(0, calendar.festivalDays(793));
        assertEquals(360 + 6, calendar.yearLength(792));
    }

    @Test
    void countsSeasonChangesSinceTheEpoch() {
        assertEquals(0, at("2023-11-01T00:00:00Z").seasonCount());
        IcCalendar calendar = at("2026-11-23T12:00:00Z");
        assertEquals(4 + 2, calendar.seasonCountOf(new IcDate(791, Month.LONTRA, 1)));
    }

    @Test
    void findsWhenAnIcDateStartsIrl() {
        IcCalendar calendar = at("2026-11-23T12:00:00Z");

        // Feb 29, 2024 at midnight in UTC+1 is 23:00 UTC the day before.
        assertEquals(Instant.parse("2024-02-28T23:00:00Z"), calendar.irlStartOf(new IcDate(790, Festival.FESTIVAL, 1)));
        assertEquals(calendar.today(), calendar.dateAt(calendar.irlStartOf(calendar.today())));
        assertEquals(calendar.now().dayNumber(), calendar.dayNumberOf(calendar.today()));
    }
}
