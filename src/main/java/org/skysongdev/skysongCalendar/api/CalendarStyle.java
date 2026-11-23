package org.skysongdev.skysongCalendar.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * How the calendar looks in chat: the season colours and the same coloured names and lines
 * that {@code /date} and calendar items show. Every method is safe from any thread.
 */
@ApiStatus.NonExtendable
public interface CalendarStyle {

    /**
     * The colour of a season: Florescia #7B935B, Su'untah #C3A03F, Altrue #B6654E and
     * Vinse'er #6380A3. A season's months use the same colour.
     */
    @NotNull TextColor seasonColour(@NotNull Season season);

    /** The season's name in its colour. */
    @NotNull Component season(@NotNull Season season);

    /** A month's name in its season's colour; the festival's name is left plain. */
    @NotNull Component period(@NotNull CalendarPeriod period);

    /** The weekday's name in its own colour. */
    @NotNull Component weekday(@NotNull Weekday weekday);

    /** The lines a calendar item shows: the date, then the season. */
    @NotNull List<Component> describe(@NotNull IcDate date);

    /** The lines {@code /date} shows: the date, the time of day, then the season. */
    @NotNull List<Component> describe(@NotNull IcDate date, @NotNull IcTimeOfDay time);
}
