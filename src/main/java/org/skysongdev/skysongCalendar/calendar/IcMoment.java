package org.skysongdev.skysongCalendar.calendar;

/**
 * A point on the IC timeline.
 *
 * @param dayNumber IC days since the calibration's epoch, whose first day is number 0
 * @param timeOfDay the time within that IC day
 */
public record IcMoment(long dayNumber, IcTimeOfDay timeOfDay) {
}
