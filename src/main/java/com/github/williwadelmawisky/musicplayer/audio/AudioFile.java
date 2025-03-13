package com.github.williwadelmawisky.musicplayer.audio;

import java.io.File;

/**
 *
 */
public class AudioFile {

    private final File _file;
    private String _name, _artist;
    private Language _language;
    private Genre _genre;


    /**
     * @param file
     */
    public AudioFile(final File file) {
        this(file, null, null, null, null);
    }

    /**
     * @param file
     * @param name
     * @param artist
     * @param genre
     */
    public AudioFile(final File file, final String name, final String artist, final Language language, final Genre genre) {
        _file = file;
        _name = name;
        _artist = artist;
        _language = language;
        _genre = genre;
    }
}
