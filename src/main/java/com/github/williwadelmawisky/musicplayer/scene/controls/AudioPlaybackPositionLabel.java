package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.core.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.core.audio.ProgressProperty;
import com.github.williwadelmawisky.musicplayer.util.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioPlaybackPositionLabel extends Label {

    private ProgressProperty _progressProperty;
    private AudioProperty _audioProperty;


    /**
     *
     */
    public AudioPlaybackPositionLabel() {
        super();
        updateView(Duration.ZERO);
    }

    /**
     * @param progressProperty
     * @param audioProperty
     */
    public AudioPlaybackPositionLabel(final ProgressProperty progressProperty, final AudioProperty audioProperty) {
        this();
        setProgressProperty(progressProperty, audioProperty);
    }


    /**
     * @param progressProperty
     * @param audioProperty
     */
    public void setProgressProperty(final ProgressProperty progressProperty, final AudioProperty audioProperty) {
        if (_progressProperty != null) _progressProperty.getUpdateEvent().removeListener(this::onProgressChanged);

        _audioProperty = audioProperty;
        _progressProperty = progressProperty;
        updateView(_progressProperty.getValue());
        _progressProperty.getUpdateEvent().addListener(this::onProgressChanged);
    }

    /**
     * @param changeEvent
     */
    private void onProgressChanged(final ProgressProperty.ChangeEvent changeEvent) {
        updateView(changeEvent.Progress);
    }

    /**
     * @param progress
     */
    private void updateView(final double progress) {
        final Duration playbackPosition = _audioProperty.getDuration().multiply(progress);
        updateView(playbackPosition);
    }

    /**
     * @param playbackPosition
     */
    private void updateView(final Duration playbackPosition) {
        this.setText(Strings.durationToString(playbackPosition));
    }
}
