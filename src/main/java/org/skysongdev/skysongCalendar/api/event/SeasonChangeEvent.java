package org.skysongdev.skysongCalendar.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.Season;

/**
 * A new season has started, about every 30 IRL days. Fired on the main thread right after
 * the {@link IcDayChangeEvent} of the season's first day.
 */
public final class SeasonChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Season previous;
    private final Season current;
    private final long seasonCount;

    public SeasonChangeEvent(@NotNull Season previous, @NotNull Season current, long seasonCount) {
        this.previous = previous;
        this.current = current;
        this.seasonCount = seasonCount;
    }

    /** The season that just ended. */
    public @NotNull Season previous() {
        return previous;
    }

    /** The season that just started. */
    public @NotNull Season current() {
        return current;
    }

    /** The season count now, as {@code calendar().seasonCount()} gives it. */
    public long seasonCount() {
        return seasonCount;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
