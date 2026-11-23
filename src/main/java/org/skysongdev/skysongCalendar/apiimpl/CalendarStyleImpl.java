package org.skysongdev.skysongCalendar.apiimpl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.CalendarPeriod;
import org.skysongdev.skysongCalendar.api.CalendarStyle;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;
import org.skysongdev.skysongCalendar.api.Season;
import org.skysongdev.skysongCalendar.api.Weekday;
import org.skysongdev.skysongCalendar.display.ChatPalette;
import org.skysongdev.skysongCalendar.display.IcDateMessages;

import java.util.List;

/** The {@link CalendarStyle} the plugin's own chat messages use. */
public final class CalendarStyleImpl implements CalendarStyle {
    private final MiniMessage miniMessage;
    private final ChatPalette palette;
    private final IcDateMessages messages;

    public CalendarStyleImpl(MiniMessage miniMessage, ChatPalette palette, IcDateMessages messages) {
        this.miniMessage = miniMessage;
        this.palette = palette;
        this.messages = messages;
    }

    @Override
    public @NotNull TextColor seasonColour(@NotNull Season season) {
        return palette.seasonColour(season);
    }

    @Override
    public @NotNull Component season(@NotNull Season season) {
        return miniMessage.deserialize(palette.season(season));
    }

    @Override
    public @NotNull Component period(@NotNull CalendarPeriod period) {
        return miniMessage.deserialize(palette.period(period));
    }

    @Override
    public @NotNull Component weekday(@NotNull Weekday weekday) {
        return miniMessage.deserialize(palette.weekday(weekday));
    }

    @Override
    public @NotNull List<Component> describe(@NotNull IcDate date) {
        return messages.describe(date);
    }

    @Override
    public @NotNull List<Component> describe(@NotNull IcDate date, @NotNull IcTimeOfDay time) {
        return messages.describe(date, time);
    }
}
