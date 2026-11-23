package org.skysongdev.skysongCalendar.api;

/**
 * A time of day on the IC clock, which has 24 hours like the IRL one but runs three times
 * faster: one IC day lasts 8 IRL hours.
 *
 * @param secondOfDay seconds since IC midnight, from 0 to {@link #SECONDS_PER_DAY} - 1
 */
public record IcTimeOfDay(int secondOfDay) {
    public static final int SECONDS_PER_DAY = 24 * 60 * 60;

    public IcTimeOfDay {
        if (secondOfDay < 0 || secondOfDay >= SECONDS_PER_DAY) {
            throw new IllegalArgumentException("Not a second of the day: " + secondOfDay);
        }
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
