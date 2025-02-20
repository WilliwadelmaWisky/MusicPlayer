package com.github.williwadelmawisky.musicplayer.core.audio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public final class VolumeProperty {

    private final UpdateEvent _updateEvent;
    private double _value;


    /**
     *
     */
    public VolumeProperty() {
        this(1);
    }

    /**
     * @param defaultValue
     */
    public VolumeProperty(final double defaultValue) {
        _updateEvent = new UpdateEvent();
        _value = defaultValue;
    }

    /**
     * @return
     */
    public UpdateEvent getUpdateEvent() {
        return _updateEvent;
    }

    /**
     * @return
     */
    public double getValue() {
        return _value;
    }

    /**
     * @param value
     */
    public void setValue(double value) {
        value = Math.clamp(value, 0, 1);
        if (Math.abs(_value - value) <= 1e-6)
            return;

        _value = value;
        _updateEvent.invoke(_value);
    }


    /**
     *
     */
    public static final class UpdateEvent {

        private final List<OnUpdate> _listenerList;

        /**
         *
         */
        public UpdateEvent() {
            _listenerList = new LinkedList<>();
        }


        /**
         * @param volume
         */
        private void invoke(final double volume) {
            _listenerList.forEach(e -> e.invoke(volume));
        }


        /**
         * @param e
         */
        public void addListener(final OnUpdate e) { _listenerList.add(e); }

        /**
         * @param e
         */
        public void removeListener(final OnUpdate e) { _listenerList.remove(e); }
    }

    /**
     *
     */
    @FunctionalInterface
    public interface OnUpdate {

        /**
         * @param value
         */
        void invoke(final double value);
    }
}
