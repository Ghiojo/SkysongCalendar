package org.skysongdev.skysongCalendar.time;

import org.bukkit.configuration.Configuration;
import org.skysongdev.skysongCalendar.calendar.IcMonthDay;

/**
 * The {@code time} section of {@code config.yml}.
 *
 * @param enabled             whether the plugin drives world time at all
 * @param updateIntervalTicks how often world time is set, in server ticks
 * @param blacklist           worlds left alone
 * @param daylight            how long the sun is up through the seasons
 */
public record TimeSettings(boolean enabled, int updateIntervalTicks, WorldBlacklist blacklist,
                           DaylightSettings daylight) {
    private static final int MIN_INTERVAL_TICKS = 1;

    public static TimeSettings from(Configuration config) {
        return new TimeSettings(
                config.getBoolean("time.enabled"),
                Math.max(MIN_INTERVAL_TICKS, config.getInt("time.update-interval-ticks")),
                new WorldBlacklist(config.getStringList("time.blacklisted-worlds")),
                new DaylightSettings(
                        config.getDouble("time.daylight.min-hours", DaylightSettings.EVEN.minHours()),
                        config.getDouble("time.daylight.max-hours", DaylightSettings.EVEN.maxHours()),
                        IcMonthDay.parse(config.getString("time.daylight.summer-solstice",
                                DaylightSettings.DEFAULT_SUMMER_SOLSTICE.toString()))));
    }
}
