package org.skysongdev.skysongCalendar.api;

import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * How SkysongCalendar drives Minecraft world time from the IC clock: one Minecraft day per IC
 * day, with daylight that grows and shrinks with the seasons. This view is read only; which
 * worlds follow the clock is set by admins in config.yml.
 *
 * <p>The daylight and Minecraft time methods are pure maths and safe from any thread.
 * {@link #isManaged(World)} must be called on the server's main thread.
 */
@ApiStatus.NonExtendable
public interface WorldTime {

    /**
     * The settings time sync is running with. When config.yml was invalid at start-up, sync is
     * off and this says so, with even 12-hour days.
     */
    @NotNull TimeSyncSettings settings();

    /**
     * Whether SkysongCalendar is setting this world's time right now: sync is enabled, the
     * world has a day cycle (an overworld) and it is not blacklisted.
     *
     * @throws IllegalStateException when called off the server's main thread
     */
    boolean isManaged(@NotNull World world);

    /** IC hours from sunrise to sunset on an IC date, with the current settings. */
    double daylightHours(@NotNull IcDate date);

    /** The IC time of sunrise on an IC date. Sunrise and sunset sit evenly around noon. */
    @NotNull IcTimeOfDay sunrise(@NotNull IcDate date);

    /** The IC time of sunset on an IC date. */
    @NotNull IcTimeOfDay sunset(@NotNull IcDate date);

    /**
     * The world full time, as {@code World#getFullTime} reads it, that managed worlds show at
     * an IC moment. Tick 0 of each Minecraft day is that IC day's sunrise and 12000 its sunset.
     */
    long minecraftTimeAt(@NotNull IcMoment moment);

    /** The world full time managed worlds show right now. */
    long currentMinecraftTime();
}
