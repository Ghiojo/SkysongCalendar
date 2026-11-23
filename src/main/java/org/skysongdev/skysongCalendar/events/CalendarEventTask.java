package org.skysongdev.skysongCalendar.events;

import org.bukkit.plugin.PluginManager;
import org.skysongdev.skysongCalendar.api.IcCalendar;
import org.skysongdev.skysongCalendar.api.event.FestivalStartEvent;
import org.skysongdev.skysongCalendar.api.event.IcDayChangeEvent;
import org.skysongdev.skysongCalendar.api.event.SeasonChangeEvent;

/**
 * Fires the calendar's day, season and festival events. It runs on its own timer, apart
 * from world time sync, so the events come even when sync is off.
 */
public final class CalendarEventTask implements Runnable {
    /** Once a second: a day lasts 8 IRL hours, so events are at most a second late. */
    public static final long INTERVAL_TICKS = 20;

    private final PluginManager plugins;
    private final IcCalendar calendar;
    private final DayWatcher watcher;

    public CalendarEventTask(PluginManager plugins, IcCalendar calendar, DayWatcher watcher) {
        this.plugins = plugins;
        this.calendar = calendar;
        this.watcher = watcher;
    }

    @Override
    public void run() {
        watcher.check().ifPresent(this::publish);
    }

    private void publish(DayChange change) {
        plugins.callEvent(new IcDayChangeEvent(change.previous(), change.current()));
        if (change.seasonChanged()) {
            plugins.callEvent(new SeasonChangeEvent(change.previous().season(), change.current().season(),
                    calendar.seasonCountOf(change.current())));
        }
        if (change.festivalStarted()) {
            plugins.callEvent(new FestivalStartEvent(change.current(), calendar.festivalDays(change.current().year())));
        }
    }
}
