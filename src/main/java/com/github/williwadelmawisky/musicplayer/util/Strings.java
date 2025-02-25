package com.github.williwadelmawisky.musicplayer.util;

import javafx.util.Duration;

public abstract class Strings {

    /**
     * Add specified extra characters to the start of the string until a certain length is achieved.
     * @param s String to modify
     * @param length Target length of the string
     * @param padChar A character to add
     * @return A new modified string
     */
    public static String padLeft(String s, int length, char padChar) {
        if (s.length() >= length)
            return s;

        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            stringBuilder.insert(0, padChar);
        }

        return stringBuilder.toString();
    }

    /**
     * Add specified extra characters to the end of the string until a certain length is achieved.
     * @param s String to modify
     * @param length Target length of the string
     * @param padChar A character to add
     * @return A new modified string
     */
    public static String padRight(String s, int length, char padChar) {
        if (s.length() >= length)
            return s;

        StringBuilder stringBuilder = new StringBuilder(s);
        for (int i = 0; i < length - s.length(); i++) {
            stringBuilder.append(padChar);
        }

        return stringBuilder.toString();
    }


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
