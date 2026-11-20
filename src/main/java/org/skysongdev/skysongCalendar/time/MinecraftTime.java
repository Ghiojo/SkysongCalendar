package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.calendar.IcMoment;
import org.skysongdev.skysongCalendar.calendar.IcTimeOfDay;

/**
 * Where a Minecraft world's clock stands at an IC moment: one Minecraft day per IC day.
 *
 * <p>Minecraft days start at 06:00, so IC midnight is tick 18000 of the day before, noon is
 * 6000, and the world's day count (and with it the moon phase) goes up once per IC day.
 */
public final class MinecraftTime {
    public static final long TICKS_PER_DAY = 24_000;

    private static final long TICKS_FROM_MIDNIGHT_TO_DAY_START = 6_000;

    /** The world full time, as {@code World#setFullTime} takes it. */
    public long fullTimeOf(IcMoment moment) {
        return moment.dayNumber() * TICKS_PER_DAY
                + ticksSinceMidnight(moment.timeOfDay())
                - TICKS_FROM_MIDNIGHT_TO_DAY_START;
    }

    private static long ticksSinceMidnight(IcTimeOfDay time) {
        return time.secondOfDay() * TICKS_PER_DAY / IcTimeOfDay.SECONDS_PER_DAY;
    }
}
