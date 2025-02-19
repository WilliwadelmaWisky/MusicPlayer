package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.core.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 *
 */
public class AudioProgressView extends HBox {

    @FXML private Label _playbackPositionLabel;
    @FXML private Label _durationLabel;
    @FXML private ProgressBar _progressBar;

    private Duration _duration;

    /**
     *
     */
    public AudioProgressView() {
        super();
    }


    /**
     * @param duration
     */
    public void setDuration(final Duration duration) {
        _duration = duration;
        _durationLabel.setText(Timer.durationToString(duration));
    }

    /**
     * @param progress
     */
    public void setProgess(double progress) {
        progress = Math.clamp(progress, 0, 1);
        final Duration playbackPosition = _duration.multiply(progress);
        _playbackPositionLabel.setText(Timer.durationToString(playbackPosition));
        _progressBar.setProgress(progress);
    }
}
