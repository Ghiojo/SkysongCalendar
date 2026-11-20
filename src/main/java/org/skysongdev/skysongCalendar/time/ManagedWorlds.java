package org.skysongdev.skysongCalendar.time;

import org.bukkit.World;

/** Decides which worlds follow the IC clock: those with a day cycle that aren't blacklisted. */
public final class ManagedWorlds {
    private final WorldBlacklist blacklist;

    public ManagedWorlds(WorldBlacklist blacklist) {
        this.blacklist = blacklist;
    }

    public boolean manages(World world) {
        return world.getEnvironment() == World.Environment.NORMAL && !blacklist.contains(world.getName());
    }
}
