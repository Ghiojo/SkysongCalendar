package org.skysongdev.skysongCalendar.time;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldBlacklistTest {
    private final WorldBlacklist blacklist = new WorldBlacklist(List.of("creative", "Events"));

    @Test
    void containsListedWorldsIgnoringCase() {
        assertTrue(blacklist.contains("creative"));
        assertTrue(blacklist.contains("events"));
        assertTrue(blacklist.contains("CREATIVE"));
    }

    @Test
    void leavesOtherWorldsOut() {
        assertFalse(blacklist.contains("world"));
        assertFalse(new WorldBlacklist(List.of()).contains("world"));
    }
}
