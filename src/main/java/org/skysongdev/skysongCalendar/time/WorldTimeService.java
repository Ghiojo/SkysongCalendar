package org.skysongdev.skysongCalendar.time;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;

import java.util.Optional;

/** Starts and stops driving world time from the IC clock, following {@link TimeSettings}. */
public final class WorldTimeService {
    private final Plugin plugin;
    private final IcTimeSource icTime;
    private final IcDateConverter dates;

    /** Read by the API from any thread, so every thread sees a reload. */
    private volatile TimeSettings settings;
    private ManagedWorlds worlds;
    private BukkitTask task;
    private DaylightCycleGuard guard;

    public WorldTimeService(Plugin plugin, IcTimeSource icTime, IcDateConverter dates) {
        this.plugin = plugin;
        this.icTime = icTime;
        this.dates = dates;
    }

    public void start(TimeSettings settings) {
        stop();
        this.settings = settings;
        if (!settings.enabled()) {
            plugin.getLogger().info("World time sync is disabled in config.yml.");
            return;
        }
        worlds = new ManagedWorlds(settings.blacklist());
        guard = new DaylightCycleGuard(worlds);
        plugin.getServer().getWorlds().forEach(guard::claim);
        plugin.getServer().getPluginManager().registerEvents(guard, plugin);

        MinecraftTime minecraftTime = new MinecraftTime(new SeasonalDaylight(dates, settings.daylight()));
        WorldTimeSync sync = new WorldTimeSync(plugin.getServer(), icTime, minecraftTime, worlds);
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, sync, 0L, settings.updateIntervalTicks());
    }

    /** The settings last started with, even if they disable sync; empty if none were valid. */
    public Optional<TimeSettings> settings() {
        return Optional.ofNullable(settings);
    }

    /** Whether the time of this world follows the IC clock right now. */
    public boolean manages(World world) {
        return worlds != null && worlds.manages(world);
    }

    public void stop() {
        worlds = null;
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (guard != null) {
            HandlerList.unregisterAll(guard);
            guard = null;
        }
    }
}
