package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.ObservableValue;

import java.io.File;

/**
 *
 */
public class AudioClip {

    private final File _file;
    private final ObservableValue<String> _name;
    private final ObservableValue<String> _artist;
    private final ObservableValue<Genre> _genre;
    private final ObservableValue<Language> _language;


    /**
     * @param audioFile
     */
    public AudioClip(final AudioFile audioFile) {
        this(audioFile.getFile(), audioFile.getName(), audioFile.getArtist(), audioFile.getGenre(), audioFile.getLanguage());
    }

    /**
     * @param file
     * @param name
     * @param artist
     * @param genre
     * @param language
     */
    public AudioClip(final File file, final String name, final String artist, final Genre genre, final Language language) {
        _file = file;
        _name = new ObservableValue<>(name);
        _artist = new ObservableValue<>(artist);
        _genre = new ObservableValue<>(genre);
        _language = new ObservableValue<>(language);
    }


    /**
     * @return
     */
    public String getID() { return _file.getAbsolutePath(); }

    /**
     * @return
     */
    File getFile() { return _file; }

    /**
     * @return
     */
    public ObservableValue<String> getName() { return _name; }

    /**
     * @return
     */
    public ObservableValue<String> getArtist() { return _artist; }

    /**
     * @return
     */
    public ObservableValue<Genre> getGenre() { return _genre; }

    /**
     * @return
     */
    public ObservableValue<Language> getLanguage() { return _language; }


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
