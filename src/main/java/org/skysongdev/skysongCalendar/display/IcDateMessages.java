package org.skysongdev.skysongCalendar.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.skysongdev.skysongCalendar.calendar.IcDate;

import java.util.List;

/** The chat lines that tell a player the IC date and season. */
public final class IcDateMessages {
    private static final String DATE_LINE = "<yellow>Today is <weekday>, <day> of <period>, <year> AB.";
    private static final String SEASON_LINE = "<yellow>The season is <season>";

    private final MiniMessage miniMessage;
    private final ChatPalette palette;

    public IcDateMessages(MiniMessage miniMessage, ChatPalette palette) {
        this.miniMessage = miniMessage;
        this.palette = palette;
    }

    public List<Component> describe(IcDate date) {
        Component dateLine = miniMessage.deserialize(DATE_LINE,
                Placeholder.parsed("weekday", palette.weekday(date.weekday())),
                Placeholder.unparsed("day", String.valueOf(date.day())),
                Placeholder.parsed("period", palette.period(date.period())),
                Placeholder.unparsed("year", String.valueOf(date.year())));
        Component seasonLine = miniMessage.deserialize(SEASON_LINE,
                Placeholder.parsed("season", palette.season(date.season())));
        return List.of(dateLine, seasonLine);
    }
}
