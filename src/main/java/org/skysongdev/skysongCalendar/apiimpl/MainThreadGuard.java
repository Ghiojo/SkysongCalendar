package org.skysongdev.skysongCalendar.apiimpl;

import java.util.function.BooleanSupplier;

/** Refuses API calls that touch the server from any thread but its main one. */
public final class MainThreadGuard {
    private final BooleanSupplier onMainThread;

    /** @param onMainThread tells whether the current thread is the server's main thread */
    public MainThreadGuard(BooleanSupplier onMainThread) {
        this.onMainThread = onMainThread;
    }

    public void require(String call) {
        if (!onMainThread.getAsBoolean()) {
            throw new IllegalStateException(call + " must be called on the server's main thread");
        }
    }
}
