package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.core.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.core.audio.ProgressProperty;
import com.github.williwadelmawisky.musicplayer.util.Strings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

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
     * @param audioProperty
     */
    public AudioProgressView(final ProgressProperty progressProperty, final AudioProperty audioProperty) {
        this();
        setProgressProperty(progressProperty, audioProperty);
    }


    /**
     * @param progressProperty
     * @param audioProperty
     */
    public void setProgressProperty(final ProgressProperty progressProperty, final AudioProperty audioProperty) {
        _audioPlaybackPositionLabel.setProgressProperty(progressProperty, audioProperty);
        _audioDurationLabel.setAudioProperty(audioProperty);
        _audioProgressBar.setProgressProperty(progressProperty);
    }
}
