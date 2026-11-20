package org.skysongdev.skysongCalendar.time;

import org.bukkit.Server;
import org.bukkit.World;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;

/**
 * Sets every managed world to the IC time. Each run reads the IRL clock afresh, so lag or
 * a restart never makes the worlds drift; the work per run doesn't grow with players.
 */
public final class WorldTimeSync implements Runnable {
    private final Server server;
    private final IcTimeSource icTime;
    private final MinecraftTime minecraftTime;
    private final ManagedWorlds worlds;

    public WorldTimeSync(Server server, IcTimeSource icTime, MinecraftTime minecraftTime, ManagedWorlds worlds) {
        this.server = server;
        this.icTime = icTime;
        this.minecraftTime = minecraftTime;
        this.worlds = worlds;
    }

    @Override
    public void run() {
        long fullTime = minecraftTime.fullTimeOf(icTime.now());
        for (World world : server.getWorlds()) {
            if (worlds.manages(world)) {
                world.setFullTime(fullTime);
            }
        }
    }
}
