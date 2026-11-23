package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcDate;

import java.time.Clock;
import java.time.LocalDateTime;

/** The IC date that the IRL time on a {@link Clock} stands for. */
public final class IrlIcClock implements IcClock {
    private final Clock clock;
    private final IcDateConverter converter;

    public IrlIcClock(Clock clock, IcDateConverter converter) {
        this.clock = clock;
        this.converter = converter;
    }

    @Override
    public IcDate today() {
        return converter.toIcDate(LocalDateTime.now(clock));
    }
}
