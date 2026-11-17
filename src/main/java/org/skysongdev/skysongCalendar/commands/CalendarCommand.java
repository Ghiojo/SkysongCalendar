package org.skysongdev.skysongCalendar.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.SkysongCalendar;

public class CalendarCommand implements CommandExecutor {
    SkysongCalendar plugin;

    public CalendarCommand(SkysongCalendar plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,@NotNull String s,@NotNull String[] strings) {
        commandSender.sendMessage(this.plugin.getMiniMessage().deserialize("<yellow>You can find our calendar <click:open_url:'https://docs.google.com/spreadsheets/d/1H2pKWQepzdBsrJU-IBsjRGS9DQWaFEd-OeODmss90Cs/edit?usp=sharing'><hover:show_text:'<gold>Click to open Link!</gold>'><blue>Here!</blue></hover></click>"));
        return true;
    }
}
