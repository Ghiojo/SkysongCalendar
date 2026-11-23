package org.skysongdev.skysongCalendar.api;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReloadResultTest {

    @Test
    void aSuccessHasNoError() {
        assertTrue(ReloadResult.success().succeeded());
        assertEquals(Optional.empty(), ReloadResult.success().error());
    }

    @Test
    void aFailureSaysWhy() {
        ReloadResult failure = ReloadResult.failure("No month called \"Smarch\"");

        assertFalse(failure.succeeded());
        assertEquals(Optional.of("No month called \"Smarch\""), failure.error());
    }
}
