package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.geometry.Bounds;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.awt.*;

/**
 *
 */
public class AudioPlaybackProgressBar extends ProgressBar {

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioPlaybackProgressBar() {
        super();

        this.setOnMouseClicked(this::onClicked);
        this.setProgress(0);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        audioClipPlayer.OnProgressUpdated.addListener(this::onProgressChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged(final Object sender, final AudioClipPlayer.OnProgressUpdatedEventArgs args) {
        setProgress(args.Progress);
    }


    /**
     * @param e
     */
    private void onClicked(final MouseEvent e) {
        if (_audioClipPlayer == null)
            return;

        final double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        final Bounds progressBarBounds = this.localToScreen(this.getBoundsInLocal());
        final double progress = (mouseX - progressBarBounds.getMinX()) / (progressBarBounds.getMaxX() - progressBarBounds.getMinX());
        _audioClipPlayer.rewind(progress);
    }
}
