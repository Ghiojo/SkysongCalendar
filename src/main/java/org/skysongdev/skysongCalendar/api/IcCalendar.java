package org.skysongdev.skysongCalendar.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneOffset;

/**
 * The IC calendar: today's date, the IC time, conversions from IRL time, festivals and
 * seasons. Every method is safe from any thread.
 *
 * <p>The calendar follows the IRL clock in a fixed {@link #irlZone()} of UTC+1, and every IRL
 * day holds {@link #icDaysPerIrlDay()} IC days of 8 IRL hours, starting at 00:00, 08:00 and
 * 16:00 in that zone.
 */
@ApiStatus.NonExtendable
public interface IcCalendar {

    /** Today's IC date. */
    @NotNull IcDate today();

    /** The IC moment right now: the IC day number and the IC time of day. */
    @NotNull IcMoment now();

    /** The IC time of day right now. */
    default @NotNull IcTimeOfDay timeOfDay() {
        return now().timeOfDay();
    }

    /**
     * The IC date at an IRL instant.
     *
     * @param irl any instant; the calendar reads it in {@link #irlZone()} itself
     */
    @NotNull IcDate dateAt(@NotNull Instant irl);

    /**
     * The IC moment at an IRL instant.
     *
     * @param irl any instant; the calendar reads it in {@link #irlZone()} itself
     */
    @NotNull IcMoment momentAt(@NotNull Instant irl);

    /**
     * The IC date of an IC day number, as {@link IcMoment#dayNumber()} counts them.
     *
     * @param dayNumber IC days since the calendar's epoch, Celestine 1, 790 AB being 0
     */
    @NotNull IcDate dateOfDay(long dayNumber);

    /** The IRL time zone the calendar follows: UTC+1 all year, without daylight saving. */
    @NotNull ZoneOffset irlZone();

    /** How many IC days fit in one IRL day: 3. */
    int icDaysPerIrlDay();

    /**
     * How many festival days close an IC year: 3, 9 or 6 as the years of a cycle go, and 0
     * for the first year of a cycle whose IRL year has no Feb 29 to hold its festival.
     *
     * @param icYear the year, counted After the Blight (AB)
     */
    int festivalDays(int icYear);

    /**
     * How many days an IC year has, its months and festival together.
     *
     * @param icYear the year, counted After the Blight (AB)
     */
    int yearLength(int icYear);

    /**
     * How many season changes have happened by today, the count behind SkysongItems'
     * {@code season} timer unit. It goes up by 1 at every season change and never down.
     */
    long seasonCount();

    /** How many season changes have happened by an IC date, as {@link #seasonCount()} counts them. */
    long seasonCountOf(@NotNull IcDate date);
}
