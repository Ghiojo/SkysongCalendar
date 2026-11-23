package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcMoment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Turns an IRL date and time into the IC date it stands for, and back. */
public final class IcDateConverter {
    private final IrlCalibration calibration;

    public IcDateConverter(IrlCalibration calibration) {
        this.calibration = calibration;
    }

    /** The IC date of an IC day number, counted as in {@link IcMoment#dayNumber()}. */
    public IcDate dateOfDay(long dayNumber) {
        LocalDateTime start = calibration.epochStart().atStartOfDay()
                .plusHours(dayNumber * IrlCalibration.IRL_HOURS_PER_IC_DAY);
        return toIcDate(start);
    }

    /**
     * The IRL date and time an IC date starts at, in {@link IrlCalibration#ZONE}: the start
     * of its {@link IrlCalibration#IRL_HOURS_PER_IC_DAY}-hour block.
     *
     * @throws IllegalArgumentException when the date doesn't exist, like Idalia 46 or a
     *                                  festival day past the end of that year's festival
     */
    public LocalDateTime irlStartOf(IcDate date) {
        int cycleYear = calibration.cycleYearOfIcYear(date.year());
        List<IcYearLayout> years = AtmosCycle.years(calibration.hasLeapFestival(cycleYear));
        int yearInCycle = date.year() - calibration.firstIcYearOf(cycleYear);
        requireExists(years.get(yearInCycle), date);

        long icDay = date.dayOfYear();
        for (IcYearLayout earlier : years.subList(0, yearInCycle)) {
            icDay += earlier.length();
        }
        return calibration.cycleStart(cycleYear).atStartOfDay().plusHours(icDay * IrlCalibration.IRL_HOURS_PER_IC_DAY);
    }

    /**
     * The IC day number of an IC date, counted as in {@link IcMoment#dayNumber()}.
     *
     * @throws IllegalArgumentException when the date doesn't exist
     */
    public long dayNumberOf(IcDate date) {
        long hours = ChronoUnit.HOURS.between(calibration.epochStart().atStartOfDay(), irlStartOf(date));
        return Math.floorDiv(hours, IrlCalibration.IRL_HOURS_PER_IC_DAY);
    }

    public IcDate toIcDate(LocalDateTime irl) {
        LocalDate date = irl.toLocalDate();
        int cycleYear = calibration.cycleYearOf(date);
        long irlDays = ChronoUnit.DAYS.between(calibration.cycleStart(cycleYear), date);
        long icDay = irlDays * IrlCalibration.IC_DAYS_PER_IRL_DAY + irl.getHour() / IrlCalibration.IRL_HOURS_PER_IC_DAY;

        int icYear = calibration.firstIcYearOf(cycleYear);
        for (IcYearLayout year : AtmosCycle.years(calibration.hasLeapFestival(cycleYear))) {
            if (icDay < year.length()) {
                return year.dateAt(icYear, (int) icDay);
            }
            icDay -= year.length();
            icYear++;
        }
        throw new IllegalStateException("The cycle starting in " + cycleYear + " doesn't cover " + irl);
    }

    private static void requireExists(IcYearLayout year, IcDate date) {
        boolean exists = date.day() >= 1 && date.dayOfYear() < year.length()
                && year.dateAt(date.year(), date.dayOfYear()).equals(date);
        if (!exists) {
            throw new IllegalArgumentException(date.period().displayName() + " " + date.day() + ", "
                    + date.year() + " AB doesn't exist");
        }
    }
}
