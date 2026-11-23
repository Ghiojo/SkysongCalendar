package org.skysongdev.skysongCalendar.events;

import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcClock;

import java.util.Optional;

/** Notices when the IC date moves on, starting from the date it was created on. */
public final class DayWatcher {
    private final IcClock clock;
    private IcDate last;

    public DayWatcher(IcClock clock) {
        this.clock = clock;
        this.last = clock.today();
    }

    /** The change since the last check, or empty if it is still the same IC day. */
    public Optional<DayChange> check() {
        IcDate today = clock.today();
        if (today.equals(last)) {
            return Optional.empty();
        }
        DayChange change = new DayChange(last, today);
        last = today;
        return Optional.of(change);
    }
}
