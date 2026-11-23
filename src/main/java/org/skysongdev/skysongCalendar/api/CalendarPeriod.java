package org.skysongdev.skysongCalendar.api;

/**
 * A named stretch of days within an IC year: a {@link Month} or the {@link Festival}.
 * It is sealed, so a {@code switch} over it covers every case.
 */
public sealed interface CalendarPeriod permits Month, Festival {
    String displayName();

    Season season();
}
