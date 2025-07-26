package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.Progress;
import com.williwadelmawisky.musicplayer.util.Durations;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

/**
 *
 */
public class ProgressControlPanel extends HBox {

    private final Label _playbackPositionLabel;
    private final Label _totalDurationLabel;
    private final Slider _slider;
    private AudioClip _audioClip;


    /**
     *
     */
    public ProgressControlPanel() {
        super();

        _playbackPositionLabel = new Label();
        _playbackPositionLabel.setAlignment(Pos.CENTER_LEFT);
        _playbackPositionLabel.setMinWidth(40);
        _playbackPositionLabel.setMaxWidth(40);

        _slider = new Slider();
        _slider.setMin(0);
        _slider.setMax(1);
        HBox.setHgrow(_slider, Priority.ALWAYS);
        _slider.setMaxWidth(Double.POSITIVE_INFINITY);

        _totalDurationLabel = new Label();
        _totalDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        _totalDurationLabel.setMinWidth(40);
        _totalDurationLabel.setMaxWidth(40);

        updateView(0, Duration.UNKNOWN, Duration.UNKNOWN);
        setDisable(true);
        setSpacing(5);
        getChildren().addAll(_playbackPositionLabel, _slider, _totalDurationLabel);

        _slider.valueProperty().addListener(this::onValueChanged_Slider);
    }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        _audioClip = audioClip;

        updateView(0, Duration.ZERO, _audioClip.getTotalDuration());
        setDisable(false);

        _audioClip.OnProgressChanged.addListener(this::onProgressChanged_AudioClip);
    }

    /**
     *
     */
    public void clear() {
        updateView(0, Duration.UNKNOWN, Duration.UNKNOWN);
        setDisable(true);

        if (_audioClip == null)
            return;

        _audioClip.OnProgressChanged.removeListener(this::onProgressChanged_AudioClip);
        _audioClip = null;
    }


    /**
     * @param progress
     */
    private void updateView(final Progress progress) {
        updateView(progress.NormalizedPlaybackPosition, progress.PlaybackPosition, progress.TotalDuration);
    }

    /**
     * @param normalizedPlaybackPosition
     * @param playbackPosition
     * @param totalDuration
     */
    private void updateView(final double normalizedPlaybackPosition, final Duration playbackPosition, final Duration totalDuration) {
        updatePlaybackPositionLabel(playbackPosition);
        updateTotalDurationLabel(totalDuration);
        updateSlider(normalizedPlaybackPosition);
    }

    /**
     * @param playbackPosition
     */
    private void updatePlaybackPositionLabel(final Duration playbackPosition) {
        _playbackPositionLabel.setText(Durations.durationToString(playbackPosition));
    }

    /**
     * @param totalDuration
     */
    private void updateTotalDurationLabel(final Duration totalDuration) {
        _totalDurationLabel.setText(Durations.durationToString(totalDuration));
    }

    /**
     * @param normalizedPlaybackPosition
     */
    private void updateSlider(final double normalizedPlaybackPosition) {
        _slider.setValue(normalizedPlaybackPosition);
    }


    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged_AudioClip(final Object sender, final EventArgs_SingleValue<Progress> args) {
        if (Math.abs(_slider.getValue() - args.Value.NormalizedPlaybackPosition) <= 1e-6)
            return;

        updatePlaybackPositionLabel(args.Value.PlaybackPosition);
        updateSlider(args.Value.NormalizedPlaybackPosition);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onValueChanged_Slider(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
        if (_audioClip == null || _audioClip.getStatus() == AudioClip.Status.STOPPED)
            return;

        final double delta = Math.abs(oldValue.doubleValue() - newValue.doubleValue());
        final double SKIP_THRESHOLD = 0.05;
        if (delta > SKIP_THRESHOLD || _audioClip.getStatus() == AudioClip.Status.PAUSED) {
            _audioClip.seek(newValue.doubleValue());
            updatePlaybackPositionLabel(_audioClip.getTotalDuration().multiply(newValue.doubleValue()));
        }
    }
}
