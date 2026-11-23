package org.skysongdev.skysongCalendar.display;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.Festival;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;
import org.skysongdev.skysongCalendar.api.Month;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IcDateMessagesTest {
    private final IcDateMessages messages = new IcDateMessages(MiniMessage.miniMessage(), new ChatPalette());

    private static List<String> plain(List<Component> lines) {
        return lines.stream().map(PlainTextComponentSerializer.plainText()::serialize).toList();
    }

    @Test
    void describesTheDateAndSeason() {
        List<Component> lines = messages.describe(new IcDate(799, Month.IDALIA, 6));

        assertEquals(List.of("Today is Luxensday, 6 of Idalia, 799 AB.", "The season is Florescia"), plain(lines));
    }

    @Test
    void putsTheTimeBetweenTheDateAndTheSeason() {
        List<Component> lines = messages.describe(new IcDate(799, Month.IDALIA, 6), new IcTimeOfDay(9 * 3600 + 5 * 60 + 59));

        assertEquals(List.of("Today is Luxensday, 6 of Idalia, 799 AB.", "It is 09:05.", "The season is Florescia"),
                plain(lines));
    }

    @Test
    void describesFestivalDays() {
        List<Component> lines = messages.describe(new IcDate(790, Festival.FESTIVAL, 2));

        assertEquals(List.of("Today is Ignisday, 2 of Festival, 790 AB.", "The season is Vinse'er"), plain(lines));
    }

    @Test
    void coloursTheNamesAndKeepsTheRestYellow() {
        Component line = messages.describe(new IcDate(799, Month.IDALIA, 6)).get(0);

        assertEquals(NamedTextColor.YELLOW, line.color());
        assertTrue(MiniMessage.miniMessage().serialize(line).contains("<#7b935b>Idalia"));
    }
}
