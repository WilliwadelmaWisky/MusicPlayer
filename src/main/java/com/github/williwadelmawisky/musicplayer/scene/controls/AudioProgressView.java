package com.github.williwadelmawisky.musicplayer.scene.controls;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class AudioProgressView extends HBox {

    private final AudioPlaybackPositionLabel _audioPlaybackPositionLabel;
    private final AudioDurationLabel _audioDurationLabel;
    private final AudioProgressBar _audioProgressBar;


    /**
     *
     */
    public AudioProgressView() {
        super();

        _audioPlaybackPositionLabel = new AudioPlaybackPositionLabel();
        _audioPlaybackPositionLabel.setAlignment(Pos.CENTER_LEFT);
        _audioPlaybackPositionLabel.setMinWidth(40);
        _audioPlaybackPositionLabel.setMaxWidth(40);
        this.getChildren().add(_audioPlaybackPositionLabel);

        _audioProgressBar = new AudioProgressBar();
        HBox.setHgrow(_audioProgressBar, Priority.ALWAYS);
        _audioProgressBar.setMaxWidth(Double.POSITIVE_INFINITY);
        this.getChildren().add(_audioProgressBar);

        _audioDurationLabel = new AudioDurationLabel();
        _audioDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        _audioDurationLabel.setMinWidth(40);
        _audioDurationLabel.setMaxWidth(40);
        this.getChildren().add(_audioDurationLabel);
    }

    /**
     * @param progressProperty
     * @param audioClipProperty
     */
    public AudioProgressView(final ProgressProperty progressProperty, final AudioClipProperty audioClipProperty) {
        this();
        setProgressAndAudioProperty(progressProperty, audioClipProperty);
    }


    /**
     * @param progressProperty
     * @param audioClipProperty
     */
    public void setProgressAndAudioProperty(final ProgressProperty progressProperty, final AudioClipProperty audioClipProperty) {
        _audioPlaybackPositionLabel.setProgressAndAudioProperty(progressProperty, audioClipProperty);
        _audioDurationLabel.setAudioProperty(audioClipProperty);
        _audioProgressBar.setProgressProperty(progressProperty);
    }
}
