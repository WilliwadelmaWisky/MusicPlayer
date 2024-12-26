package com.github.williwadelmawisky.musicplayer.core.serialization;

/**
 * @param <T>
 */
public interface Formatter<T> {

    /**
     * @param obj
     * @return
     */
    String serialize(final T obj);

    /**
     * @param s
     * @return
     */
    T deserialize(final String s);
}
