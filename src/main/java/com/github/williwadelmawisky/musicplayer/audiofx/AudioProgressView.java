package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class AudioProgressView extends HBox {

    private final AudioPlaybackPositionLabel _audioPlaybackPositionLabel;
    private final AudioTotalDurationLabel _audioTotalDurationLabel;
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

        _audioTotalDurationLabel = new AudioTotalDurationLabel();
        _audioTotalDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        _audioTotalDurationLabel.setMinWidth(40);
        _audioTotalDurationLabel.setMaxWidth(40);
        this.getChildren().add(_audioTotalDurationLabel);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioPlaybackPositionLabel.bindTo(audioClipPlayer);
        _audioTotalDurationLabel.bindTo(audioClipPlayer);
        _audioProgressBar.bindTo(audioClipPlayer);
    }
}
