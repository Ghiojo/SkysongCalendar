package org.skysongdev.skysongCalendar.calendar;

import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Season;

/**
 * How many season changes have happened since the start of an epoch year. It goes up by 1
 * at every season change and never goes down, so SkysongItems can count timers in seasons.
 */
public final class SeasonCount {
    private static final int SEASONS_PER_YEAR = Season.values().length;

    private final int epochYear;

    /** @param epochYear the IC year whose first season counts as 0 */
    public SeasonCount(int epochYear) {
        this.epochYear = epochYear;
    }

    public long of(IcDate date) {
        return (long) (date.year() - epochYear) * SEASONS_PER_YEAR + date.season().ordinal();
    }
}
