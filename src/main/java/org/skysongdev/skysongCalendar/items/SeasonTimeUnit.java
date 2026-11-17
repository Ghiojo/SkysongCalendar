package org.skysongdev.skysongCalendar.items;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.SeasonCount;
import org.skysongdev.skysongitems.api.event.SkysongItemsRegisterEvent;

/**
 * The SkysongItems timer unit {@code season}: a timer of {@code 1season} fires at the
 * next season change, {@code 2season} at the second one, and so on.
 */
public final class SeasonTimeUnit implements Listener {
    public static final String ID = "season";

    private final Plugin owner;
    private final IcClock clock;
    private final SeasonCount seasons;

    public SeasonTimeUnit(Plugin owner, IcClock clock, SeasonCount seasons) {
        this.owner = owner;
        this.clock = clock;
        this.seasons = seasons;
    }

    /** SkysongItems forgets plugin units on every load, so this registers it each time. */
    @EventHandler
    public void onRegister(SkysongItemsRegisterEvent event) {
        if (!event.registerTimeUnit(owner, ID, () -> seasons.of(clock.today()))) {
            owner.getLogger().warning("SkysongItems refused the '" + ID + "' time unit; season timers won't work.");
        }
    }
}
