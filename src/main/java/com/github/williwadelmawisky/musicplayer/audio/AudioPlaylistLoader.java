package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AudioPlaylistLoader {

    private final AudioFileLoader _audioFileLoader;


    /**
     *
     */
    public AudioPlaylistLoader() {

    }


    /**
     * @param file
     */
    public AudioPlaylist loadFromFile(final File file) {
        final String contents = Files.read(file);
        final List<AudioFile> audioFileList = new ArrayList<>();
        for (String line : contents.split("\n")) {
            final AudioFile audioFile = _audioFileLoader.loadFromString(line);
            if (audioFile == null)
                continue;

            audioFileList.add(audioFile);
        }

        final String name = Files.getNameWithoutExtension(file);
        final AudioFile[] audioFiles = audioFileList.toArray(new AudioFile[0]);
        return new AudioPlaylist(name, audioFiles);
    }
}
