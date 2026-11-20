package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.calendar.IcMonthDay;
import org.skysongdev.skysongCalendar.calendar.Month;

/**
 * The {@code time.daylight} section of {@code config.yml}.
 *
 * @param minHours       IC hours of daylight on the winter solstice
 * @param maxHours       IC hours of daylight on the summer solstice
 * @param summerSolstice the longest day; the shortest comes half a year later
 */
public record DaylightSettings(double minHours, double maxHours, IcMonthDay summerSolstice) {
    public static final IcMonthDay DEFAULT_SUMMER_SOLSTICE = new IcMonthDay(Month.RAMPHASTOS, 1);
    public static final DaylightSettings EVEN = new DaylightSettings(12, 12, DEFAULT_SUMMER_SOLSTICE);

    private static final double SHORTEST = 1;
    private static final double LONGEST = 23;

    public DaylightSettings {
        if (minHours < SHORTEST || maxHours > LONGEST || minHours > maxHours) {
            throw new IllegalArgumentException(
                    "Daylight must satisfy " + SHORTEST + " <= min <= max <= " + LONGEST + " hours, got "
                            + minHours + " and " + maxHours);
        }
    }
}
