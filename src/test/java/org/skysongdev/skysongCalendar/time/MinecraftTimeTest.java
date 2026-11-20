package org.skysongdev.skysongCalendar.time;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.calendar.IcMoment;
import org.skysongdev.skysongCalendar.calendar.IcTimeOfDay;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinecraftTimeTest {
    private final MinecraftTime minecraftTime = new MinecraftTime();

    private long dayTimeAt(int icHour, int icMinute) {
        IcMoment moment = new IcMoment(100, new IcTimeOfDay(icHour * 3600 + icMinute * 60));
        return Math.floorMod(minecraftTime.fullTimeOf(moment), MinecraftTime.TICKS_PER_DAY);
    }

    @Test
    void icHoursLandOnTheMatchingMinecraftTicks() {
        assertEquals(18_000, dayTimeAt(0, 0));
        assertEquals(0, dayTimeAt(6, 0));
        assertEquals(6_000, dayTimeAt(12, 0));
        assertEquals(12_000, dayTimeAt(18, 0));
        assertEquals(19_200, dayTimeAt(1, 12));
    }

    @Test
    void oneIcDayIsOneMinecraftDay() {
        IcTimeOfDay noon = new IcTimeOfDay(12 * 3600);
        long today = minecraftTime.fullTimeOf(new IcMoment(100, noon));
        long tomorrow = minecraftTime.fullTimeOf(new IcMoment(101, noon));

        assertEquals(MinecraftTime.TICKS_PER_DAY, tomorrow - today);
    }

    @Test
    void theWorldsDayCountGoesUpAtIcSixInTheMorning() {
        long justBefore = minecraftTime.fullTimeOf(new IcMoment(100, new IcTimeOfDay(6 * 3600 - 1)));
        long sixAm = minecraftTime.fullTimeOf(new IcMoment(100, new IcTimeOfDay(6 * 3600)));

        assertEquals(99, justBefore / MinecraftTime.TICKS_PER_DAY);
        assertEquals(100, sixAm / MinecraftTime.TICKS_PER_DAY);
    }

    @Test
    void midnightFollowsTheLastSecondOfTheDayWithoutAJump() {
        long lastSecond = minecraftTime.fullTimeOf(new IcMoment(100, new IcTimeOfDay(IcTimeOfDay.SECONDS_PER_DAY - 3)));
        long midnight = minecraftTime.fullTimeOf(new IcMoment(101, new IcTimeOfDay(0)));

        assertEquals(1, midnight - lastSecond);
    }
}
