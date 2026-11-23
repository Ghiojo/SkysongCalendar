package org.skysongdev.skysongCalendar.api;

/**
 * A day of the IC calendar.
 *
 * @param year   the year, counted After the Blight (AB)
 * @param period the month or festival the day falls in
 * @param day    the day of that month or festival, counting from 1
 */
public record IcDate(int year, CalendarPeriod period, int day) {

    public Weekday weekday() {
        return Weekday.ofDay(day);
    }

    public Season season() {
        return period.season();
    }

    /** Whether the day is one of the festival days that close the year. */
    public boolean isFestival() {
        return period instanceof Festival;
    }

    /** Days since the year's first day, Celestine 1 being 0; festival days come after Litoria 45. */
    public int dayOfYear() {
        int periodStart = period instanceof Month month
                ? month.ordinal() * Month.LENGTH
                : Month.values().length * Month.LENGTH;
        return periodStart + day - 1;
    }
}
