package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T>
 */
public class EventArgs_SingleValue<T> extends EventArgs {

    public final T Value;


    /**
     * @param value
     */
    public EventArgs_SingleValue(final T value) {
        Value = value;
    }
}
