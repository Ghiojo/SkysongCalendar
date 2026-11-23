package org.skysongdev.skysongCalendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.ReloadResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigReloadTest {

    @Test
    void reportsAnAppliedConfig() {
        assertEquals(ReloadResult.success(), new ConfigReload(() -> { }).get());
    }

    @Test
    void reportsAnInvalidConfigInsteadOfThrowing() {
        ConfigReload reload = new ConfigReload(() -> {
            throw new IllegalArgumentException("bad hours");
        });

        assertEquals(ReloadResult.failure("bad hours"), reload.get());
    }
}
