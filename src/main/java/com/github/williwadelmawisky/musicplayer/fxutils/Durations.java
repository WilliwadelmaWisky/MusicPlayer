package com.github.williwadelmawisky.musicplayer.fxutils;

import com.github.williwadelmawisky.musicplayer.utils.Strings;
import javafx.util.Duration;

/**
 *
 */
public abstract class Durations {

    /**
     * @param duration
     * @return
     */
    public static String durationToString(final Duration duration) {
        final int MINUTE_TO_SECOND = 60;
        final int seconds = (int)(duration.toSeconds() - (int)duration.toMinutes() * MINUTE_TO_SECOND);
        return (int)duration.toMinutes() + ":" + Strings.padLeft(String.valueOf(seconds), 2, '0');
    }
}
