package org.skysongdev.skysongCalendar.calendarfunctions;

public class FirstFestival implements Month {
    String name = "Festival";
    String season = "Vinse'er";

    public FirstFestival() {
    }

    public int getLength(int celestina) {
        return celestina % 4 == 0 ? 3 : 0;
    }

    public String getName() {
        return this.name;
    }

    public String getSeason() {
        return this.season;
    }
}
