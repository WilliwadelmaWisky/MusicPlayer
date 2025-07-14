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
        this.getChildren().add(_playbackPositionLabel);

        _slider = new Slider();
        HBox.setHgrow(_slider, Priority.ALWAYS);
        _slider.setMaxWidth(Double.POSITIVE_INFINITY);
        _slider.setMin(0);
        _slider.setMax(1);
        _slider.valueProperty().addListener(this::onValueChanged_Slider);
        this.getChildren().add(_slider);

        _totalDurationLabel = new Label();
        _totalDurationLabel.setAlignment(Pos.CENTER_RIGHT);
        _totalDurationLabel.setMinWidth(40);
        _totalDurationLabel.setMaxWidth(40);
        this.getChildren().add(_totalDurationLabel);
    }


    /**
     * @param audioClip
     */
    public void bindTo(final AudioClip audioClip) {
        _audioClip = audioClip;

        _playbackPositionLabel.setText(Durations.durationToString(Duration.ZERO));
        _totalDurationLabel.setText(Durations.durationToString(audioClip.getTotalDuration()));
        _audioClip.OnProgressChanged.addListener(this::onProgressChanged_AudioClip);
    }

    /**
     *
     */
    public void unbind() {
        if (_audioClip == null)
            return;

        _audioClip.OnProgressChanged.removeListener(this::onProgressChanged_AudioClip);
    }


    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged_AudioClip(final Object sender, final EventArgs_SingleValue<Progress> args) {
        _playbackPositionLabel.setText(Durations.durationToString(args.Value.PlaybackPosition));
        _slider.setValue(args.Value.NormalizedPlaybackPosition);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onValueChanged_Slider(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
        if (_audioClip == null)
            return;

        final double delta = Math.abs(oldValue.doubleValue() - newValue.doubleValue());
        if (delta > 0.05) {
            System.out.println("ProgressSlider Clicked");
            _audioClip.seek(newValue.doubleValue());
        }
    }
}
