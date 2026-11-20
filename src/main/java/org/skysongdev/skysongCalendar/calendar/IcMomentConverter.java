package org.skysongdev.skysongCalendar.calendar;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** Turns an IRL date and time into the IC moment it stands for. */
public final class IcMomentConverter {
    private final IrlCalibration calibration;

    public IcMomentConverter(IrlCalibration calibration) {
        this.calibration = calibration;
    }

    public IcMoment toIcMoment(LocalDateTime irl) {
        long irlDays = ChronoUnit.DAYS.between(calibration.epochStart(), irl.toLocalDate());
        long dayNumber = irlDays * IrlCalibration.IC_DAYS_PER_IRL_DAY
                + irl.getHour() / IrlCalibration.IRL_HOURS_PER_IC_DAY;
        return new IcMoment(dayNumber, IcTimeOfDay.of(irl.toLocalTime()));
    }
}
