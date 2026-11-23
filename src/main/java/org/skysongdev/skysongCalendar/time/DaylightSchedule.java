package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.api.IcTimeOfDay;

/** How long the sun is up on each IC day. Sunrise and sunset sit evenly around noon. */
@FunctionalInterface
public interface DaylightSchedule {
    int NOON = IcTimeOfDay.SECONDS_PER_DAY / 2;

    /** IC seconds from sunrise to sunset on this IC day number. */
    int daylightSeconds(long dayNumber);

    /** The IC second of sunrise on a day with this much daylight. */
    static int sunrise(int daylightSeconds) {
        return NOON - daylightSeconds / 2;
    }

    /** The IC second of sunset on a day with this much daylight. */
    static int sunset(int daylightSeconds) {
        return NOON + daylightSeconds / 2;
    }
}
