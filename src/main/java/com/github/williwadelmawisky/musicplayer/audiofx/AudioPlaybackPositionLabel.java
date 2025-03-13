package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.fxutils.Durations;
import com.github.williwadelmawisky.musicplayer.utils.Strings;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 *
 */
public class AudioPlaybackPositionLabel extends Label {

    /**
     *
     */
    public AudioPlaybackPositionLabel() {
        super();

        updateView(Duration.ZERO);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        updateView(Duration.ZERO);
        audioClipPlayer.OnProgressUpdated.addListener(this::onProgressChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged(final Object sender, final AudioClipPlayer.OnProgressUpdatedEventArgs args) {
        updateView(args.PlaybackPosition);
    }

    /**
     * @param playbackPosition
     */
    private void updateView(final Duration playbackPosition) {
        setText(Durations.durationToString(playbackPosition));
    }
}
