package com.github.williwadelmawisky.musicplayer.database;

import java.util.UUID;

/**
 *
 */
public interface Serializable {

    /**
     * @return
     */
    UUID getID();

    /**
     * @return
     */
    String serialize();

    /**
     * @param s
     */
    void deserialize(final String s);
}
