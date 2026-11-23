package org.skysongdev.skysongCalendar.events;

import org.skysongdev.skysongCalendar.api.IcDate;

/** One IC day giving way to the next, and what else started with it. */
public record DayChange(IcDate previous, IcDate current) {

    public boolean seasonChanged() {
        return previous.season() != current.season();
    }

    public boolean festivalStarted() {
        return current.isFestival() && !previous.isFestival();
    }
}
