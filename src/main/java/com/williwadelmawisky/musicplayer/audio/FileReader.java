package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.Files;

import java.io.File;
import java.util.function.Consumer;

/**
 *
 */
public class FileReader {

    private static final String[] ALLOWED_EXTENSIONS = new String[] { "mp3", "wav" };

    private final File _file;


    /**
     * @param file
     */
    public FileReader(final File file) {
        _file = file;
    }


    /**
     * @param action
     */
    public void read(final Consumer<AudioClip> action) {
        if (_file.isDirectory()) {
            readDirectory(_file, action);
            return;
        }

        if (Files.doesMatchExtension(_file, ALLOWED_EXTENSIONS))
            readFile(_file, action);
    }


    /**
     * @param directory
     * @param action
     */
    private void readDirectory(final File directory, final Consumer<AudioClip> action) {
        Files.listFiles(directory, ALLOWED_EXTENSIONS, true, file -> readFile(file, action));
    }

    /**
     * @param file
     * @param action
     */
    private void readFile(final File file, final Consumer<AudioClip> action) {
        final AudioClip audioClip = new AudioClip(file);
        action.accept(audioClip);
    }
}
