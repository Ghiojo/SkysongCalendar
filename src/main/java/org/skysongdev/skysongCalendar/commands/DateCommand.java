package org.skysongdev.skysongCalendar.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.SkysongCalendar;

public class DateCommand implements CommandExecutor {
    SkysongCalendar plugin;

    public DateCommand(SkysongCalendar plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        MiniMessage miniMessage = this.plugin.getMiniMessage();
        String dayName = this.plugin.getUtils().getCurrentDayName();
        commandSender.sendMessage(miniMessage.deserialize("<yellow>Today is " + dayName + "<yellow>, " + this.plugin.getUtils().getCurrentDay() + "<yellow> of " + this.plugin.getUtils().getCurrentMonth() + "<yellow>, " + this.plugin.getUtils().getCurrentYear() + "<yellow> AB."));
        commandSender.sendMessage(this.plugin.getMiniMessage().deserialize("<yellow>The season is " + this.plugin.getUtils().getCurrentSeason()));
        return true;
    }
}
