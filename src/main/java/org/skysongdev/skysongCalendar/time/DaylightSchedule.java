package org.skysongdev.skysongCalendar.time;

/** How long the sun is up on each IC day. Sunrise and sunset sit evenly around noon. */
@FunctionalInterface
public interface DaylightSchedule {
    /** IC seconds from sunrise to sunset on this IC day number. */
    int daylightSeconds(long dayNumber);
}
