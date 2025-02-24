package com.github.williwadelmawisky.musicplayer.core.database;

import com.github.williwadelmawisky.musicplayer.core.database.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.database.data.AudioFile;
import com.github.williwadelmawisky.musicplayer.core.database.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.database.data.Song;
import com.github.williwadelmawisky.musicplayer.core.database.serialization.*;
import com.github.williwadelmawisky.musicplayer.util.Files;
import com.github.williwadelmawisky.musicplayer.util.Tuple;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class Database {

    public static final String HOME_PATH = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toString();

    private final Map<URL, Table> _tableMap;


    /**
     *
     */
    public Database() {
        _tableMap = new HashMap<>();
        for (URL url : URL.values()) {
            final Table table = new Table();
            final String filePath = Paths.get(HOME_PATH, url.name()).toString();
            final String content = Files.read(filePath);

            if (content != null) {
                for (String value : content.split("\n")) {
                    final Tuple<UUID, Object> tuple = getValue(url, value);
                    if (tuple == null)
                        continue;

                    table.write(tuple.first(), tuple.second());
                }
            }

            _tableMap.put(url, table);
        }
    }

    /**
     * @param url
     * @return
     */
    public Table getTable(final URL url) {
        return _tableMap.get(url);
    }


    private Tuple<UUID, Object> getValue(final URL url, final String s) {
        switch (url) {
            case SONG -> {
                final Formatter<Song> formatter = new SongFormatter();
                final Song song = formatter.deserialize(s);
                return new Tuple<>(song.getID(), song);
            }
            case ARTIST -> {
                final Formatter<Artist> formatter = new ArtistFormatter();
                final Artist artist = formatter.deserialize(s);
                return new Tuple<>(artist.getID(), artist);
            }
            case PLAYLIST -> {
                final Formatter<Playlist> formatter = new PlaylistFormatter();
                final Playlist playlist = formatter.deserialize(s);
                return new Tuple<>(playlist.getID(), playlist);
            }
            case AUDIO_FILE -> {
                final Formatter<AudioFile> formatter = new AudioFileFormatter();
                final AudioFile audioFile = formatter.deserialize(s);
                return new Tuple<>(audioFile.getID(), audioFile);
            }
        }

        return null;
    }
}
