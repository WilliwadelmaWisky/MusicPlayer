package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.utils.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioDurationLabel extends Label {

    private AudioClipProperty _audioClipProperty;


    /**
     *
     */
    public AudioDurationLabel() {
        super();
        updateView(Duration.ZERO);
    }

    /**
     * @param audioClipProperty
     */
    public AudioDurationLabel(final AudioClipProperty audioClipProperty) {
        this();
        setAudioProperty(audioClipProperty);
    }


    /**
     * @param audioClipProperty
     */
    public void setAudioProperty(final AudioClipProperty audioClipProperty) {
        if (_audioClipProperty != null) _audioClipProperty.getUpdateEvent().removeListener(this::onAudioChanged);

        _audioClipProperty = audioClipProperty;
        updateView(_audioClipProperty.getDuration());
        _audioClipProperty.getUpdateEvent().addListener(this::onAudioChanged);
    }

    /**
     * @param changeEvent
     */
    private void onAudioChanged(final AudioClipProperty.ChangeEvent changeEvent) {
        updateView(changeEvent.Duration);
    }

    /**
     * @param duration
     */
    private void updateView(final Duration duration) {
        this.setText(Strings.durationToString(duration));
    }
}
