package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T1>
 * @param <T2>
 * @param <T3>
 */
public class EventArgs_TripleValue<T1, T2, T3> extends EventArgs {

    public final T1 Value1;
    public final T2 Value2;
    public final T3 Value3;


    /**
     * @param value1
     * @param value2
     * @param value3
     */
    public EventArgs_TripleValue(final T1 value1, final T2 value2, final T3 value3) {
        Value1 = value1;
        Value2 = value2;
        Value3 = value3;
    }
}
