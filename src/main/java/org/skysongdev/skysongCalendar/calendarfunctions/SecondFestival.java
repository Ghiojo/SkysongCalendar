package org.skysongdev.skysongCalendar.calendarfunctions;

public class SecondFestival implements Month{
    String name = "Festival";
    String season = "Vinse'er";

    public SecondFestival() {
    }

    public int getLength(int celestina) {
        return 9;
    }

    public String getName() {
        return this.name;
    }

    public String getSeason() {
        return this.season;
    }
}
