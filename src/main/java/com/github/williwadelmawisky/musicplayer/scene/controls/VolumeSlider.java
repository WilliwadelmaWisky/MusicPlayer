package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.VolumeProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;

/**
 *
 */
public class VolumeSlider extends Slider {

    private VolumeProperty _volumeProperty;


    /**
     *
     */
    public VolumeSlider() {
        super();

        this.setMax(1);
        updateView(0);
        this.valueProperty().addListener(this::onVolumeSliderChanged);
    }

    /**
     * @param volumeProperty
     */
    public VolumeSlider(final VolumeProperty volumeProperty) {
        this();
        setVolumeProperty(volumeProperty);
    }

    /**
     * @param volumeProperty
     */
    public void setVolumeProperty(final VolumeProperty volumeProperty) {
        if (_volumeProperty != null) _volumeProperty.getUpdateEvent().removeListener(this::onVolumeChanged);

        _volumeProperty = volumeProperty;
        updateView(_volumeProperty.getValue());
        _volumeProperty.getUpdateEvent().addListener(this::onVolumeChanged);
    }

    /**
     * @param volume
     */
    private void onVolumeChanged(final double volume) {
        updateView(volume);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onVolumeSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        _volumeProperty.setValue(newValue.doubleValue());
    }

    /**
     * @param volume
     */
    private void updateView(final double volume) {
        this.setValue(volume);
    }
}
