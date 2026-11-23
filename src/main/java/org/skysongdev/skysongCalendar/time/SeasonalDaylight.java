package org.skysongdev.skysongCalendar.time;

import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;

/**
 * Daylight that follows the seasons on a smooth curve: longest on the summer solstice,
 * shortest half a year later on the winter solstice, and even halfway in between.
 */
public final class SeasonalDaylight implements DaylightSchedule {
    private static final double DAYS_PER_CURVE = Month.values().length * Month.LENGTH;
    private static final int SECONDS_PER_HOUR = 3600;

    private final IcDateConverter dates;
    private final double meanHours;
    private final double swingHours;
    private final int summerSolstice;

    public SeasonalDaylight(IcDateConverter dates, DaylightSettings settings) {
        this.dates = dates;
        this.meanHours = (settings.minHours() + settings.maxHours()) / 2;
        this.swingHours = (settings.maxHours() - settings.minHours()) / 2;
        this.summerSolstice = settings.summerSolstice().dayOfYear();
    }

    @Override
    public int daylightSeconds(long dayNumber) {
        return (int) Math.round(hoursOn(dates.dateOfDay(dayNumber)) * SECONDS_PER_HOUR);
    }

    private double hoursOn(IcDate date) {
        double angle = 2 * Math.PI * (date.dayOfYear() - summerSolstice) / DAYS_PER_CURVE;
        return meanHours + swingHours * Math.cos(angle);
    }
}
