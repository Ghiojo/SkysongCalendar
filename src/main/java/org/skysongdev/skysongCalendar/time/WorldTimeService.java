package org.skysongdev.skysongCalendar.time;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;

/** Starts and stops driving world time from the IC clock, following {@link TimeSettings}. */
public final class WorldTimeService {
    private final Plugin plugin;
    private final IcTimeSource icTime;
    private final MinecraftTime minecraftTime;

    private BukkitTask task;
    private DaylightCycleGuard guard;

    public WorldTimeService(Plugin plugin, IcTimeSource icTime, MinecraftTime minecraftTime) {
        this.plugin = plugin;
        this.icTime = icTime;
        this.minecraftTime = minecraftTime;
    }

    public void start(TimeSettings settings) {
        stop();
        if (!settings.enabled()) {
            plugin.getLogger().info("World time sync is disabled in config.yml.");
            return;
        }
        ManagedWorlds worlds = new ManagedWorlds(settings.blacklist());
        guard = new DaylightCycleGuard(worlds);
        plugin.getServer().getWorlds().forEach(guard::claim);
        plugin.getServer().getPluginManager().registerEvents(guard, plugin);

        WorldTimeSync sync = new WorldTimeSync(plugin.getServer(), icTime, minecraftTime, worlds);
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, sync, 0L, settings.updateIntervalTicks());
    }

    public void stop() {
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
