package com.github.williwadelmawisky.musicplayer.util;

/**
 * @param <T>
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * @param arg
     * @return
     */
    boolean invoke(final T arg);
}
