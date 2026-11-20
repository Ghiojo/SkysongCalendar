package org.skysongdev.skysongCalendar.placeholders;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.calendar.Festival;
import org.skysongdev.skysongCalendar.calendar.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcMoment;
import org.skysongdev.skysongCalendar.calendar.IcTimeOfDay;
import org.skysongdev.skysongCalendar.calendar.Month;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarPlaceholderValuesTest {
    private static final IcMoment FIVE_PAST_NINE = new IcMoment(0, new IcTimeOfDay(9 * 3600 + 5 * 60 + 30));

    private final CalendarPlaceholderValues values =
            new CalendarPlaceholderValues(() -> new IcDate(799, Month.IDALIA, 6), () -> FIVE_PAST_NINE);

    @Test
    void tellsTheTime() {
        assertEquals(Optional.of("09:05"), values.valueOf("time"));
        assertEquals(Optional.of("09"), values.valueOf("hour"));
        assertEquals(Optional.of("05"), values.valueOf("minute"));
    }

    @Test
    void tellsTheDateInPlainText() {
        assertEquals(Optional.of("Luxensday"), values.valueOf("weekday"));
        assertEquals(Optional.of("6"), values.valueOf("day"));
        assertEquals(Optional.of("Idalia"), values.valueOf("month"));
        assertEquals(Optional.of("Florescia"), values.valueOf("season"));
        assertEquals(Optional.of("799"), values.valueOf("year"));
    }

    @Test
    void namesTheFestivalAsTheMonth() {
        CalendarPlaceholderValues festival =
                new CalendarPlaceholderValues(() -> new IcDate(799, Festival.FESTIVAL, 2), () -> FIVE_PAST_NINE);

        assertEquals(Optional.of("Festival"), festival.valueOf("month"));
        assertEquals(Optional.of("Vinse'er"), festival.valueOf("season"));
    }

    @Test
    void ignoresCaseAndRejectsUnknownNames() {
        assertEquals(Optional.of("09:05"), values.valueOf("TIME"));
        assertEquals(Optional.empty(), values.valueOf("weather"));
    }
}
