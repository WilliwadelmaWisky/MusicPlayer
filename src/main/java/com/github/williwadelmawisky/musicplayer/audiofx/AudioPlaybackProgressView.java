package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.fxutils.Durations;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

/**
 *
 */
public class AudioPlaybackProgressView extends HBox {

    private final PlaybackLabel _playbackPositionLabel, _totalDurationLabel;
    private final AudioPlaybackProgressBar _progressBar;
    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioPlaybackProgressView() {
        super();

        _playbackPositionLabel = new PlaybackLabel(40);
        _playbackPositionLabel.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(_playbackPositionLabel);

        _progressBar = new AudioPlaybackProgressBar();
        HBox.setHgrow(_progressBar, Priority.ALWAYS);
        _progressBar.setMaxWidth(Double.POSITIVE_INFINITY);
        this.getChildren().add(_progressBar);

        _totalDurationLabel = new PlaybackLabel(40);
        _totalDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(_totalDurationLabel);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        audioClipPlayer.OnAudioClipStarted.addListener(this::onAudioClipStarted);
        audioClipPlayer.OnProgressUpdated.addListener(this::onProgressChanged);
        _progressBar.bindTo(audioClipPlayer);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipStarted(final Object sender, final AudioClipPlayer.OnAudioClipStartedEventArgs args) {
        _totalDurationLabel.setText(Durations.durationToString(args.TotalDuration));
    }

    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged(final Object sender, final AudioClipPlayer.OnProgressUpdatedEventArgs args) {
        _playbackPositionLabel.setText(Durations.durationToString(args.PlaybackPosition));
    }


    /**
     *
     */
    private static class PlaybackLabel extends Label {

        /**
         * @param width
         */
        public PlaybackLabel(final double width) {
            super();

            setText(Durations.durationToString(Duration.ZERO));
            setMinWidth(width);
            setMaxWidth(width);
        }
    }
}
