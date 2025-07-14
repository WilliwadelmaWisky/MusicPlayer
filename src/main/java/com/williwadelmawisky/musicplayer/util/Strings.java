package com.williwadelmawisky.musicplayer.util;

/**
 *
 */
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
        for (int i = 0; i < length - s.length(); i++)
            stringBuilder.insert(0, padChar);

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
        for (int i = 0; i < length - s.length(); i++)
            stringBuilder.append(padChar);

        return stringBuilder.toString();
    }
}
