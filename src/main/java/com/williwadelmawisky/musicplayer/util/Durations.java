package com.williwadelmawisky.musicplayer.util;

import javafx.util.Duration;

import java.util.List;

/**
 *
 */
public abstract class Durations {

    /**
     * @param duration
     * @return
     */
    public static String durationToString(final Duration duration) {
        if (duration.isUnknown())
            return "-- : --";

        final int MINUTE_TO_SECOND = 60;
        final int seconds = (int)(duration.toSeconds() - (int)duration.toMinutes() * MINUTE_TO_SECOND);
        return (int)duration.toMinutes() + ":" + Strings.padLeft(String.valueOf(seconds), 2, '0');
    }


    /**
     * @param duration
     * @return
     */
    public static boolean isZero(final Duration duration) {
        return Math.abs(duration.toMillis()) <= 1e-6;
    }


    /**
     * @param durations
     * @return
     */
    public static Duration sum(final List<Duration> durations) {
        Duration totalDuration = Duration.ZERO;
        for (Duration duration : durations) {
            if (!duration.isUnknown())
                totalDuration = totalDuration.add(duration);
        }


        return totalDuration;
    }


    /**
     * @param duration
     * @param min
     * @param max
     * @return
     */
    public static Duration clamp(final Duration duration, final Duration min, final Duration max) {
        if (duration.lessThan(min)) return min;
        if (duration.greaterThan(max)) return max;
        return duration;
    }
}
