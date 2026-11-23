package org.skysongdev.skysongCalendar.api;

/** The nine days of an Atmos week. Every month and festival starts on {@link #MANAESDAY}. */
public enum Weekday {
    MANAESDAY("Manaesday"),
    IGNISDAY("Ignisday"),
    AQUANSDAY("Aquansday"),
    TERRANSDAY("Terransday"),
    AERSDAY("Aersday"),
    LUXENSDAY("Luxensday"),
    TENESDAY("Tenesday"),
    ORDOSDAY("Ordosday"),
    PERDISDAY("Perdisday");

    private final String displayName;

    Weekday(String displayName) {
        this.displayName = displayName;
    }

    /** The weekday of a day of a month or festival, counting from 1. */
    public static Weekday ofDay(int dayOfPeriod) {
        if (dayOfPeriod < 1) {
            throw new IllegalArgumentException("Days are counted from 1, got " + dayOfPeriod);
        }
        Weekday[] week = values();
        return week[(dayOfPeriod - 1) % week.length];
    }

    public String displayName() {
        return displayName;
    }
}
