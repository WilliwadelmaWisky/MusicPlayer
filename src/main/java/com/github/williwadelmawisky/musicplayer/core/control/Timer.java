package com.github.williwadelmawisky.musicplayer.core.control;

import com.github.williwadelmawisky.musicplayer.util.Util;

import javafx.util.Duration;

/**
 *
 */
public class Timer {

    private static final int MINUTE_TO_SECOND = 60;

    private final Duration _duration;
    private Duration _playbackPosition;


    /**
     * @param duration
     */
    public Timer(final Duration duration) {
        _duration = duration;
        _playbackPosition = Duration.ZERO;
    }


    /**
     * @return
     */
    public double getProgress() { return _playbackPosition.toMillis() / _duration.toMillis(); }

    /**
     * @return
     */
    public Duration getPlaybackPosition() { return _playbackPosition; }

    /**
     * @return
     */
    public Duration getDuration() { return _duration; }


    /**
     * @param playbackPosition
     */
    public void setPlaybackPosition(final Duration playbackPosition) {
        final double clampedMS = Math.clamp(playbackPosition.toMillis(), 0, _duration.toMillis());
        _playbackPosition = Duration.millis(clampedMS);
    }


    /**
     * @param duration
     * @return
     */
    public static String durationToString(final Duration duration) {
        final int seconds = (int)(duration.toSeconds() - (int)duration.toMinutes() * MINUTE_TO_SECOND);
        return (int)duration.toMinutes() + ":" + Util.padLeft(String.valueOf(seconds), 2, '0');
    }
}
