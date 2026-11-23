package org.skysongdev.skysongCalendar.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Entry point of the SkysongCalendar API, the supported way for other plugins to read the
 * IC calendar. Classes outside the {@code org.skysongdev.skysongCalendar.api} package are
 * internal and may change without notice.
 *
 * <p>SkysongCalendar registers this API with Bukkit's {@link org.bukkit.plugin.ServicesManager}
 * in its {@code onEnable} and removes it in {@code onDisable}. Plugins that use it add
 * {@code softdepend: [SkysongCalendar]} (or {@code depend}) to their plugin.yml, so they
 * enable after SkysongCalendar, and get it with {@link #get()}:
 *
 * <pre>{@code
 * IcDate today = SkysongCalendarApi.get().calendar().today();
 * }</pre>
 *
 * <p>SkysongCalendar implements this interface; other plugins must not.
 */
@ApiStatus.NonExtendable
public interface SkysongCalendarApi {

    /**
     * The API registered by SkysongCalendar.
     *
     * @return the API
     * @throws IllegalStateException when SkysongCalendar is not installed or not enabled yet
     */
    static @NotNull SkysongCalendarApi get() {
        RegisteredServiceProvider<SkysongCalendarApi> registration =
                Bukkit.getServicesManager().getRegistration(SkysongCalendarApi.class);
        if (registration == null) {
            throw new IllegalStateException("The SkysongCalendar API is not available: SkysongCalendar is not enabled."
                    + " Add 'softdepend: [SkysongCalendar]' to your plugin.yml and check that SkysongCalendar is installed.");
        }
        return registration.getProvider();
    }

    /**
     * The version of the API that the installed SkysongCalendar implements, as
     * {@code major.minor.patch}. A plugin can compare it with the version it was built
     * against and log a clear message instead of failing with {@link NoSuchMethodError}.
     * Adding methods raises the minor version; removing or changing one raises the major.
     *
     * @return the API version, for example {@code "1.0.0"}
     */
    @NotNull String apiVersion();

    /** The IC calendar: dates, times, conversions, festivals and seasons. */
    @NotNull IcCalendar calendar();

    /** How the calendar looks in chat: season colours and the {@code /date} lines. */
    @NotNull CalendarStyle style();

    /** How SkysongCalendar drives Minecraft world time: its settings and the daylight maths. */
    @NotNull WorldTime worldTime();

    /**
     * Re-reads SkysongCalendar's config.yml and applies it, as {@code /calendar reload} does.
     * When the config is invalid nothing changes and the result says why.
     *
     * @throws IllegalStateException when called off the server's main thread
     */
    @NotNull ReloadResult reload();
}
