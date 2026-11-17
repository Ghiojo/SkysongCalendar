package org.skysongdev.skysongCalendar.calendar;

/** The eight months of an Atmos year, in order. Every month has {@link #LENGTH} days. */
public enum Month implements CalendarPeriod {
    CELESTINE("Celestine", Season.FLORESCIA),
    IDALIA("Idalia", Season.FLORESCIA),
    DASYURUS("Dasyurus", Season.SU_UNTAH),
    RAMPHASTOS("Ramphastos", Season.SU_UNTAH),
    LONTRA("Lontra", Season.ALTRUE),
    SAIMIRI("Saimiri", Season.ALTRUE),
    RANA("Rana", Season.VINSE_ER),
    LITORIA("Litoria", Season.VINSE_ER);

    /** Days in every month: five weeks of nine days. */
    public static final int LENGTH = 45;

    private final String displayName;
    private final Season season;

    Month(String displayName, Season season) {
        this.displayName = displayName;
        this.season = season;
    }

    @Override
    public String displayName() {
        return displayName;
    }

    @Override
    public Season season() {
        return season;
    }
}
