package com.williwadelmawisky.musicplayer.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 */
public abstract class Arrays {

    /**
     * @param array
     * @param action
     * @param <T>
     */
    public static <T> void forEach(final T[] array, final Consumer<? super T> action) {
        for (T item : array)
            action.accept(item);
    }

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


    /**
     * @param array
     * @param match
     * @param <T>
     * @return
     */
    public static <T> int indexFunc(final T[] array, final Predicate<? super T> match) {
        for (int i = 0; i < array.length; i++) {
            if (match.test(array[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @param array
     * @param item
     * @param <T>
     * @return
     */
    public static <T> int indexof(final T[] array, final T item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }

        return -1;
    }
}
