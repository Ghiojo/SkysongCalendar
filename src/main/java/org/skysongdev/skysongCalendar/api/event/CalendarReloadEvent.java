package org.skysongdev.skysongCalendar.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.TimeSyncSettings;

/**
 * SkysongCalendar's config.yml was reloaded and applied, by {@code /calendar reload} or the
 * API's {@code reload()}. A reload that failed fires nothing. Fired on the main thread.
 */
public final class CalendarReloadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final TimeSyncSettings settings;

    public CalendarReloadEvent(@NotNull TimeSyncSettings settings) {
        this.settings = settings;
    }

    /** The time sync settings now in use. */
    public @NotNull TimeSyncSettings settings() {
        return settings;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
