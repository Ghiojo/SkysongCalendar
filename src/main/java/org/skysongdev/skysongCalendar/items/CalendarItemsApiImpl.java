package org.skysongdev.skysongCalendar.items;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skysongdev.skysongCalendar.api.CalendarItemsApi;
import org.skysongdev.skysongCalendar.apiimpl.MainThreadGuard;
import org.skysongdev.skysongitems.api.ItemTags;

/** The {@link CalendarItemsApi} over SkysongItems' tags. */
public final class CalendarItemsApiImpl implements CalendarItemsApi {
    private final ItemTags tags;
    private final MainThreadGuard mainThread;

    public CalendarItemsApiImpl(ItemTags tags, MainThreadGuard mainThread) {
        this.tags = tags;
        this.mainThread = mainThread;
    }

    @Override
    public @NotNull String tagId() {
        return CalendarItemTag.ID;
    }

    @Override
    public @NotNull String seasonTimeUnitId() {
        return SeasonTimeUnit.ID;
    }

    @Override
    public boolean isCalendar(@Nullable ItemStack item) {
        mainThread.require("CalendarItemsApi.isCalendar");
        return tags.getBool(item, CalendarItemTag.ID).orElse(false);
    }
}
