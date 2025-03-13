package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

/**
 *
 */
public class VolumeSlider extends Slider {

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public VolumeSlider() {
        super();

        setMax(1);
        updateView(0);
        valueProperty().addListener(this::onVolumeSliderChanged);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        updateView(audioClipPlayer.getVolume());
        audioClipPlayer.OnVolumeChanged.addListener(this::onVolumeChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onVolumeChanged(final Object sender, final AudioClipPlayer.OnVolumeChangedEventArgs args) {
        updateView(args.Volume);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onVolumeSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (_audioClipPlayer != null) _audioClipPlayer.setVolume(newValue.doubleValue());
    }

    /**
     * @param volume
     */
    private void updateView(final double volume) {
        setValue(volume);
    }
}
