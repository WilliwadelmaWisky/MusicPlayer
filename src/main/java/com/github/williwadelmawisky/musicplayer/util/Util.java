package com.github.williwadelmawisky.musicplayer.util;

import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Util {

    /**
     * @param in
     * @param proc
     * @param <TIn>
     * @param <TOut>
     * @return
     */
    public static <TIn, TOut> Iterable<TOut> map(final Iterable<TIn> in, final Callback<TIn, TOut> proc) {
        final List<TOut> out = new ArrayList<>();
        for (TIn t : in) {
            out.add(proc.call(t));
        }

        return out;
    }

    /**
     * @param in
     * @param match
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> filter(final Iterable<T> in, final Callback<T, Boolean> match) {
        final List<T> out = new ArrayList<>();
        for (T t : in) {
            if (match.call(t))
                out.add(t);
        }

        return out;
    }

    /**
     * @param in
     * @param match
     * @param <T>
     * @return
     */
    public static <T> T find(final Iterable<T> in, final Callback<T, Boolean> match) {
        for (T t : in) {
            if (match.call(t))
                return t;
        }

        return null;
    }


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
}
