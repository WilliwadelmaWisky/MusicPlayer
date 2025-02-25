package com.github.williwadelmawisky.musicplayer.util;

import java.util.List;

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
    public static <T> int indexFunc(final List<T> list, final Predicate<T> match) {
        for (int i = 0; i < list.size(); i++) {
            if (match.invoke(list.get(i))) {
                return i;
            }
        }

        return -1;
    }
}
