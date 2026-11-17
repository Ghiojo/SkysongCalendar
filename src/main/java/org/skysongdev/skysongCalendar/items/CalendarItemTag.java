package org.skysongdev.skysongCalendar.items;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.skysongdev.skysongitems.api.TagType;
import org.skysongdev.skysongitems.api.event.SkysongItemsRegisterEvent;

/**
 * The SkysongItems tag that turns an item into a calendar: staff set it with
 * {@code /si tag set calendar true}, and right-clicking the item shows the IC date.
 */
public final class CalendarItemTag implements Listener {
    public static final String ID = "calendar";

    private final Plugin owner;

    public CalendarItemTag(Plugin owner) {
        this.owner = owner;
    }

    /** SkysongItems forgets plugin tags on every load, so this registers it each time. */
    @EventHandler
    public void onRegister(SkysongItemsRegisterEvent event) {
        if (!event.registerTag(owner, ID, TagType.BOOL)) {
            owner.getLogger().warning("SkysongItems refused the '" + ID + "' tag; calendar items won't work.");
        }
    }
}
