package com.github.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T>
 */
@FunctionalInterface
public interface EventHandler<T> {

    /**
     * @param e
     */
    void invoke(final T e);
}
