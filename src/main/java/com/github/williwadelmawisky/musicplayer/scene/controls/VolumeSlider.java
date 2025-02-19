package com.github.williwadelmawisky.musicplayer.scene.controls;

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


    /**
     *
     */
    public VolumeSlider() {
        super();
    }


    /**
     * @param volume
     */
    public void setVolume(double volume) {
        volume = Math.clamp(volume, 0, 1);
        _slider.setValue(volume);
        final int percentage = (int)(volume * 100);
        _label.setText(percentage + "%");
    }
}
