package org.skysongdev.skysongCalendar.items;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.skysongdev.skysongCalendar.api.CalendarItemsApi;
import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.display.IcDateMessages;

/** Shows the IC date and season to a player who right-clicks a calendar item. */
public final class CalendarItemListener implements Listener {
    private final CalendarItemsApi items;
    private final IcClock clock;
    private final IcDateMessages messages;

    public CalendarItemListener(CalendarItemsApi items, IcClock clock, IcDateMessages messages) {
        this.items = items;
        this.clock = clock;
        this.messages = messages;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (!isRightClick(event.getAction()) || !isCalendar(event.getItem()) || isShownByMainHand(event)) {
            return;
        }
        Player player = event.getPlayer();
        messages.describe(clock.today()).forEach(player::sendMessage);
    }

    private static boolean isRightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean isCalendar(ItemStack item) {
        return items.isCalendar(item);
    }

    /** Bukkit fires once per hand; with a calendar in each, only the main hand answers. */
    private boolean isShownByMainHand(PlayerInteractEvent event) {
        return event.getHand() == EquipmentSlot.OFF_HAND
                && isCalendar(event.getPlayer().getInventory().getItemInMainHand());
    }
}
