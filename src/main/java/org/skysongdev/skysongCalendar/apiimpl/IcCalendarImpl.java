package org.skysongdev.skysongCalendar.apiimpl;

import org.jetbrains.annotations.NotNull;
import org.skysongdev.skysongCalendar.api.IcCalendar;
import org.skysongdev.skysongCalendar.api.IcDate;
import org.skysongdev.skysongCalendar.api.IcMoment;
import org.skysongdev.skysongCalendar.calendar.IcDateConverter;
import org.skysongdev.skysongCalendar.calendar.IcMomentConverter;
import org.skysongdev.skysongCalendar.calendar.IrlCalibration;
import org.skysongdev.skysongCalendar.calendar.SeasonCount;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/** The {@link IcCalendar} that follows the IRL time on a {@link Clock}. */
public final class IcCalendarImpl implements IcCalendar {
    private final Clock irlClock;
    private final IrlCalibration calibration;
    private final IcDateConverter dates;
    private final IcMomentConverter moments;
    private final SeasonCount seasons;

    public IcCalendarImpl(Clock irlClock, IrlCalibration calibration) {
        this.irlClock = irlClock;
        this.calibration = calibration;
        this.dates = new IcDateConverter(calibration);
        this.moments = new IcMomentConverter(calibration);
        this.seasons = new SeasonCount(calibration.epochIcYear());
    }

    @Override
    public @NotNull IcDate today() {
        return dateAt(irlClock.instant());
    }

    @Override
    public @NotNull IcMoment now() {
        return momentAt(irlClock.instant());
    }

    @Override
    public @NotNull IcDate dateAt(@NotNull Instant irl) {
        return dates.toIcDate(inIrlZone(irl));
    }

    @Override
    public @NotNull IcMoment momentAt(@NotNull Instant irl) {
        return moments.toIcMoment(inIrlZone(irl));
    }

    @Override
    public @NotNull IcDate dateOfDay(long dayNumber) {
        return dates.dateOfDay(dayNumber);
    }

    @Override
    public @NotNull ZoneOffset irlZone() {
        return IrlCalibration.ZONE;
    }

    @Override
    public int icDaysPerIrlDay() {
        return IrlCalibration.IC_DAYS_PER_IRL_DAY;
    }

    @Override
    public int festivalDays(int icYear) {
        return calibration.layoutOf(icYear).festivalDays();
    }

    @Override
    public int yearLength(int icYear) {
        return calibration.layoutOf(icYear).length();
    }

    @Override
    public long seasonCount() {
        return seasons.of(today());
    }

    @Override
    public long seasonCountOf(@NotNull IcDate date) {
        return seasons.of(date);
    }

    private static LocalDateTime inIrlZone(Instant irl) {
        return LocalDateTime.ofInstant(irl, IrlCalibration.ZONE);
    }
}
