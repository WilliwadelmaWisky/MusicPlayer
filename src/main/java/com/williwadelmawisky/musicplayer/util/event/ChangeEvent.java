package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T>
 */
public class ChangeEvent<T> extends Event {

    public final T Value;


    /**
     * @param value
     */
    public ChangeEvent(final T value) {
        Value = value;
    }
}
