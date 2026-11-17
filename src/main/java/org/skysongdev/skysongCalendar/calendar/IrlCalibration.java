package org.skysongdev.skysongCalendar.calendar;

import java.time.LocalDate;
import java.time.Year;

/**
 * How IRL dates line up with the IC calendar.
 *
 * <p>Every IRL day is {@link #IC_DAYS_PER_IRL_DAY} IC days, and every IRL year is one
 * {@link AtmosCycle} of three IC years. The first IC year's months end on Feb 28, so its
 * 3-day festival falls on Feb 29 when the IRL year has one, and the cycle starts 120 IRL
 * days earlier, on Nov 1 of the year before.
 *
 * @param epochCycleYear the IRL year whose Nov 1 starts a known cycle
 * @param epochIcYear    the IC year that cycle starts with
 */
public record IrlCalibration(int epochCycleYear, int epochIcYear) {
    /** The calibration Skysong uses: Nov 1, 2023 is Celestine 1, 790 AB. */
    public static final IrlCalibration SKYSONG = new IrlCalibration(2023, 790);

    public static final int IC_DAYS_PER_IRL_DAY = 3;

    private static final int FIRST_YEAR_MONTHS_IRL_DAYS =
            Month.values().length * Month.LENGTH / IC_DAYS_PER_IRL_DAY;

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
        return epochIcYear + (cycleYear - epochCycleYear) * AtmosCycle.years(false).size();
    }
}
