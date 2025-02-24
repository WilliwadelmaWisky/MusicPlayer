package com.github.williwadelmawisky.musicplayer.core.database.serialization;

import com.github.williwadelmawisky.musicplayer.core.database.data.Playlist;
import com.github.williwadelmawisky.musicplayer.util.Util;

import java.util.UUID;

/**
 *
 */
public class PlaylistFormatter implements Formatter<Playlist> {

    /**
     * @param playlist
     * @return
     */
    @Override
    public String serialize(final Playlist playlist) {
        Iterable<String> values = Util.map(playlist, UUID::toString);
        return playlist.getID().toString() + "|" + playlist.getName() + "|" + String.join("|", values);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Playlist deserialize(final String s) {
        String[] values = s.split("\\|");
        Playlist playlist = new Playlist(UUID.fromString(values[0]), values[1]);
        for (int i = 2; i < values.length; i++) {
            playlist.add(UUID.fromString(values[i]));
        }

        return playlist;
    }
}
