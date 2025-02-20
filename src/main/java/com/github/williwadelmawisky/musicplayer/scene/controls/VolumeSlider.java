package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.audio.VolumeProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 *
 */
public class VolumeSlider extends HBox {

    @FXML private Label _label;
    @FXML private Slider _slider;

    private VolumeProperty _volumeProperty;


    /**
     *
     */
    public VolumeSlider() {
        super();

        ResourceLoader.loadFxml("fxml/controls/VolumeSlider.fxml", this);
        _slider.valueProperty().addListener(this::onVolumeSliderChanged);
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
    private void updateView(final double volume) {
        _slider.setValue(volume);
        final int percentage = (int)(volume * 100);
        _label.setText(percentage + "%");
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
}
