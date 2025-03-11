package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.ProgressProperty;
import javafx.geometry.Bounds;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.awt.*;

/**
 *
 */
public class AudioProgressBar extends ProgressBar {

    private ProgressProperty _progressProperty;


    /**
     *
     */
    public AudioProgressBar() {
        super();
        updateView(0);
        this.setOnMouseClicked(this::onClicked);
    }

    /**
     * @param progressProperty
     */
    public AudioProgressBar(final ProgressProperty progressProperty) {
        this();
        setProgressProperty(progressProperty);
    }


    /**
     * @param progressProperty
     */
    public void setProgressProperty(final ProgressProperty progressProperty) {
        if (_progressProperty != null) _progressProperty.getUpdateEvent().removeListener(this::onProgressChanged);

        _progressProperty = progressProperty;
        updateView(_progressProperty.getValue());
        _progressProperty.getUpdateEvent().addListener(this::onProgressChanged);
    }

    /**
     * @param changeEvent
     */
    private void onProgressChanged(final ProgressProperty.ChangeEvent changeEvent) {
        updateView(changeEvent.Progress);
    }

    /**
     * @param e
     */
    private void onClicked(MouseEvent e) {
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        Bounds progressBarBounds = this.localToScreen(this.getBoundsInLocal());
        double progress = (mouseX - progressBarBounds.getMinX()) / (progressBarBounds.getMaxX() - progressBarBounds.getMinX());
        _progressProperty.setValue(progress);
    }

    /**
     * @param progress
     */
    private void updateView(final double progress) {
        this.setProgress(progress);
    }
}
