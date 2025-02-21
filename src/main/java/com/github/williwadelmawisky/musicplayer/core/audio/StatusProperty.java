package com.github.williwadelmawisky.musicplayer.core.audio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class StatusProperty {

    private final UpdateEvent _updateEvent;
    private boolean _value;

    /**
     *
     */
    public StatusProperty() {
        this(false);
    }

    /**
     * @param defaultValue
     */
    public StatusProperty(boolean defaultValue) {
        _value = defaultValue;
        _updateEvent = new UpdateEvent();
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
    public boolean getValue() { return _value; }

    /**
     * @param value
     */
    public void setValue(boolean value) {
        if (_value == value)
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
        public UpdateEvent(){
            _listenerList = new LinkedList<>();
        }

        /**
         * @param isPlaying
         */
        private void invoke(boolean isPlaying) {
            _listenerList.forEach(e -> e.invoke(isPlaying));
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
         * @param isPlaying
         */
        void invoke(boolean isPlaying);
    }
}
