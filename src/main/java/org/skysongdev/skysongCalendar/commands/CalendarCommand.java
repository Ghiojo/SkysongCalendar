package org.skysongdev.skysongCalendar.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/** {@code /calendar}: links to the public calendar sheet. */
public class CalendarCommand implements CommandExecutor {
    private static final String CALENDAR_LINK = "<yellow>You can find our calendar <click:open_url:'https://docs.google.com/spreadsheets/d/1H2pKWQepzdBsrJU-IBsjRGS9DQWaFEd-OeODmss90Cs/edit?usp=sharing'><hover:show_text:'<gold>Click to open Link!</gold>'><blue>Here!</blue></hover></click>";

    private final MiniMessage miniMessage;

    public CalendarCommand(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(miniMessage.deserialize(CALENDAR_LINK));
        return true;
    }
}
