package org.skysongdev.skysongCalendar;

import org.skysongdev.skysongCalendar.api.ReloadResult;

import java.util.function.Supplier;

/** Re-reads config.yml and applies it, for {@code /calendar reload} and the API alike. */
public final class ConfigReload implements Supplier<ReloadResult> {
    private final Runnable reload;

    /** @param reload re-reads the config and applies it; throws if the config is invalid */
    public ConfigReload(Runnable reload) {
        this.reload = reload;
    }

    @Override
    public ReloadResult get() {
        try {
            reload.run();
            return ReloadResult.success();
        } catch (IllegalArgumentException e) {
            return ReloadResult.failure(e.getMessage());
        }
    }
}
