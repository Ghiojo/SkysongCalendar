package org.skysongdev.skysongCalendar.calendar;

/** Tells the IC moment right now. */
@FunctionalInterface
public interface IcTimeSource {
    IcMoment now();
}
