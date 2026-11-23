package org.skysongdev.skysongCalendar.apiimpl;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.api.IcTimeOfDay;
import org.skysongdev.skysongCalendar.api.TimeSyncSettings;
import org.skysongdev.skysongCalendar.api.WorldTime;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IcTimeSource;
import org.skysongdev.skysongCalendar.time.DaylightSchedule;
import org.skysongdev.skysongCalendar.time.DaylightSettings;
import org.skysongdev.skysongCalendar.time.MinecraftTime;
import org.skysongdev.skysongCalendar.time.SeasonalDaylight;
import org.skysongdev.skysongCalendar.time.TimeSettings;
import org.skysongdev.skysongCalendar.time.WorldTimeService;

import java.util.Set;

/** The read-only {@link WorldTime} view of the running {@link WorldTimeService}. */
public final class WorldTimeImpl implements WorldTime {
    /** What sync looks like when config.yml was invalid at start-up and it never started. */
    private static final TimeSyncSettings OFF = new TimeSyncSettings(false, 20, Set.of(),
            DaylightSettings.EVEN.minHours(), DaylightSettings.EVEN.maxHours(), DaylightSettings.EVEN.summerSolstice());

    private final WorldTimeService service;
    private final IcDateConverter dates;
    private final IcTimeSource icTime;
    private final MainThreadGuard mainThread;

    public WorldTimeImpl(WorldTimeService service, IcDateConverter dates, IcTimeSource icTime, MainThreadGuard mainThread) {
        this.service = service;
        this.dates = dates;
        this.icTime = icTime;
        this.mainThread = mainThread;
    }

    @Override
    public @NotNull TimeSyncSettings settings() {
        return service.settings().map(WorldTimeImpl::snapshot).orElse(OFF);
    }

    @Override
    public boolean isManaged(@NotNull World world) {
        mainThread.require("WorldTime.isManaged");
        return service.manages(world);
    }

    @Override
    public double daylightHours(@NotNull IcDate date) {
        return daylight().hoursOn(date);
    }

    @Override
    public @NotNull IcTimeOfDay sunrise(@NotNull IcDate date) {
        return new IcTimeOfDay(DaylightSchedule.sunrise(daylight().daylightSeconds(date)));
    }

    @Override
    public @NotNull IcTimeOfDay sunset(@NotNull IcDate date) {
        return new IcTimeOfDay(DaylightSchedule.sunset(daylight().daylightSeconds(date)));
    }

    @Override
    public long minecraftTimeAt(@NotNull IcMoment moment) {
        return new MinecraftTime(daylight()).fullTimeOf(moment);
    }

    @Override
    public long currentMinecraftTime() {
        return minecraftTimeAt(icTime.now());
    }

    private SeasonalDaylight daylight() {
        DaylightSettings settings = service.settings().map(TimeSettings::daylight).orElse(DaylightSettings.EVEN);
        return new SeasonalDaylight(dates, settings);
    }

    private static TimeSyncSettings snapshot(TimeSettings settings) {
        DaylightSettings daylight = settings.daylight();
        return new TimeSyncSettings(settings.enabled(), settings.updateIntervalTicks(), settings.blacklist().names(),
                daylight.minHours(), daylight.maxHours(), daylight.summerSolstice());
    }
}
