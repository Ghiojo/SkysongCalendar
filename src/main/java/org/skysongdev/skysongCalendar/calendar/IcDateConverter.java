package org.skysongdev.skysongCalendar.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** Turns an IRL date and time into the IC date it stands for. */
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
}
