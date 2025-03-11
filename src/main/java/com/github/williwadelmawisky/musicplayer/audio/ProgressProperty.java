package com.github.williwadelmawisky.musicplayer.audio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ProgressProperty {

    private final UpdateEvent _updateEvent;
    private double _value;

    /**
     *
     */
    public ProgressProperty() {
        this(0);
    }

    /**
     * @param defaultValue
     */
    public ProgressProperty(final double defaultValue) {
        _updateEvent = new UpdateEvent();
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
     * @param value
     */
    public void setValue(double value) {
        setValue(value, ChangeEvent.Type.ANY);
    }

    /**
     * @param value
     * @param eventType
     */
    public void setValue(double value, final ChangeEvent.Type eventType) {
        value = Math.clamp(value, 0, 1);
        if (Math.abs(_value - value) <= 1e-6)
            return;

        _value = value;
        final ChangeEvent changeEvent = new ChangeEvent(_value, eventType);
        _updateEvent.invoke(changeEvent);
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
         * @param changeEvent
         */
        private void invoke(final ChangeEvent changeEvent) {
            _listenerList.forEach(e -> e.invoke(changeEvent));
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
    public static class ChangeEvent {

        /**
         *
         */
        public enum Type {
            ANY,
            IGNORED
        }

        public final double Progress;
        public final Type EventType;


        /**
         * @param progress
         * @param eventType
         */
        public ChangeEvent(final double progress, final Type eventType) {
            Progress = progress;
            EventType = eventType;
        }
    }

    /**
     *
     */
    @FunctionalInterface
    public interface OnUpdate {

        /**
         * @param changeEvent
         */
        void invoke(final ChangeEvent changeEvent);
    }
}
