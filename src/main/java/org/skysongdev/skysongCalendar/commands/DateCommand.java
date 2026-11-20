package org.skysongdev.skysongCalendar.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;
import org.skysongdev.skysongCalendar.display.IcDateMessages;

/** {@code /date}: tells the sender today's IC date, the IC time and the season. */
public class DateCommand implements CommandExecutor {
    private final IcClock clock;
    private final IcTimeSource time;
    private final IcDateMessages messages;

    public DateCommand(IcClock clock, IcTimeSource time, IcDateMessages messages) {
        this.clock = clock;
        this.time = time;
        this.messages = messages;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        messages.describe(clock.today(), time.now().timeOfDay()).forEach(sender::sendMessage);
        return true;
    }
}
