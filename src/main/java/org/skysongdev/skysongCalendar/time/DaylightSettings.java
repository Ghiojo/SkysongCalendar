package org.skysongdev.skysongCalendar.time;

/**
 * The {@code time.daylight} section of {@code config.yml}.
 *
 * @param minHours IC hours of daylight at mid-winter
 * @param maxHours IC hours of daylight at mid-summer
 */
public record DaylightSettings(double minHours, double maxHours) {
    public static final DaylightSettings EVEN = new DaylightSettings(12, 12);

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
