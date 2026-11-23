package org.skysongdev.skysongCalendar.api;

/** The four seasons of Atmos. Each one lasts two months, about 30 days IRL. */
public enum Season {
    FLORESCIA("Florescia"),
    SU_UNTAH("Su'untah"),
    ALTRUE("Altrue"),
    VINSE_ER("Vinse'er");

    private final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
