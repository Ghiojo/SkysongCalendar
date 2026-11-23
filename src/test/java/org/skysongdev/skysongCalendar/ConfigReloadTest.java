package org.skysongdev.skysongCalendar;

import org.junit.jupiter.api.Test;
import org.skysongdev.skysongCalendar.api.ReloadResult;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigReloadTest {
    private final AtomicInteger announced = new AtomicInteger();

    @Test
    void reportsAndAnnouncesAnAppliedConfig() {
        assertEquals(ReloadResult.success(), new ConfigReload(() -> { }, announced::incrementAndGet).get());
        assertEquals(1, announced.get());
    }

    @Test
    void reportsAnInvalidConfigInsteadOfThrowingAndAnnouncesNothing() {
        ConfigReload reload = new ConfigReload(() -> {
            throw new IllegalArgumentException("bad hours");
        }, announced::incrementAndGet);

        assertEquals(ReloadResult.failure("bad hours"), reload.get());
        assertEquals(0, announced.get());
    }
}
