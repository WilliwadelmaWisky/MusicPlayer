package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.util.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioDurationLabel extends Label {

    private AudioProperty _audioProperty;


    /**
     *
     */
    public AudioDurationLabel() {
        super();
        updateView(Duration.ZERO);
    }

    /**
     * @param audioProperty
     */
    public AudioDurationLabel(final AudioProperty audioProperty) {
        this();
        setAudioProperty(audioProperty);
    }


    /**
     * @param audioProperty
     */
    public void setAudioProperty(final AudioProperty audioProperty) {
        if (_audioProperty != null) _audioProperty.getUpdateEvent().removeListener(this::onAudioChanged);

        _audioProperty = audioProperty;
        updateView(_audioProperty.getDuration());
        _audioProperty.getUpdateEvent().addListener(this::onAudioChanged);
    }

    /**
     * @param changeEvent
     */
    private void onAudioChanged(final AudioProperty.ChangeEvent changeEvent) {
        updateView(changeEvent.Duration);
    }

    /**
     * @param duration
     */
    private void updateView(final Duration duration) {
        this.setText(Strings.durationToString(duration));
    }
}
