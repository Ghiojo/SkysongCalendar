package org.skysongdev.skysongCalendar.time;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * Stops vanilla from moving the sun in managed worlds, so only the IC clock does. Worlds
 * loaded later, by Multiverse for example, are claimed as they load.
 */
public final class DaylightCycleGuard implements Listener {
    private final ManagedWorlds worlds;

    public DaylightCycleGuard(ManagedWorlds worlds) {
        this.worlds = worlds;
    }

    public void claim(World world) {
        if (worlds.manages(world)) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        claim(event.getWorld());
    }
}
