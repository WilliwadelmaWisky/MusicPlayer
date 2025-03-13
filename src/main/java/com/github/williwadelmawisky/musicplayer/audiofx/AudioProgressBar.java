package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.geometry.Bounds;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.awt.*;

/**
 *
 */
public class AudioProgressBar extends ProgressBar {

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioProgressBar() {
        super();

        updateView(0);
        this.setOnMouseClicked(this::onClicked);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        updateView(0);
        audioClipPlayer.OnProgressUpdated.addListener(this::onProgressChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged(final Object sender, final AudioClipPlayer.OnProgressUpdatedEventArgs args) {
        updateView(args.Progress);
    }

    /**
     * @param e
     */
    private void onClicked(final MouseEvent e) {
        final double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        final Bounds progressBarBounds = this.localToScreen(this.getBoundsInLocal());
        final double progress = (mouseX - progressBarBounds.getMinX()) / (progressBarBounds.getMaxX() - progressBarBounds.getMinX());
        _audioClipPlayer.rewind(progress);
    }

    /**
     * @param progress
     */
    private void updateView(final double progress) {
        setProgress(progress);
    }
}
