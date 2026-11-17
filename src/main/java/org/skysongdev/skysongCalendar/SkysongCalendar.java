package org.skysongdev.skysongCalendar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.skysongdev.skysongCalendar.commands.CalendarCommand;
import org.skysongdev.skysongCalendar.commands.DateCommand;
import org.skysongdev.skysongCalendar.utils.Utils;

public final class SkysongCalendar extends JavaPlugin {
    private Utils utils = new Utils();
    private MiniMessage mm = MiniMessage.miniMessage();

    public SkysongCalendar() {
    }

    public Utils getUtils() {
        return this.utils;
    }

    public MiniMessage getMiniMessage() {
        return this.mm;
    }

    public void onEnable() {
        this.getCommand("calendar").setExecutor(new CalendarCommand(this));
        this.getCommand("date").setExecutor(new DateCommand(this));
    }

    public void onDisable() {
    }
}
