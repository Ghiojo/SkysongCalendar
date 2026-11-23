package org.skysongdev.skysongCalendar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.skysongdev.skysongCalendar.api.CalendarItemsApi;
import org.skysongdev.skysongCalendar.api.IcCalendar;
import org.skysongdev.skysongCalendar.api.WorldTime;
import org.skysongdev.skysongCalendar.api.event.CalendarReloadEvent;
import org.skysongdev.skysongCalendar.apiimpl.CalendarStyleImpl;
import org.skysongdev.skysongCalendar.apiimpl.IcCalendarImpl;
import org.skysongdev.skysongCalendar.apiimpl.MainThreadGuard;
import org.skysongdev.skysongCalendar.apiimpl.SkysongCalendarApiImpl;
import org.skysongdev.skysongCalendar.apiimpl.WorldTimeImpl;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IcMomentConverter;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;
import org.skysongdev.skysongCalendar.calendar.IrlIcClock;
import org.skysongdev.skysongCalendar.calendar.IrlIcTimeSource;
import org.skysongdev.skysongCalendar.commands.CalendarCommand;
import org.skysongdev.skysongCalendar.commands.DateCommand;
import org.skysongdev.skysongCalendar.display.ChatPalette;
import org.skysongdev.skysongCalendar.display.IcDateMessages;
import org.skysongdev.skysongCalendar.events.CalendarEventTask;
import org.skysongdev.skysongCalendar.events.DayWatcher;
import org.skysongdev.skysongCalendar.items.CalendarItems;
import org.skysongdev.skysongCalendar.placeholders.CalendarPlaceholderValues;
import org.skysongdev.skysongCalendar.placeholders.CalendarPlaceholders;
import org.skysongdev.skysongCalendar.time.TimeSettings;
import org.skysongdev.skysongCalendar.time.WorldTimeService;

import java.time.Clock;

public final class SkysongCalendar extends JavaPlugin {
    private WorldTimeService worldTime;
    private SkysongCalendarApiImpl api;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Clock irlClock = Clock.system(IrlCalibration.ZONE);
        IcDateConverter dates = new IcDateConverter(IrlCalibration.SKYSONG);
        IcClock clock = new IrlIcClock(irlClock, dates);
        IcTimeSource icTime = new IrlIcTimeSource(irlClock, new IcMomentConverter(IrlCalibration.SKYSONG));
        ChatPalette palette = new ChatPalette();
        IcDateMessages messages = new IcDateMessages(miniMessage, palette);
        MainThreadGuard mainThread = new MainThreadGuard(getServer()::isPrimaryThread);

        worldTime = new WorldTimeService(this, icTime, dates);
        WorldTime worldTimeView = new WorldTimeImpl(worldTime, dates, icTime, mainThread);
        ConfigReload reload = new ConfigReload(this::reloadTimeSettings,
                () -> getServer().getPluginManager().callEvent(new CalendarReloadEvent(worldTimeView.settings())));
        getCommand("calendar").setExecutor(new CalendarCommand(miniMessage, reload));
        getCommand("date").setExecutor(new DateCommand(clock, icTime, messages));
        CalendarItemsApi items = registerCalendarItems(clock, messages, mainThread);
        registerPlaceholders(clock, icTime);

        try {
            worldTime.start(TimeSettings.from(getConfig()));
        } catch (IllegalArgumentException e) {
            getLogger().severe("World time sync is off, config.yml is invalid: " + e.getMessage());
        }

        IcCalendar calendar = new IcCalendarImpl(irlClock, IrlCalibration.SKYSONG);
        api = new SkysongCalendarApiImpl(calendar, new CalendarStyleImpl(miniMessage, palette, messages),
                worldTimeView, items, reload, mainThread);
        api.register(getServer().getServicesManager(), this);
        startCalendarEvents(calendar);
    }

    /** Fires the day, season and festival events; Bukkit cancels the task on disable. */
    private void startCalendarEvents(IcCalendar calendar) {
        CalendarEventTask events = new CalendarEventTask(getServer().getPluginManager(), calendar,
                new DayWatcher(calendar::today));
        getServer().getScheduler().runTaskTimer(this, events, CalendarEventTask.INTERVAL_TICKS, CalendarEventTask.INTERVAL_TICKS);
    }

    /** Re-reads config.yml; if it is invalid, throws and leaves the running settings alone. */
    private void reloadTimeSettings() {
        reloadConfig();
        worldTime.start(TimeSettings.from(getConfig()));
    }

    @Override
    public void onDisable() {
        if (api != null) {
            api.unregister(getServer().getServicesManager());
        }
        if (worldTime != null) {
            worldTime.stop();
        }
    }

    /** Placeholders need PlaceholderAPI; without it the plugin runs without them. */
    private void registerPlaceholders(IcClock clock, IcTimeSource icTime) {
        if (!getServer().getPluginManager().isPluginEnabled(CalendarPlaceholders.PLACEHOLDER_API)) {
            getLogger().info(CalendarPlaceholders.PLACEHOLDER_API + " is not enabled; placeholders are off.");
            return;
        }
        new CalendarPlaceholders(this, new CalendarPlaceholderValues(clock, icTime)).register();
    }

    /**
     * Calendar items need SkysongItems; without it the plugin runs with its commands only.
     *
     * @return the items' API view, or null when SkysongItems is not enabled
     */
    private CalendarItemsApi registerCalendarItems(IcClock clock, IcDateMessages messages, MainThreadGuard mainThread) {
        if (!getServer().getPluginManager().isPluginEnabled(CalendarItems.SKYSONG_ITEMS)) {
            getLogger().info(CalendarItems.SKYSONG_ITEMS + " is not enabled; calendar items are off.");
            return null;
        }
        return CalendarItems.register(this, clock, messages, mainThread);
    }
}
