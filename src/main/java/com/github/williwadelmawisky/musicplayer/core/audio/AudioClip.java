package com.github.williwadelmawisky.musicplayer.core.audio;

import com.github.williwadelmawisky.musicplayer.core.data.AudioFile;
import com.github.williwadelmawisky.musicplayer.core.data.Genre;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import javafx.util.Duration;
import java.util.UUID;

/**
 *
 */
public class AudioClip {

    private final UUID _id, _artistID;
    private final String _name, _filePath;
    private final Genre _genre;
    private Duration _duration;


    /**
     * @param id
     * @param name
     * @param genre
     * @param filePath
     * @param artistID
     */
    public AudioClip(final UUID id, final String name, final Genre genre, final String filePath, final UUID artistID) {
        _id = id;
        _name = name;
        _genre = genre;
        _filePath = filePath;
        _artistID = artistID;
        _duration = Duration.ZERO;
    }

    /**
     * @param song
     * @param audioFile
     */
    public AudioClip(final Song song, final AudioFile audioFile) {
        this(song.getID(), song.getName(), song.getGenre(), audioFile.getPath(), song.getArtistID());
    }


    /**
     * @return
     */
    public UUID getID() { return _id; }

    /**
     * @return
     */
    public String getName() { return _name; }

    /**
     * @return
     */
    public Genre getGenre() { return _genre; }

    /**
     * @return
     */
    public Duration getDuration() { return _duration; }

    /**
     * @return
     */
    public UUID getArtistID() { return _artistID; }

    /**
     * @return
     */
    public String getFilePath() { return _filePath; }


    /**
     * @param duration
     */
    public void setDuration(final Duration duration) { _duration = duration; }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof AudioClip))
            return false;

        return equalsID(((AudioClip)obj)._id);
    }

    /**
     * @param id
     * @return
     */
    public boolean equalsID(final UUID id) {
        return _id.equals(id);
    }
}
