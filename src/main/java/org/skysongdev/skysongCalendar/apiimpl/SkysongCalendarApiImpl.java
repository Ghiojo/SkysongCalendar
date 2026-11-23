package org.skysongdev.skysongCalendar.apiimpl;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.CalendarStyle;
import org.skysongdev.skysongCalendar.api.IcCalendar;
import org.skysongdev.skysongCalendar.api.ReloadResult;
import org.skysongdev.skysongCalendar.api.SkysongCalendarApi;
import org.skysongdev.skysongCalendar.api.WorldTime;

import java.util.function.Supplier;

/** The API SkysongCalendar registers for other plugins. */
public final class SkysongCalendarApiImpl implements SkysongCalendarApi {
    /** Raise the minor version when adding methods, the major when removing or changing one. */
    public static final String API_VERSION = "1.0.0";

    private final IcCalendar calendar;
    private final CalendarStyle style;
    private final WorldTime worldTime;
    private final Supplier<ReloadResult> reload;
    private final MainThreadGuard mainThread;

    public SkysongCalendarApiImpl(IcCalendar calendar, CalendarStyle style, WorldTime worldTime,
                                  Supplier<ReloadResult> reload, MainThreadGuard mainThread) {
        this.calendar = calendar;
        this.style = style;
        this.worldTime = worldTime;
        this.reload = reload;
        this.mainThread = mainThread;
    }

    public void register(ServicesManager services, Plugin owner) {
        services.register(SkysongCalendarApi.class, this, owner, ServicePriority.Normal);
    }

    public void unregister(ServicesManager services) {
        services.unregister(SkysongCalendarApi.class, this);
    }

    @Override
    public @NotNull String apiVersion() {
        return API_VERSION;
    }

    @Override
    public @NotNull IcCalendar calendar() {
        return calendar;
    }

    @Override
    public @NotNull CalendarStyle style() {
        return style;
    }

    @Override
    public @NotNull WorldTime worldTime() {
        return worldTime;
    }

    @Override
    public @NotNull ReloadResult reload() {
        mainThread.require("SkysongCalendarApi.reload");
        return reload.get();
    }
}
