package org.skysongdev.skysongCalendar.events;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.Month;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayWatcherTest {
    private static final IcDate FIRST = new IcDate(799, Month.IDALIA, 6);
    private static final IcDate SECOND = new IcDate(799, Month.IDALIA, 7);

    private static DayWatcher watching(IcDate... dates) {
        Deque<IcDate> clock = new ArrayDeque<>(List.of(dates));
        return new DayWatcher(clock::pop);
    }

    @Test
    void theDayItStartsOnIsNotAChange() {
        assertEquals(Optional.empty(), watching(FIRST, FIRST).check());
    }

    @Test
    void reportsEachNewDayOnce() {
        DayWatcher watcher = watching(FIRST, SECOND, SECOND);

        assertEquals(Optional.of(new DayChange(FIRST, SECOND)), watcher.check());
        assertEquals(Optional.empty(), watcher.check());
    }
}
