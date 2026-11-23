package org.skysongdev.skysongCalendar.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.IcDate;

/**
 * A new IC day has started, every 8 IRL hours at 00:00, 08:00 and 16:00 UTC+1. Fired on the
 * main thread while the server runs; a day that started while it was down fires nothing, so
 * read {@code calendar().today()} in your own {@code onEnable}.
 *
 * <p>When a day also starts a season or a festival, this event comes first, then
 * {@link SeasonChangeEvent}, then {@link FestivalStartEvent}.
 */
public final class IcDayChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IcDate previous;
    private final IcDate current;

    public IcDayChangeEvent(@NotNull IcDate previous, @NotNull IcDate current) {
        this.previous = previous;
        this.current = current;
    }

    /** The day that just ended. */
    public @NotNull IcDate previous() {
        return previous;
    }

    /** The day that just started. */
    public @NotNull IcDate current() {
        return current;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
