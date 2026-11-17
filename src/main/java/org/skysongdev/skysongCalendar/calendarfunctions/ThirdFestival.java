package org.skysongdev.skysongCalendar.calendarfunctions;

public class ThirdFestival implements Month{
    String name = "Festival";
    String season = "Vinse'er";

    public ThirdFestival() {
    }

    public int getLength(int celestina) {
        return 6;
    }

    public String getName() {
        return this.name;
    }

    public String getSeason() {
        return this.season;
    }
}
