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

import java.time.Clock;

public final class SkysongCalendar extends JavaPlugin {

    @Override
    public void onEnable() {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        IcClock clock = new IrlIcClock(Clock.systemDefaultZone(), new IcDateConverter(IrlCalibration.SKYSONG));
        IcDateMessages messages = new IcDateMessages(miniMessage, new ChatPalette());

        getCommand("calendar").setExecutor(new CalendarCommand(miniMessage));
        getCommand("date").setExecutor(new DateCommand(clock, messages));
    }
}
