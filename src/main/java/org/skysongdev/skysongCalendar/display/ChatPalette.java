package org.skysongdev.skysongCalendar.display;

import org.skysongdev.skysongCalendar.calendar.CalendarPeriod;
import org.skysongdev.skysongCalendar.calendar.Month;
import org.skysongdev.skysongCalendar.calendar.Season;
import org.skysongdev.skysongCalendar.calendar.Weekday;

/** How calendar names are coloured in chat, as MiniMessage text. */
public final class ChatPalette {

    public String season(Season season) {
        return styled(seasonStyle(season), season.displayName());
    }

    /** A month takes its season's colour; a festival is left plain. */
    public String period(CalendarPeriod period) {
        if (period instanceof Month month) {
            return styled(seasonStyle(month.season()), month.displayName());
        }
        return period.displayName();
    }

    public String weekday(Weekday weekday) {
        return styled(weekdayStyle(weekday), weekday.displayName());
    }

    private static String seasonStyle(Season season) {
        return switch (season) {
            case FLORESCIA -> "#F3A8BC";
            case SU_UNTAH -> "#FFD700";
            case ALTRUE -> "#D45B12";
            case VINSE_ER -> "#B3DAF1";
        };
    }

    private static String weekdayStyle(Weekday weekday) {
        return switch (weekday) {
            case MANAESDAY -> "gradient:#6EB3D4:#993FD0";
            case IGNISDAY -> "#F6672C";
            case AQUANSDAY -> "#4EBAE9";
            case TERRANSDAY -> "#6AC048";
            case AERSDAY -> "#ECEF9A";
            case LUXENSDAY -> "white";
            case TENESDAY -> "#2F2F2F";
            case ORDOSDAY -> "#CEBFBF";
            case PERDISDAY -> "#795083";
        };
    }

    private static String styled(String style, String text) {
        String tagName = style.split(":", 2)[0];
        return "<" + style + ">" + text + "</" + tagName + ">";
    }
}
