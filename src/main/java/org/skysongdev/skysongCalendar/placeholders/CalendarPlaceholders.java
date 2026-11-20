package org.skysongdev.skysongCalendar.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code %skysongcalendar_...%} PlaceholderAPI expansion. It is the only class that
 * touches PlaceholderAPI, so servers without it never load that API.
 */
public final class CalendarPlaceholders extends PlaceholderExpansion {
    public static final String PLACEHOLDER_API = "PlaceholderAPI";

    private final Plugin plugin;
    private final CalendarPlaceholderValues values;

    public CalendarPlaceholders(Plugin plugin, CalendarPlaceholderValues values) {
        this.plugin = plugin;
        this.values = values;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "skysongcalendar";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /** Stay registered across {@code /papi reload}; the plugin owns this expansion. */
    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(@Nullable OfflinePlayer player, @NotNull String params) {
        return values.valueOf(params).orElse(null);
    }
}
