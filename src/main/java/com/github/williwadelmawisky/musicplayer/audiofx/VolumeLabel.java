package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.scene.control.Label;

/**
 *
 */
public class VolumeLabel extends Label {

    /**
     *
     */
    public VolumeLabel() {
        super();
        updateView(0);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
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
     * @param volume
     */
    private void updateView(final double volume) {
        final int percentage = (int)(volume * 100);
        this.setText(percentage + "%");
    }
}
