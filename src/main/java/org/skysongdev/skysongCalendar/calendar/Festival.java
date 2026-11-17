package org.skysongdev.skysongCalendar.calendar;

/** The festival that closes every IC year, after Litoria. It belongs to the last season. */
public enum Festival implements CalendarPeriod {
    FESTIVAL;

    @Override
    public String displayName() {
        return "Festival";
    }

    @Override
    public Season season() {
        return Season.VINSE_ER;
    }
}
