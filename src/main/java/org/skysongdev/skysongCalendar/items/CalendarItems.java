package org.skysongdev.skysongCalendar.items;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;
import org.skysongdev.skysongCalendar.calendar.SeasonCount;
import org.skysongdev.skysongCalendar.display.IcDateMessages;
import org.skysongdev.skysongitems.api.SkysongItemsApi;

/**
 * Hooks calendar items and the season timer unit into SkysongItems. It is the only class
 * the plugin calls that touches SkysongItems' API, so servers without SkysongItems never
 * load that API.
 */
public final class CalendarItems {
    public static final String SKYSONG_ITEMS = "Skysong_Items";

    private CalendarItems() {
    }

    public static void register(Plugin plugin, IcClock clock, IcDateMessages messages) {
        PluginManager plugins = plugin.getServer().getPluginManager();
        plugins.registerEvents(new CalendarItemTag(plugin), plugin);
        SeasonCount seasons = new SeasonCount(IrlCalibration.SKYSONG.epochIcYear());
        plugins.registerEvents(new SeasonTimeUnit(plugin, clock, seasons), plugin);
        plugins.registerEvents(new CalendarItemListener(SkysongItemsApi.get().tags(), clock, messages), plugin);
    }
}
