package com.williwadelmawisky.musicplayer.util;

import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public abstract class Lists {

    /**
     * @param list
     * @param match
     * @param <T>
     * @return
     */
    public static <T> int indexFunc(final List<T> list, final Predicate<? super T> match) {
        for (int i = 0; i < list.size(); i++) {
            if (match.test(list.get(i))) {
                return i;
            }
        }

        return -1;
    }
}
