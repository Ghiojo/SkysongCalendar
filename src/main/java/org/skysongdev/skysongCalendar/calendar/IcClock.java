package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcDate;

/** Tells the IC date right now. */
@FunctionalInterface
public interface IcClock {
    IcDate today();
}
