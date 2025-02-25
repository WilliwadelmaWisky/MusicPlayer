package com.github.williwadelmawisky.musicplayer.util;

/**
 *
 */
public abstract class Arrays {

    /**
     * @param array
     * @param match
     * @param <T>
     * @return
     */
    public static <T> boolean containsFunc(final T[] array, final Predicate<T> match) {
        for (T elem : array) {
            if (match.invoke(elem))
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
