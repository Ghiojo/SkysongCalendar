package org.skysongdev.skysongCalendar.time;

import org.bukkit.configuration.Configuration;

/**
 * The {@code time} section of {@code config.yml}.
 *
 * @param enabled             whether the plugin drives world time at all
 * @param updateIntervalTicks how often world time is set, in server ticks
 * @param blacklist           worlds left alone
 */
public record TimeSettings(boolean enabled, int updateIntervalTicks, WorldBlacklist blacklist) {
    private static final int MIN_INTERVAL_TICKS = 1;

    public static TimeSettings from(Configuration config) {
        return new TimeSettings(
                config.getBoolean("time.enabled"),
                Math.max(MIN_INTERVAL_TICKS, config.getInt("time.update-interval-ticks")),
                new WorldBlacklist(config.getStringList("time.blacklisted-worlds")));
    }
}
