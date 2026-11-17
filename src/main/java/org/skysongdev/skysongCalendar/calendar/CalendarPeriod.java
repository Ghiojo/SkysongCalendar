package org.skysongdev.skysongCalendar.calendar;

/** A named stretch of days within an IC year: a month or a festival. */
public interface CalendarPeriod {
    String displayName();

    Season season();
}
