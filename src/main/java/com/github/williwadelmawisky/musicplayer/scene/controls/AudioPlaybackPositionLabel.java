package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.utils.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioPlaybackPositionLabel extends Label {

    private ProgressProperty _progressProperty;
    private AudioClipProperty _audioClipProperty;


    /**
     *
     */
    public AudioPlaybackPositionLabel() {
        super();
        updateView(Duration.ZERO);
    }

    /**
     * @param progressProperty
     * @param audioClipProperty
     */
    public AudioPlaybackPositionLabel(final ProgressProperty progressProperty, final AudioClipProperty audioClipProperty) {
        this();
        setProgressAndAudioProperty(progressProperty, audioClipProperty);
    }


    /**
     * @param progressProperty
     * @param audioClipProperty
     */
    public void setProgressAndAudioProperty(final ProgressProperty progressProperty, final AudioClipProperty audioClipProperty) {
        if (_progressProperty != null) _progressProperty.getUpdateEvent().removeListener(this::onProgressChanged);

        _audioClipProperty = audioClipProperty;
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
        final Duration playbackPosition = _audioClipProperty.getDuration().multiply(progress);
        updateView(playbackPosition);
    }

    /**
     * @param playbackPosition
     */
    private void updateView(final Duration playbackPosition) {
        this.setText(Strings.durationToString(playbackPosition));
    }
}
