package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.Files;

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
        this(file, Files.getNameWithoutExtension(file), "", null, null);
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


    /**
     * @param s
     * @return
     */
    public static AudioFile parse(final String s) {
        final String[] props = s.split("\\|");
        final File file = new File(props[0].trim());
        if (!file.exists() || !file.isFile())
            return null;

        final String name = props[1].trim();
        final String artist = props[2].trim();
        final Language language = Language.valueOf(props[3].trim());
        final Genre genre = Genre.valueOf(props[4].trim());
        return new AudioFile(file, name, artist, language, genre);
    }


    /**
     * @return
     */
    public File getFile() { return _file; }

    /**
     * @return
     */
    public String getName() { return _name; }

    /**
     * @return
     */
    public String getArtist() { return _artist; }

    /**
     * @return
     */
    public Language getLanguage() { return _language; }

    /**
     * @return
     */
    public Genre getGenre() { return _genre; }


    /**
     * @param name
     */
    public void setName(final String name) { _name = name; }

    /**
     * @param artist
     */
    public void setArtist(final String artist) { _artist = artist; }

    /**
     * @param language
     */
    public void setLanguage(final Language language) { _language = language; }

    /**
     * @param genre
     */
    public void setGenre(final Genre genre) { _genre = genre; }
}
