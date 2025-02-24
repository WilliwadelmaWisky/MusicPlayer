package com.github.williwadelmawisky.musicplayer.core.database.serialization;

import com.github.williwadelmawisky.musicplayer.core.database.data.Artist;

import java.util.UUID;

/**
 *
 */
public class ArtistFormatter implements Formatter<Artist> {

    /**
     * @param artist
     * @return
     */
    @Override
    public String serialize(final Artist artist) {
        String[] values = new String[] {
                artist.getID().toString(),
                artist.getName()
        };
        return String.join("|", values);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Artist deserialize(final String s) {
        String[] values = s.split("\\|");
        return new Artist(
                UUID.fromString(values[0]),
                values[1]
        );
    }
}
