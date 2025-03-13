package com.github.williwadelmawisky.musicplayer.audio;

import java.io.File;
import java.util.UUID;

/**
 *
 */
public class AudioClip {

    private final AudioFile _audioFile;


    /**
     * @param audioFile
     */
    public AudioClip(final AudioFile audioFile) {
        _audioFile = audioFile;
    }


    /**
     * @return
     */
    public String getID() { return _audioFile.getFile().getAbsolutePath(); }

    /**
     * @return
     */
    public File getFile() { return _audioFile.getFile(); }

    /**
     * @return
     */
    public String getName() { return _audioFile.getName(); }

    /**
     * @return
     */
    public String getArtist() { return _audioFile.getArtist(); }

    /**
     * @return
     */
    public Language getLangugage() { return _audioFile.getLanguage(); }

    /**
     * @return
     */
    public Genre getGenre() { return _audioFile.getGenre(); }


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
