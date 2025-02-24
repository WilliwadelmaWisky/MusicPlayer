package com.github.williwadelmawisky.musicplayer.core.database.data;

import java.util.UUID;

/**
 *
 */
public class Song {

    private final UUID _id;
    private String _name;
    private Genre _genre;
    private UUID _artistID;


    /**
     * @param id
     * @param name
     * @param genre
     * @param artistID
     */
    public Song(final UUID id, final String name, final Genre genre, final UUID artistID) {
        _id = id;
        _name = name;
        _genre = genre;
        _artistID = artistID;
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
     * @return
     */
    public Genre getGenre() {
        return _genre;
    }

    /**
     * @return
     */
    public UUID getArtistID() {
        return _artistID;
    }


    /**
     * @param name
     */
    public void setName(final String name) { _name = name; }

    /**
     * @param genre
     */
    public void setGenre(final Genre genre) { _genre = genre; }

    /**
     * @param artistID
     */
    public void setArtistID(final UUID artistID) { _artistID = artistID; }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Song))
            return false;

        return equalsID(((Song)obj)._id);
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
