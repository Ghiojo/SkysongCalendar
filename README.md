# SkysongCalendar

Paper plugin for Skysong's IC calendar, based on Atmos' calendar: 1 IRL day is 3 IC days,
read in a fixed UTC+1. It provides `/date`, calendar items (with SkysongItems), PlaceholderAPI
placeholders and drives Minecraft world time from the IC clock.

## Using the API from another plugin

Add SkysongCalendar as a compile-only dependency through JitPack:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.Ghiojo:SkysongCalendar:<tag>'
}
```

Add `softdepend: [SkysongCalendar]` (or `depend`) to your `plugin.yml`, so your plugin enables
after SkysongCalendar, then get the API:

```java
SkysongCalendarApi calendar = SkysongCalendarApi.get();
IcDate today = calendar.calendar().today();
getLogger().info("Today is " + today.day() + " " + today.period().displayName() + ", " + today.year() + " AB");
```

Only the `org.skysongdev.skysongCalendar.api` package is supported. Everything else is internal
and may change in any release. `apiVersion()` tells you which API version the installed plugin
implements.
