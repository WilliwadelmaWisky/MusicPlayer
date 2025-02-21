package com.github.williwadelmawisky.musicplayer.core.audio;

import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ProgressProperty {

    private final UpdateEvent _updateEvent;
    private Duration _duration;
    private double _value;

    /**
     *
     */
    public ProgressProperty() {
        this(Duration.ZERO, 0);
    }

    /**
     * @param duration
     */
    public ProgressProperty(final Duration duration) {
        this(duration, 0);
    }

    /**
     * @param duration
     * @param defaultValue
     */
    public ProgressProperty(final Duration duration, final double defaultValue) {
        _updateEvent = new UpdateEvent();
        _duration = duration;
        _value = Math.clamp(defaultValue, 0, 1);
    }


    /**
     * @return
     */
    public UpdateEvent getUpdateEvent() { return _updateEvent; }

    /**
     * @return
     */
    public double getValue() { return _value; }

    /**
     * @return
     */
    public Duration getDuration() { return _duration; }

    /**
     * @return
     */
    public Duration getPlaybackPosition() { return _duration.multiply(_value); }

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

    public void reset(final Duration duration) {

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
         * @param progress
         */
        private void invoke(final double progress) {
            _listenerList.forEach(e -> e.invoke(progress));
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
         * @param progress
         */
        void invoke(final double progress);
    }
}
