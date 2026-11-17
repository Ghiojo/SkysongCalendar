package org.skysongdev.skysongCalendar.calendar;

/**
 * The shape of one IC year: the eight months, then a festival of {@code festivalDays} days.
 * A festival of 0 days is skipped, as the leap festival is in IRL years without a Feb 29.
 */
public record IcYearLayout(int festivalDays) {

    private static final int MONTH_DAYS = Month.values().length * Month.LENGTH;

    public IcYearLayout {
        if (festivalDays < 0) {
            throw new IllegalArgumentException("A festival can't last " + festivalDays + " days");
        }
    }

    public int length() {
        return MONTH_DAYS + festivalDays;
    }

    /**
     * The date of a day of this year.
     *
     * @param year      the year number to put on the date
     * @param dayOfYear the day within this year, counting from 0
     */
    public IcDate dateAt(int year, int dayOfYear) {
        if (dayOfYear < 0 || dayOfYear >= length()) {
            throw new IllegalArgumentException("Day " + dayOfYear + " is outside a year of " + length() + " days");
        }
        if (dayOfYear >= MONTH_DAYS) {
            return new IcDate(year, Festival.FESTIVAL, dayOfYear - MONTH_DAYS + 1);
        }
        Month month = Month.values()[dayOfYear / Month.LENGTH];
        return new IcDate(year, month, dayOfYear % Month.LENGTH + 1);
    }
}
