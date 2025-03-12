package com.github.williwadelmawisky.musicplayer.utils;

import java.util.function.Predicate;

/**
 *
 */
public abstract class Arrays {

    /**
     * @param array
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> boolean containsFunc(final T[] array, final Predicate<? super T> predicate) {
        for (T elem : array) {
            if (predicate.test(elem))
                return true;
        }

        return false;
    }

    /**
     * @param array
     * @param match
     * @param <T>
     * @return
     */
    public static <T> boolean contains(final T[] array, final T match) {
        for (T elem : array) {
            if (elem.equals(match))
                return true;
        }

        return false;
    }
}
