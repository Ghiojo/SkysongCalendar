package org.skysongdev.skysongCalendar.calendar;

import java.util.List;

/**
 * The three IC years that make up one IRL year, as laid out in Atmos' calendar.
 * Their festivals last 3, 9 and 6 days; the first one only happens when the IRL
 * year has a Feb 29, so that a cycle lasts exactly as many IRL days as its IRL year.
 */
public final class AtmosCycle {
    private static final List<IcYearLayout> LEAP = years(3);
    private static final List<IcYearLayout> COMMON = years(0);

    private AtmosCycle() {
    }

    /** The IC years of a cycle, in order. */
    public static List<IcYearLayout> years(boolean hasLeapDay) {
        return hasLeapDay ? LEAP : COMMON;
    }

    private static List<IcYearLayout> years(int leapFestivalDays) {
        return List.of(new IcYearLayout(leapFestivalDays), new IcYearLayout(9), new IcYearLayout(6));
    }
}
