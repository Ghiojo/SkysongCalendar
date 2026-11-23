package org.skysongdev.skysongCalendar.api;

import java.util.Arrays;

/**
 * A day of a month that recurs every IC year, such as Ramphastos 1.
 *
 * @param month the month
 * @param day   the day of that month, from 1 to {@link Month#LENGTH}
 */
public record IcMonthDay(Month month, int day) {

    public IcMonthDay {
        if (day < 1 || day > Month.LENGTH) {
            throw new IllegalArgumentException(month.displayName() + " has no day " + day);
        }
    }

    /** Reads text like {@code "Ramphastos 1"}, ignoring case. */
    public static IcMonthDay parse(String text) {
        String[] parts = text.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Expected a month and a day, like \"Ramphastos 1\", got \"" + text + "\"");
        }
        Month month = Arrays.stream(Month.values())
                .filter(candidate -> candidate.displayName().equalsIgnoreCase(parts[0]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No month called \"" + parts[0] + "\""));
        try {
            return new IcMonthDay(month, Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\"" + parts[1] + "\" is not a day number");
        }
    }

    /** Days since Celestine 1, as {@link IcDate#dayOfYear()} counts them. */
    public int dayOfYear() {
        return new IcDate(0, month, day).dayOfYear();
    }

    @Override
    public String toString() {
        return month.displayName() + " " + day;
    }
}
