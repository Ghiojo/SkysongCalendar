package org.skysongdev.skysongCalendar.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.IcDate;

/**
 * The festival that closes the IC year has started. Fired on the main thread after the
 * {@link IcDayChangeEvent} and {@link SeasonChangeEvent} of its first day.
 */
public final class FestivalStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IcDate date;
    private final int festivalDays;

    public FestivalStartEvent(@NotNull IcDate date, int festivalDays) {
        this.date = date;
        this.festivalDays = festivalDays;
    }

    /** The festival's first day. */
    public @NotNull IcDate date() {
        return date;
    }

    /** How many days this year's festival lasts: 3, 9 or 6. */
    public int festivalDays() {
        return festivalDays;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
