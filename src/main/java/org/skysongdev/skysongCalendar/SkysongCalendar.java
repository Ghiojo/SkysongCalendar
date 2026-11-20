package org.skysongdev.skysongCalendar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
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
import org.skysongdev.skysongCalendar.time.TimeSettings;
import org.skysongdev.skysongCalendar.time.WorldTimeService;

import java.time.Clock;

public final class SkysongCalendar extends JavaPlugin {
    private WorldTimeService worldTime;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Clock irlClock = Clock.system(IrlCalibration.ZONE);
        IcDateConverter dates = new IcDateConverter(IrlCalibration.SKYSONG);
        IcClock clock = new IrlIcClock(irlClock, dates);
        IcTimeSource icTime = new IrlIcTimeSource(irlClock, new IcMomentConverter(IrlCalibration.SKYSONG));
        IcDateMessages messages = new IcDateMessages(miniMessage, new ChatPalette());

        getCommand("calendar").setExecutor(new CalendarCommand(miniMessage));
        getCommand("date").setExecutor(new DateCommand(clock, icTime, messages));
        registerCalendarItems(clock, messages);

        worldTime = new WorldTimeService(this, icTime, dates);
        worldTime.start(TimeSettings.from(getConfig()));
    }

    @Override
    public void onDisable() {
        if (worldTime != null) {
            worldTime.stop();
        }
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
