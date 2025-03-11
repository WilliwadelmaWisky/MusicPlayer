package com.github.williwadelmawisky.musicplayer.database;

import java.util.UUID;

/**
 *
 */
public class SongData implements Serializable {

    private UUID _id;
    private String _name;
    private Genre _genre;
    private UUID _artistID;


    /**
     *
     */
    SongData() {
        this(null, null, null, null);
    }

    /**
     * @param id
     * @param name
     * @param genre
     * @param artistID
     */
    public SongData(final UUID id, final String name, final Genre genre, final UUID artistID) {
        _id = id;
        _name = name;
        _genre = genre;
        _artistID = artistID;
    }


    /**
     * @param s
     * @return
     */
    public static SongData parse(final String s) {
        final SongData songData = new SongData();
        songData.deserialize(s);
        return songData;
    }


    /**
     * @return
     */
    @Override
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
     * @param id
     */
    private void setID(final UUID id) { _id = id; }

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
        if (!(obj instanceof SongData))
            return false;

        return equalsID(((SongData)obj)._id);
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


    /**
     * @return
     */
    @Override
    public String serialize() {
        final String[] values = new String[] {
                _id.toString(),
                _name,
                _genre.name(),
                _artistID.toString()
        };
        return String.join("|", values);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public void deserialize(final String s) {
        final String[] values = s.split("\\|");
        setID(UUID.fromString(values[0]));
        setName(values[1]);
        setGenre(Genre.valueOf(values[2]));
        setArtistID(UUID.fromString(values[3]));
    }
}
