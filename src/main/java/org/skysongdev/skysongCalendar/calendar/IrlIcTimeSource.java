package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcMoment;

import java.time.Clock;
import java.time.LocalDateTime;

/** The IC moment that the IRL time on a {@link Clock} stands for. */
public final class IrlIcTimeSource implements IcTimeSource {
    private final Clock clock;
    private final IcMomentConverter converter;

    public IrlIcTimeSource(Clock clock, IcMomentConverter converter) {
        this.clock = clock;
        this.converter = converter;
    }

    @Override
    public IcMoment now() {
        return converter.toIcMoment(LocalDateTime.now(clock));
    }
}
