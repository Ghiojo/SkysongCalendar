package org.skysongdev.skysongCalendar.calendarfunctions;

public class BaseMonth implements Month {
    String name;
    String season;

    public BaseMonth(String name, String season) {
        this.name = name;
        this.season = season;
    }

    public int getLength(int celestina) {
        return 45;
    }

    public String getName() {
        return this.name;
    }

    public String getSeason() {
        return this.season;
    }
}
