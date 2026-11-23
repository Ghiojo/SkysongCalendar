package org.skysongdev.skysongCalendar.apiimpl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainThreadGuardTest {

    @Test
    void letsMainThreadCallsThrough() {
        assertDoesNotThrow(() -> new MainThreadGuard(() -> true).require("reload"));
    }

    @Test
    void refusesOtherThreadsByName() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> new MainThreadGuard(() -> false).require("SkysongCalendarApi.reload"));

        assertEquals("SkysongCalendarApi.reload must be called on the server's main thread", e.getMessage());
    }
}
