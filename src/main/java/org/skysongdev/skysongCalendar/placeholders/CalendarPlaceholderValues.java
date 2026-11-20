package org.skysongdev.skysongCalendar.placeholders;

import org.skysongdev.skysongCalendar.calendar.IcClock;
import org.skysongdev.skysongCalendar.calendar.IcDate;
import org.skysongdev.skysongCalendar.calendar.IcTimeOfDay;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;

import java.util.Locale;
import java.util.Optional;

/**
 * The plain-text value of each {@code %skysongcalendar_<name>%} placeholder. Values are the
 * same for every player and world, and carry no colours so other plugins can style them.
 */
public final class CalendarPlaceholderValues {
    private final IcClock dates;
    private final IcTimeSource times;

    public CalendarPlaceholderValues(IcClock dates, IcTimeSource times) {
        this.dates = dates;
        this.times = times;
    }

    /** The value of a placeholder, or empty if there is no placeholder by that name. */
    public Optional<String> valueOf(String name) {
        return Optional.ofNullable(switch (name.toLowerCase(Locale.ROOT)) {
            case "time" -> twoDigits(time().hour()) + ":" + twoDigits(time().minute());
            case "hour" -> twoDigits(time().hour());
            case "minute" -> twoDigits(time().minute());
            case "weekday" -> date().weekday().displayName();
            case "day" -> String.valueOf(date().day());
            case "month" -> date().period().displayName();
            case "season" -> date().season().displayName();
            case "year" -> String.valueOf(date().year());
            default -> null;
        });
    }

    private IcDate date() {
        return dates.today();
    }

    private IcTimeOfDay time() {
        return times.now().timeOfDay();
    }

    private static String twoDigits(int value) {
        return String.format("%02d", value);
    }
}
