package org.skysongdev.skysongCalendar.calendar;

import java.time.LocalTime;

/**
 * A time of day on the IC clock, which has 24 hours like the IRL one but runs
 * {@link IrlCalibration#IC_DAYS_PER_IRL_DAY} times faster.
 *
 * @param secondOfDay seconds since IC midnight, from 0 to {@link #SECONDS_PER_DAY} - 1
 */
public record IcTimeOfDay(int secondOfDay) {
    public static final int SECONDS_PER_DAY = 24 * 60 * 60;

    private static final int IRL_SECONDS_PER_IC_DAY = IrlCalibration.IRL_HOURS_PER_IC_DAY * 60 * 60;

    public IcTimeOfDay {
        if (secondOfDay < 0 || secondOfDay >= SECONDS_PER_DAY) {
            throw new IllegalArgumentException("Not a second of the day: " + secondOfDay);
        }
    }

    /** The IC time an IRL time of day stands for, inside its own IC day. */
    public static IcTimeOfDay of(LocalTime irl) {
        int irlSecondsIntoIcDay = irl.toSecondOfDay() % IRL_SECONDS_PER_IC_DAY;
        return new IcTimeOfDay(irlSecondsIntoIcDay * IrlCalibration.IC_DAYS_PER_IRL_DAY);
    }

    public int hour() {
        return secondOfDay / 3600;
    }

    public int minute() {
        return secondOfDay / 60 % 60;
    }

    public int second() {
        return secondOfDay % 60;
    }
}
