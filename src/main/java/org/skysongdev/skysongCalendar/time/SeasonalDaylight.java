package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.calendar.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.Month;

/**
 * Daylight that follows the seasons on a smooth curve: shortest in the middle of Vinse'er,
 * longest in the middle of Su'untah, halfway in between at the middle of Florescia and Altrue.
 */
public final class SeasonalDaylight implements DaylightSchedule {
    private static final double DAYS_PER_CURVE = Month.values().length * Month.LENGTH;
    /** The middle of Rana and Litoria, Vinse'er's months. */
    private static final double MID_WINTER = 7 * Month.LENGTH - 0.5;
    private static final int SECONDS_PER_HOUR = 3600;

    private final IcDateConverter dates;
    private final double meanHours;
    private final double swingHours;

    public SeasonalDaylight(IcDateConverter dates, DaylightSettings settings) {
        this.dates = dates;
        this.meanHours = (settings.minHours() + settings.maxHours()) / 2;
        this.swingHours = (settings.maxHours() - settings.minHours()) / 2;
    }

    @Override
    public int daylightSeconds(long dayNumber) {
        return (int) Math.round(hoursOn(dates.dateOfDay(dayNumber)) * SECONDS_PER_HOUR);
    }

    private double hoursOn(IcDate date) {
        double angle = 2 * Math.PI * (date.dayOfYear() - MID_WINTER) / DAYS_PER_CURVE;
        return meanHours - swingHours * Math.cos(angle);
    }
}
