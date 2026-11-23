/**
 * The public API of SkysongCalendar: the only classes other plugins should compile against.
 * Classes outside this package are internal and may change in any release.
 *
 * <p>Other plugins add the plugin as a compile-only dependency through JitPack,
 * {@code compileOnly 'com.github.Ghiojo:SkysongCalendar:<tag>'}, and
 * {@code softdepend: [SkysongCalendar]} to their plugin.yml.
 *
 * <p>The calendar's values ({@link org.skysongdev.skysongCalendar.api.IcDate},
 * {@link org.skysongdev.skysongCalendar.api.IcMoment} and the rest) are immutable and safe to
 * share between threads.
 */
package org.skysongdev.skysongCalendar.api;
