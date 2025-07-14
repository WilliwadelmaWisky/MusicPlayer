package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T1>
 * @param <T2>
 */
public class EventArgs_DoubleValue<T1, T2> extends EventArgs {

    public final T1 Value1;
    public final T2 Value2;

    /**
     * @param value1
     * @param value2
     */
    public EventArgs_DoubleValue(final T1 value1, final T2 value2) {
        Value1 = value1;
        Value2 = value2;
    }
}
