package org.skysongdev.skysongCalendar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.skysongdev.skysongCalendar.apiimpl.CalendarStyleImpl;
import org.skysongdev.skysongCalendar.apiimpl.IcCalendarImpl;
import org.skysongdev.skysongCalendar.apiimpl.SkysongCalendarApiImpl;
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

        getCommand("calendar").setExecutor(new CalendarCommand(miniMessage, this::reloadTimeSettings));
        getCommand("date").setExecutor(new DateCommand(clock, icTime, messages));
        registerCalendarItems(clock, messages);
        registerPlaceholders(clock, icTime);

        worldTime = new WorldTimeService(this, icTime, dates);
        try {
            worldTime.start(TimeSettings.from(getConfig()));
        } catch (IllegalArgumentException e) {
            getLogger().severe("World time sync is off, config.yml is invalid: " + e.getMessage());
        }

        api = new SkysongCalendarApiImpl(new IcCalendarImpl(irlClock, IrlCalibration.SKYSONG),
                new CalendarStyleImpl(miniMessage, palette, messages));
        api.register(getServer().getServicesManager(), this);
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

    /** Calendar items need SkysongItems; without it the plugin runs with its commands only. */
    private void registerCalendarItems(IcClock clock, IcDateMessages messages) {
        if (!getServer().getPluginManager().isPluginEnabled(CalendarItems.SKYSONG_ITEMS)) {
            getLogger().info(CalendarItems.SKYSONG_ITEMS + " is not enabled; calendar items are off.");
            return;
        }
        CalendarItems.register(this, clock, messages);
    }
}
