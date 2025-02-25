package com.github.williwadelmawisky.musicplayer.util;

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
