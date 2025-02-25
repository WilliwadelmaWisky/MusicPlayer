package com.github.williwadelmawisky.musicplayer.core.database;

import java.util.UUID;

/**
 *
 */
public class ArtistData implements Serializable {

    private UUID _id;
    private String _name;


    /**
     *
     */
    ArtistData() {
        this(null, null);
    }

    /**
     * @param id
     * @param name
     */
    public ArtistData(final UUID id, final String name) {
        _id = id;
        _name = name;
    }


    /**
     * @param s
     * @return
     */
    public static ArtistData parse(final String s) {
        final ArtistData artistData = new ArtistData();
        artistData.deserialize(s);
        return artistData;
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
     * @param id
     */
    private void setID(final UUID id) { _id = id; }

    /**
     * @param name
     */
    public void setName(final String name) { _name = name; }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ArtistData))
            return false;

        return equalsID(((ArtistData)obj)._id);
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
                _name
        };
        return String.join("|", values);
    }

    /**
     * @param s
     */
    @Override
    public void deserialize(final String s) {
        final String[] values = s.split("\\|");
        setID(UUID.fromString(values[0]));
        setName(values[1]);
    }
}
