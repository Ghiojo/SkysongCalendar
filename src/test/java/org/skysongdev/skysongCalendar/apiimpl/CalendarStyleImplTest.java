package org.skysongdev.skysongCalendar.apiimpl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.CalendarStyle;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;
import org.skysongdev.skysongCalendar.api.Month;
import org.skysongdev.skysongCalendar.api.Season;
import org.skysongdev.skysongCalendar.api.Weekday;
import org.skysongdev.skysongCalendar.display.ChatPalette;
import org.skysongdev.skysongCalendar.display.IcDateMessages;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarStyleImplTest {
    private final CalendarStyle style = new CalendarStyleImpl(MiniMessage.miniMessage(), new ChatPalette(),
            new IcDateMessages(MiniMessage.miniMessage(), new ChatPalette()));

    private static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    @Test
    void givesEachSeasonItsCalendarColour() {
        assertEquals(TextColor.fromHexString("#7B935B"), style.seasonColour(Season.FLORESCIA));
        assertEquals(TextColor.fromHexString("#C3A03F"), style.seasonColour(Season.SU_UNTAH));
        assertEquals(TextColor.fromHexString("#B6654E"), style.seasonColour(Season.ALTRUE));
        assertEquals(TextColor.fromHexString("#6380A3"), style.seasonColour(Season.VINSE_ER));
    }

    @Test
    void colouredNamesKeepTheirText() {
        assertEquals("Su'untah", plain(style.season(Season.SU_UNTAH)));
        assertEquals("Ramphastos", plain(style.period(Month.RAMPHASTOS)));
        assertEquals("Ignisday", plain(style.weekday(Weekday.IGNISDAY)));
    }

    @Test
    void monthsTakeTheirSeasonsColourAndTheFestivalStaysPlain() {
        assertEquals("<#b6654e>Lontra", MiniMessage.miniMessage().serialize(style.period(Month.LONTRA)));
        assertEquals("Festival", MiniMessage.miniMessage().serialize(style.period(Festival.FESTIVAL)));
    }

    @Test
    void describesLikeTheDateCommand() {
        IcDate date = new IcDate(799, Month.IDALIA, 6);

        assertEquals(List.of("Today is Luxensday, 6 of Idalia, 799 AB.", "The season is Florescia"),
                style.describe(date).stream().map(CalendarStyleImplTest::plain).toList());
        assertEquals(3, style.describe(date, new IcTimeOfDay(9 * 3600)).size());
    }
}
