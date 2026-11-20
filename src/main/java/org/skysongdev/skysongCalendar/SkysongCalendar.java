package org.skysongdev.skysongCalendar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;
import org.skysongdev.skysongCalendar.calendar.IrlIcClock;
import org.skysongdev.skysongCalendar.commands.CalendarCommand;
import org.skysongdev.skysongCalendar.commands.DateCommand;
import org.skysongdev.skysongCalendar.display.ChatPalette;
import org.skysongdev.skysongCalendar.display.IcDateMessages;
import org.skysongdev.skysongCalendar.items.CalendarItems;

import java.time.Clock;

public final class SkysongCalendar extends JavaPlugin {

    @Override
    public void onEnable() {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        IcClock clock = new IrlIcClock(Clock.system(IrlCalibration.ZONE), new IcDateConverter(IrlCalibration.SKYSONG));
        IcDateMessages messages = new IcDateMessages(miniMessage, new ChatPalette());

        getCommand("calendar").setExecutor(new CalendarCommand(miniMessage));
        getCommand("date").setExecutor(new DateCommand(clock, messages));
        registerCalendarItems(clock, messages);
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
