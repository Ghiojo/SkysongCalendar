package org.skysongdev.skysongCalendar.calendar;

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
}
