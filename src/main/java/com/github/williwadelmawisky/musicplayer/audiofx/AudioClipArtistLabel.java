package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.scene.control.Label;

/**
 *
 */
public class AudioClipArtistLabel extends Label {

    /**
     *
     */
    public AudioClipArtistLabel() {
        super();
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        audioClipPlayer.OnAudioClipStarted.addListener(this::onAudioClipStarted);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipStarted(final Object sender, final AudioClipPlayer.OnAudioClipStartedEventArgs args) {
        updateView(args.AudioClip.getArtist());
    }

    /**
     * @param artist
     */
    private void updateView(final String artist) {
        setText(artist);
    }
}
