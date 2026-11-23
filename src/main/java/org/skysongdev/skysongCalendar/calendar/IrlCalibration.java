package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.Month;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneOffset;

/**
 * How IRL dates line up with the IC calendar.
 *
 * <p>Every IRL day is {@link #IC_DAYS_PER_IRL_DAY} IC days, and every IRL year is one
 * {@link AtmosCycle} of three IC years. The first IC year's months end on Feb 28, so its
 * 3-day festival falls on Feb 29 when the IRL year has one, and the cycle starts 120 IRL
 * days earlier, on Nov 1 of the year before.
 *
 * <p>IRL dates and times are read in {@link #ZONE}, a fixed offset with no daylight saving,
 * so every IC day lasts exactly {@link #IRL_HOURS_PER_IC_DAY} IRL hours all year.
 *
 * @param epochCycleYear the IRL year whose Nov 1 starts a known cycle
 * @param epochIcYear    the IC year that cycle starts with
 */
public record IrlCalibration(int epochCycleYear, int epochIcYear) {
    /** The calibration Skysong uses: Nov 1, 2023 is Celestine 1, 790 AB. */
    public static final IrlCalibration SKYSONG = new IrlCalibration(2023, 790);

    public static final int IC_DAYS_PER_IRL_DAY = 3;

    /** IRL hours in one IC day: the IRL day splits into blocks of this length, from midnight. */
    public static final int IRL_HOURS_PER_IC_DAY = 24 / IC_DAYS_PER_IRL_DAY;

    /** The IRL time zone the calendar follows: UTC+1 all year, Paris winter time without DST. */
    public static final ZoneOffset ZONE = ZoneOffset.ofHours(1);

    private static final int YEARS_PER_CYCLE = AtmosCycle.years(false).size();

    private static final int FIRST_YEAR_MONTHS_IRL_DAYS =
            Month.values().length * Month.LENGTH / IC_DAYS_PER_IRL_DAY;

    /** The first IRL day of the epoch cycle, whose first IC day is IC day number 0. */
    public LocalDate epochStart() {
        return cycleStart(epochCycleYear);
    }

    /** The IRL year whose cycle a date falls in: its own, or the one before until Nov 1. */
    public int cycleYearOf(LocalDate date) {
        int year = date.getYear();
        return date.isBefore(cycleStart(year)) ? year - 1 : year;
    }

    /** The first day of the cycle that starts in this IRL year. */
    public LocalDate cycleStart(int cycleYear) {
        LocalDate lastMonthDay = LocalDate.of(cycleYear + 1, java.time.Month.FEBRUARY, 28);
        return lastMonthDay.minusDays(FIRST_YEAR_MONTHS_IRL_DAYS - 1);
    }

    /** Whether this cycle has the leap festival, which needs a Feb 29 to fall on. */
    public boolean hasLeapFestival(int cycleYear) {
        return Year.isLeap(cycleYear + 1);
    }

    /** The IC year this cycle starts with. */
    public int firstIcYearOf(int cycleYear) {
        return epochIcYear + (cycleYear - epochCycleYear) * YEARS_PER_CYCLE;
    }

    /** The shape of an IC year: how long its festival lasts, which depends on its cycle. */
    public IcYearLayout layoutOf(int icYear) {
        int yearsSinceEpoch = icYear - epochIcYear;
        int cycleYear = epochCycleYear + Math.floorDiv(yearsSinceEpoch, YEARS_PER_CYCLE);
        return AtmosCycle.years(hasLeapFestival(cycleYear)).get(Math.floorMod(yearsSinceEpoch, YEARS_PER_CYCLE));
    }
}
