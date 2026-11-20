package org.skysongdev.skysongCalendar.time;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/** Worlds whose time this plugin must never touch, matched by name, ignoring case. */
public final class WorldBlacklist {
    private final Set<String> names;

    public WorldBlacklist(Collection<String> names) {
        this.names = names.stream().map(WorldBlacklist::normalise).collect(Collectors.toUnmodifiableSet());
    }

    public boolean contains(String worldName) {
        return names.contains(normalise(worldName));
    }

    private static String normalise(String worldName) {
        return worldName.toLowerCase(Locale.ROOT);
    }
}
