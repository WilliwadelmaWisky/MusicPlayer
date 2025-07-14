package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T>
 */
@FunctionalInterface
public interface Event<T extends EventArgs> {

    /**
     * @param sender
     * @param args
     */
    void invoke(final Object sender, final T args);
}
