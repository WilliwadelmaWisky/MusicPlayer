package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.scene.control.Label;

/**
 *
 */
public class AudioClipNameLabel extends Label {

    /**
     *
     */
    public AudioClipNameLabel() {
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
        updateView(args.AudioClip.getName());
    }

    /**
     * @param name
     */
    private void updateView(final String name) {
        setText(name);
    }
}
