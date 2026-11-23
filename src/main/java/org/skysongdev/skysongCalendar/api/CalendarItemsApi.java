package org.skysongdev.skysongCalendar.api;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SkysongCalendar's SkysongItems hooks: calendar items, which show the IC date when
 * right-clicked, and the {@code season} timer unit. Only there while SkysongItems is enabled.
 *
 * <p>Turning an item into a calendar is SkysongItems' job: set the {@link #tagId()} bool tag
 * with SkysongItems' API or {@code /si tag set calendar true}.
 */
@ApiStatus.NonExtendable
public interface CalendarItemsApi {

    /** The SkysongItems bool tag that makes an item a calendar: {@code calendar}. */
    @NotNull String tagId();

    /** The SkysongItems timer unit that counts season changes: {@code season}. */
    @NotNull String seasonTimeUnitId();

    /**
     * Whether an item is a calendar.
     *
     * @param item the item, may be null or air
     * @throws IllegalStateException when called off the server's main thread
     */
    boolean isCalendar(@Nullable ItemStack item);
}
