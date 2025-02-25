package com.github.williwadelmawisky.musicplayer.core.database;

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
