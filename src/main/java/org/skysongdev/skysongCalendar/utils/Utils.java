package org.skysongdev.skysongCalendar.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

import org.skysongdev.skysongCalendar.calendarfunctions.*;

public class Utils {
    public static final LocalDateTime START_DATE = LocalDateTime.parse("2023-11-01T00:00:00");
    public static ArrayList<Month> CELESTINA_CYCLE;

    public Utils() {
        CELESTINA_CYCLE = new ArrayList();

        for(int i = 0; i < 3; ++i) {
            CELESTINA_CYCLE.add(new BaseMonth("<#F3A8BC>Celestine</#F3A8BC>", "<#F3A8BC>Florescia</#F3A8BC>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#F3A8BC>Idalia</#F3A8BC>", "<#F3A8BC>Florescia</#F3A8BC>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#FFD700>Dasyurus</#FFD700>", "<#FFD700>Su'untah</#FFD700>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#FFD700>Ramphastos</#FFD700>", "<#FFD700>Su'untah</#FFD700>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#D45B12>Lontra</#D45B12>", "<#D45B12>Altrue</#D45B12>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#D45B12>Saimiri</#D45B12>", "<#D45B12>Altrue</#D45B12>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#B3DAF1>Rana</#B3DAF1>", "<#B3DAF1>Vinse'er</#B3DAF1>"));
            CELESTINA_CYCLE.add(new BaseMonth("<#B3DAF1>Litoria</#B3DAF1>", "<#B3DAF1>Vinse'er</#B3DAF1>"));
            if (i == 0) {
                CELESTINA_CYCLE.add(new FirstFestival());
            }

            if (i == 1) {
                CELESTINA_CYCLE.add(new SecondFestival());
            }

            if (i == 2) {
                CELESTINA_CYCLE.add(new ThirdFestival());
            }
        }

    }

    public int getDaysSinceStart() {
        return (int) ChronoUnit.MINUTES.between(START_DATE, LocalDateTime.now()) / 480;
    }

    public int getCurrentDay() {
        int daysAddition = 0;
        int i = 0;

        while(true) {
            for(Month m : CELESTINA_CYCLE) {
                daysAddition += m.getLength(i);
                if (daysAddition > this.getDaysSinceStart()) {
                    return m.getLength(i) + 1 - (daysAddition - this.getDaysSinceStart());
                }
            }
            ++i;
        }
    }

    public String getCurrentDayName() {
        switch ((this.getCurrentDay() - 1) % 9) {
            case 0 -> {
                return "<gradient:#6EB3D4:#993FD0>Manaesday</gradient>";
            }
            case 1 -> {
                return "<#F6672C>Ignisday</#F6672C>";
            }
            case 2 -> {
                return "<#4EBAE9>Aquansday</#4EBAE9>";
            }
            case 3 -> {
                return "<#6AC048>Terransday</#6AC048>";
            }
            case 4 -> {
                return "<#ECEF9A>Aersday</#ECEF9A>";
            }
            case 5 -> {
                return "<white>Luxensday</white>";
            }
            case 6 -> {
                return "<#2F2F2F>Tenesday</#2F2F2F>";
            }
            case 7 -> {
                return "<#CEBFBF>Ordosday</#CEBFBF>";
            }
            case 8 -> {
                return "<#795083>Perdisday</#795083>";
            }
            default -> {
                return "<red>ERROR</red>";
            }
        }
    }

    public String getCurrentMonth() {
        int daysAddition = 0;
        int i = 0;

        while(true) {
            for(Month m : CELESTINA_CYCLE) {
                daysAddition += m.getLength(i);
                if (daysAddition > this.getDaysSinceStart()) {
                    return m.getName();
                }
            }

            ++i;
        }
    }

    public String getCurrentSeason() {
        int daysAddition = 0;
        int i = 0;

        while(true) {
            for(Month m : CELESTINA_CYCLE) {
                daysAddition += m.getLength(i);
                if (daysAddition > this.getDaysSinceStart()) {
                    return m.getSeason();
                }
            }

            ++i;
        }
    }

    public int getCurrentYear() {
        int daysAddition = 0;
        int year = 790;
        int i = 0;

        while(true) {
            for(Month m : CELESTINA_CYCLE) {
                daysAddition += m.getLength(i);
                if (daysAddition > this.getDaysSinceStart()) {
                    return year;
                }

                if (Objects.equals(m.getName(), "Festival")) {
                    ++year;
                }
            }

            ++i;
        }
    }
}
