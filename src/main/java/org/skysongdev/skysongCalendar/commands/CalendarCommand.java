package org.skysongdev.skysongCalendar.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.ReloadResult;

import java.util.List;
import java.util.function.Supplier;

/** {@code /calendar reload}: re-reads {@code config.yml} and applies it. */
public class CalendarCommand implements TabExecutor {
    public static final String RELOAD_PERMISSION = "skysongcalendar.reload";

    private static final String RELOAD = "reload";
    private static final String USAGE = "<yellow>Usage: /calendar reload";
    private static final String NO_PERMISSION = "<red>You don't have permission to reload the calendar.";
    private static final String RELOADED = "<yellow>SkysongCalendar's config was reloaded.";
    private static final String RELOAD_FAILED = "<red>The config couldn't be loaded, so nothing changed: <reason>";

    private final MiniMessage miniMessage;
    private final Supplier<ReloadResult> reload;

    /** @param reload re-reads the config and applies it, or leaves it alone if it is invalid */
    public CalendarCommand(MiniMessage miniMessage, Supplier<ReloadResult> reload) {
        this.miniMessage = miniMessage;
        this.reload = reload;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase(RELOAD)) {
            sender.sendMessage(miniMessage.deserialize(USAGE));
        } else if (!sender.hasPermission(RELOAD_PERMISSION)) {
            sender.sendMessage(miniMessage.deserialize(NO_PERMISSION));
        } else {
            reloadFor(sender);
        }
        return true;
    }

    private void reloadFor(CommandSender sender) {
        ReloadResult result = reload.get();
        result.error().ifPresentOrElse(
                reason -> sender.sendMessage(miniMessage.deserialize(RELOAD_FAILED, Placeholder.unparsed("reason", reason))),
                () -> sender.sendMessage(miniMessage.deserialize(RELOADED)));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission(RELOAD_PERMISSION) && RELOAD.startsWith(args[0].toLowerCase())) {
            return List.of(RELOAD);
        }
        return List.of();
    }
}
