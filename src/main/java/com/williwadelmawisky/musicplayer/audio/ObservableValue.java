package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;

/**
 * @param <T>
 */
public class ObservableValue<T> {

    public final EventHandler<ChangeEventArgs<T>> OnChanged;

    private T _value;


    /**
     * @param defaultValue
     */
    public ObservableValue(final T defaultValue) {
        _value = defaultValue;
        OnChanged = new EventHandler<>();
    }

    /**
     *
     */
    public ObservableValue() {
        this(null);
    }


    /**
     * @return
     */
    public boolean hasValue() { return _value != null; }

    /**
     * @return
     */
    public T getValue() { return _value; }

    /**
     * @param value
     */
    public void setValue(final T value) {
        _value = value;
        OnChanged.invoke(this, new ChangeEventArgs<>(value));
    }


    /**
     * @param <T>
     */
    public static final class ChangeEventArgs<T> extends EventArgs {

        public final T Value;

        /**
         * @param value
         */
        public ChangeEventArgs(final T value) {
            Value = value;
        }
    }
}
