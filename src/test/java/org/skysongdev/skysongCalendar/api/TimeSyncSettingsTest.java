package org.skysongdev.skysongCalendar.api;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeSyncSettingsTest {

    private static TimeSyncSettings withSummerSolstice(Month month, int day) {
        return new TimeSyncSettings(true, 20, Set.of(), 10, 14, new IcMonthDay(month, day));
    }

    @Test
    void winterComesHalfAYearAfterSummer() {
        assertEquals(new IcMonthDay(Month.LITORIA, 1), withSummerSolstice(Month.RAMPHASTOS, 1).winterSolstice());
    }

    @Test
    void winterWrapsIntoTheNextYear() {
        assertEquals(new IcMonthDay(Month.CELESTINE, 1), withSummerSolstice(Month.LONTRA, 1).winterSolstice());
        assertEquals(new IcMonthDay(Month.IDALIA, 45), withSummerSolstice(Month.SAIMIRI, 45).winterSolstice());
    }

    @Test
    void theBlacklistCanNotBeChangedThroughTheSnapshot() {
        Set<String> names = new HashSet<>(Set.of("lobby"));
        TimeSyncSettings settings = new TimeSyncSettings(true, 20, names, 10, 14, new IcMonthDay(Month.RAMPHASTOS, 1));
        names.add("arena");

        assertEquals(Set.of("lobby"), settings.blacklistedWorlds());
        assertThrows(UnsupportedOperationException.class, () -> settings.blacklistedWorlds().add("arena"));
    }
}
