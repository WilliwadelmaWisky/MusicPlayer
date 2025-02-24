package com.github.williwadelmawisky.musicplayer.core.database.serialization;

import com.github.williwadelmawisky.musicplayer.core.database.data.Genre;
import com.github.williwadelmawisky.musicplayer.core.database.data.Song;

import java.util.UUID;

/**
 *
 */
public class SongFormatter implements Formatter<Song> {

    /**
     * @param song
     * @return
     */
    @Override
    public String serialize(final Song song) {
        String[] values = new String[] {
                song.getID().toString(),
                song.getName(),
                song.getGenre().name(),
                song.getArtistID().toString()
        };
        return String.join("|", values);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Song deserialize(final String s) {
        String[] values = s.split("\\|");
        return new Song(
                UUID.fromString(values[0]),
                values[1],
                Genre.valueOf(values[2]),
                UUID.fromString(values[3])
        );
    }
}
