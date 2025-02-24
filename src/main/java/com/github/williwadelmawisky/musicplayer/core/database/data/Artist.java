package com.github.williwadelmawisky.musicplayer.core.database.data;

import java.util.UUID;

/**
 *
 */
public class Artist {

    private final UUID _id;
    private final String _name;


    /**
     * @param id
     * @param name
     */
    public Artist(final UUID id, final String name) {
        _id = id;
        _name = name;
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
    public String getName() {
        return _name;
    }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Artist))
            return false;

        return equalsID(((Artist)obj)._id);
    }

    /**
     * @param id
     * @return
     */
    public boolean equalsID(final UUID id) {
        return _id.equals(id);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return _name;
    }
}
