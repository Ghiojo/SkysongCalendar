package org.skysongdev.skysongCalendar.calendar;

/** Tells the IC date right now. */
@FunctionalInterface
public interface IcClock {
    IcDate today();
}
