package com.github.williwadelmawisky.musicplayer.audio;

import java.io.File;

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
    File getFile() { return _audioFile.getFile(); }

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
     * @param name
     */
    public void setName(final String name) { _audioFile.setName(name); }

    /**
     * @param artist
     */
    public void setArtist(final String artist) { _audioFile.setArtist(artist); }

    /**
     * @param genre
     */
    public void setGenre(final Genre genre) { _audioFile.setGenre(genre); }

    /**
     * @param language
     */
    public void setLanguage(final Language language) { _audioFile.setLanguage(language); }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof AudioClip))
            return false;

        return equalsID(((AudioClip)obj).getID());
    }

    /**
     * @param id
     * @return
     */
    public boolean equalsID(final String id) {
        return getID().equals(id);
    }
}
