package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;

/**
 * Where a Minecraft world's clock stands at an IC moment: one Minecraft day per IC day.
 *
 * <p>Minecraft's day starts at sunrise (tick 0), sets at tick 12000 and the next sunrise
 * comes at 24000. Each IC sunrise and sunset from the {@link DaylightSchedule} is pinned to
 * those ticks, and the sun moves at an even speed in between, so longer days make it move
 * slower by day and faster by night. Noon is always tick 6000, and the clock never jumps
 * because each night runs from one day's sunset to the next day's sunrise.
 */
public final class MinecraftTime {
    public static final long TICKS_PER_DAY = 24_000;

    private static final long TICKS_PER_HALF_DAY = TICKS_PER_DAY / 2;

    private final DaylightSchedule daylight;

    public MinecraftTime(DaylightSchedule daylight) {
        this.daylight = daylight;
    }

    /** The world full time, as {@code World#setFullTime} takes it. */
    public long fullTimeOf(IcMoment moment) {
        long day = moment.dayNumber();
        int now = moment.timeOfDay().secondOfDay();
        int sunrise = sunrise(day);
        int sunset = sunset(day);

        if (now < sunrise) {
            int lastSunset = sunset(day - 1) - IcTimeOfDay.SECONDS_PER_DAY;
            return nightStart(day - 1) + between(lastSunset, now, sunrise);
        }
        if (now < sunset) {
            return dayStart(day) + between(sunrise, now, sunset);
        }
        int nextSunrise = sunrise(day + 1) + IcTimeOfDay.SECONDS_PER_DAY;
        return nightStart(day) + between(sunset, now, nextSunrise);
    }

    private int sunrise(long day) {
        return DaylightSchedule.sunrise(daylight.daylightSeconds(day));
    }

    private int sunset(long day) {
        return DaylightSchedule.sunset(daylight.daylightSeconds(day));
    }

    private static long dayStart(long day) {
        return day * TICKS_PER_DAY;
    }

    private static long nightStart(long day) {
        return dayStart(day) + TICKS_PER_HALF_DAY;
    }

    /** Ticks into a half day that runs from {@code start} to {@code end} IC seconds. */
    private static long between(int start, int now, int end) {
        return (now - start) * TICKS_PER_HALF_DAY / (end - start);
    }
}
