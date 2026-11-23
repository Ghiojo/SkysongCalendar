package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcMoment;

/** Tells the IC moment right now. */
@FunctionalInterface
public interface IcTimeSource {
    IcMoment now();
}
