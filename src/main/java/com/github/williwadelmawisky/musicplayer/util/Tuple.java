package com.github.williwadelmawisky.musicplayer.util;

/**
 * @param <T1>
 * @param <T2>
 */
public class Tuple<T1, T2> {

    private final T1 _first;
    private final T2 _second;


    /**
     * @param first
     * @param second
     */
    public Tuple(final T1 first, final T2 second) {
        _first = first;
        _second = second;
    }


    /**
     * @return
     */
    public T1 first() { return _first; }

    /**
     * @return
     */
    public T2 second() { return _second; }
}
