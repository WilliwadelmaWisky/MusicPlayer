package com.williwadelmawisky.musicplayer.audio;

import javafx.util.Duration;

/**
 *
 */
public class Progress {

    public final double NormalizedPlaybackPosition;
    public final Duration PlaybackPosition;
    public final Duration TotalDuration;


    /**
     * @param normalizedPlaybackPosition
     * @param playbackPosition
     * @param totalDuration
     */
    public Progress(final double normalizedPlaybackPosition, final Duration playbackPosition, final Duration totalDuration) {
        NormalizedPlaybackPosition = Math.clamp(normalizedPlaybackPosition, 0, 1);
        PlaybackPosition = playbackPosition;
        TotalDuration = totalDuration;
    }

    /**
     * @param normalizedPlaybackPosition
     * @param totalDuration
     */
    public Progress(final double normalizedPlaybackPosition, final Duration totalDuration) {
        this(normalizedPlaybackPosition, totalDuration.multiply(normalizedPlaybackPosition), totalDuration);
    }

    /**
     * @param playbackPosition
     * @param totalDuration
     */
    public Progress(final Duration playbackPosition, final Duration totalDuration) {
        this(playbackPosition.toMillis() / totalDuration.toMillis(), playbackPosition, totalDuration);
    }
}
