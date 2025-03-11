package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.VolumeProperty;
import javafx.scene.control.Label;

/**
 *
 */
public class VolumeLabel extends Label {

    private VolumeProperty _volumeProperty;


    /**
     *
     */
    public VolumeLabel() {
        super();
        updateView(0);
    }

    /**
     * @param volumeProperty
     */
    public VolumeLabel(final VolumeProperty volumeProperty) {
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
     * @param volume
     */
    private void updateView(final double volume) {
        final int percentage = (int)(volume * 100);
        this.setText(percentage + "%");
    }
}
