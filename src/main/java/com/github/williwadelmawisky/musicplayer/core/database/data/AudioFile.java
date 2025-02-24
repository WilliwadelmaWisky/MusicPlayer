package com.github.williwadelmawisky.musicplayer.core.database.data;

import java.util.UUID;

/**
 *
 */
public class AudioFile {

    private final UUID _id;
    private final String _path;


    /**
     * @param id
     * @param path
     */
    public AudioFile(final UUID id, final String path) {
        _id = id;
        _path = path;
    }


    /**
     * @return
     */
    public UUID getID() {
        return _id;
    }

    /**
     * @return
     */
    public String getPath() {
        return _path;
    }
}
