package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.fxutils.Durations;
import com.github.williwadelmawisky.musicplayer.utils.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioTotalDurationLabel extends Label {

    /**
     *
     */
    public AudioTotalDurationLabel() {
        super();
        updateView(Duration.ZERO);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        updateView(Duration.ZERO);
        audioClipPlayer.OnAudioClipStarted.addListener(this::onAudioClipStarted);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipStarted(final Object sender, final AudioClipPlayer.OnAudioClipStartedEventArgs args) {
        updateView(args.TotalDuration);
    }

    /**
     * @param duration
     */
    private void updateView(final Duration duration) {
        setText(Durations.durationToString(duration));
    }
}
