package org.skysongdev.skysongCalendar.api;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * The time sync settings SkysongCalendar is running with, from the {@code time} section of its
 * config.yml. A snapshot: a reload never changes a value a plugin is holding.
 *
 * @param enabled             whether SkysongCalendar drives the time of managed worlds
 * @param updateIntervalTicks how often it sets world time, in server ticks
 * @param blacklistedWorlds   names of worlds it never touches, in lower case
 * @param minDaylightHours    IC hours of daylight on the winter solstice
 * @param maxDaylightHours    IC hours of daylight on the summer solstice
 * @param summerSolstice      the longest day of every IC year
 */
public record TimeSyncSettings(boolean enabled, int updateIntervalTicks, @NotNull Set<String> blacklistedWorlds,
                               double minDaylightHours, double maxDaylightHours, @NotNull IcMonthDay summerSolstice) {
    private static final int DAYS_TO_WINTER = Month.values().length * Month.LENGTH / 2;

    public TimeSyncSettings {
        blacklistedWorlds = Set.copyOf(blacklistedWorlds);
    }

    /** The shortest day of every IC year, half a year (180 days) after the summer solstice. */
    public @NotNull IcMonthDay winterSolstice() {
        int dayOfYear = (summerSolstice.dayOfYear() + DAYS_TO_WINTER) % (DAYS_TO_WINTER * 2);
        return new IcMonthDay(Month.values()[dayOfYear / Month.LENGTH], dayOfYear % Month.LENGTH + 1);
    }
}
