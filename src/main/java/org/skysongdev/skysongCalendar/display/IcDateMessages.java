package org.skysongdev.skysongCalendar.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.skysongdev.skysongCalendar.calendar.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcTimeOfDay;

import java.util.List;

/** The chat lines that tell a player the IC date, time and season. */
public final class IcDateMessages {
    private static final String DATE_LINE = "<yellow>Today is <weekday>, <day> of <period>, <year> AB.";
    private static final String TIME_LINE = "<yellow>It is <time>.";
    private static final String SEASON_LINE = "<yellow>The season is <season>";

    private final MiniMessage miniMessage;
    private final ChatPalette palette;

    public IcDateMessages(MiniMessage miniMessage, ChatPalette palette) {
        this.miniMessage = miniMessage;
        this.palette = palette;
    }

    /** The date, the time of day and the season. */
    public List<Component> describe(IcDate date, IcTimeOfDay time) {
        List<Component> lines = describe(date);
        return List.of(lines.get(0), describeTime(time), lines.get(1));
    }

    /** The date and the season. */
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

    private Component describeTime(IcTimeOfDay time) {
        String clock = String.format("%02d:%02d", time.hour(), time.minute());
        return miniMessage.deserialize(TIME_LINE, Placeholder.unparsed("time", clock));
    }
}
