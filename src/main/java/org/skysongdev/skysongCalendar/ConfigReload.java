package org.skysongdev.skysongCalendar;

import org.skysongdev.skysongCalendar.api.ReloadResult;

import java.util.function.Supplier;

/** Re-reads config.yml and applies it, for {@code /calendar reload} and the API alike. */
public final class ConfigReload implements Supplier<ReloadResult> {
    private final Runnable reload;
    private final Runnable afterReload;

    /**
     * @param reload      re-reads the config and applies it; throws if the config is invalid
     * @param afterReload runs once a reload has been applied, to announce it
     */
    public ConfigReload(Runnable reload, Runnable afterReload) {
        this.reload = reload;
        this.afterReload = afterReload;
    }

    @Override
    public ReloadResult get() {
        try {
            reload.run();
        } catch (IllegalArgumentException e) {
            return ReloadResult.failure(e.getMessage());
        }
        afterReload.run();
        return ReloadResult.success();
    }
}
