package com.github.williwadelmawisky.musicplayer.util;

import com.github.williwadelmawisky.musicplayer.util.event.Predicate;

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
        for (int i = 0; i < array.length; i++) {
            if (match.invoke(array[i]))
                return true;
        }

        return false;
    }
}
