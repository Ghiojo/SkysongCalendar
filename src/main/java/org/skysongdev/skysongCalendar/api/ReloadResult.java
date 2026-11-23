package org.skysongdev.skysongCalendar.api;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/** What came of re-reading config.yml: it was applied, or it was invalid and nothing changed. */
public final class ReloadResult {
    private static final ReloadResult SUCCESS = new ReloadResult(null);

    private final String error;

    private ReloadResult(String error) {
        this.error = error;
    }

    public static @NotNull ReloadResult success() {
        return SUCCESS;
    }

    /** @param error why the config couldn't be loaded */
    public static @NotNull ReloadResult failure(@NotNull String error) {
        return new ReloadResult(Objects.requireNonNull(error));
    }

    public boolean succeeded() {
        return error == null;
    }

    /** Why the config couldn't be loaded, or empty when it was applied. */
    public @NotNull Optional<String> error() {
        return Optional.ofNullable(error);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ReloadResult result && Objects.equals(error, result.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(error);
    }

    @Override
    public String toString() {
        return succeeded() ? "ReloadResult[success]" : "ReloadResult[failure: " + error + "]";
    }
}
