package com.williwadelmawisky.musicplayer.util;

import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;

/**
 * @param <T>
 */
public class ObservableValue<T> {

    public final EventHandler<OnChangedEventArgs<T>> OnChanged;

    private T _value;


    /**
     *
     */
    public ObservableValue() {
        this(null);
    }

    /**
     * @param defaultValue
     */
    public ObservableValue(final T defaultValue) {
        _value = defaultValue;
        OnChanged = new EventHandler<>();
    }


    /**
     * @return
     */
    public T getValue() { return _value; }

    /**
     * @param value
     */
    public void setValue(final T value) {
        _value = value;
        OnChanged.invoke(this, new OnChangedEventArgs<>(value));
    }


    /**
     * @param <T>
     */
    public static final class OnChangedEventArgs<T> extends EventArgs {

        public final T Value;

        /**
         * @param value
         */
        public OnChangedEventArgs(final T value) {
            Value = value;
        }
    }
}
