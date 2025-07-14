package com.williwadelmawisky.musicplayer.audio;

/**
 *
 */
public interface Saveable {

    /**
     * @return
     */
    String serialize();

    /**
     * @param s
     * @return
     */
    boolean deserialize(String s);
}
