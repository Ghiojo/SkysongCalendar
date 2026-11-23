package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/** Turns an IRL date and time into the IC moment it stands for. */
public final class IcMomentConverter {
    private static final int IRL_SECONDS_PER_IC_DAY = IrlCalibration.IRL_HOURS_PER_IC_DAY * 60 * 60;

    private final IrlCalibration calibration;

    public IcMomentConverter(IrlCalibration calibration) {
        this.calibration = calibration;
    }

    public IcMoment toIcMoment(LocalDateTime irl) {
        long irlDays = ChronoUnit.DAYS.between(calibration.epochStart(), irl.toLocalDate());
        long dayNumber = irlDays * IrlCalibration.IC_DAYS_PER_IRL_DAY
                + irl.getHour() / IrlCalibration.IRL_HOURS_PER_IC_DAY;
        return new IcMoment(dayNumber, timeOfDay(irl.toLocalTime()));
    }

    /** The IC time an IRL time of day stands for, inside its own IC day. */
    static IcTimeOfDay timeOfDay(LocalTime irl) {
        int irlSecondsIntoIcDay = irl.toSecondOfDay() % IRL_SECONDS_PER_IC_DAY;
        return new IcTimeOfDay(irlSecondsIntoIcDay * IrlCalibration.IC_DAYS_PER_IRL_DAY);
    }
}
