package com.github.williwadelmawisky.musicplayer.core.audio;

import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class AudioProperty {

    private final UpdateEvent _updateEvent;
    private Duration _duration;
    private AudioClip _audioClip;


    /**
     *
     */
    public AudioProperty() {
        this(null);
    }

    /**
     * @param audioClip
     */
    public AudioProperty(final AudioClip audioClip) {
        this(audioClip, Duration.ZERO);
    }

    /**
     * @param audioClip
     * @param duration
     */
    public AudioProperty(final AudioClip audioClip, final Duration duration) {
        _updateEvent = new UpdateEvent();
        _audioClip = audioClip;
        _duration = duration;
    }


    /**
     * @return
     */
    public UpdateEvent getUpdateEvent() { return _updateEvent; }

    /**
     * @return
     */
    public AudioClip getAudioClip() { return _audioClip; }

    /**
     * @return
     */
    public Duration getDuration() { return _duration; }


    /**
     * @param audioClip
     * @param duration
     */
    public void setValue(AudioClip audioClip, Duration duration) {
        _audioClip = audioClip;
        _duration = duration;
        final ChangeEvent changeEvent = new ChangeEvent(_audioClip, _duration);
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

        public final AudioClip AudioClip;
        public final Duration Duration;


        /**
         * @param audioClip
         * @param duration
         */
        public ChangeEvent(final AudioClip audioClip, final Duration duration) {
            AudioClip = audioClip;
            Duration = duration;
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
