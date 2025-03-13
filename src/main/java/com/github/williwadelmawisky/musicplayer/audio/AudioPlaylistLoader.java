package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class AudioPlaylistLoader {

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
        for (String line : contents.split("\r\n")) {
            final AudioFile audioFile = AudioFile.parse(line);
            if (audioFile == null)
                continue;

            audioFileList.add(audioFile);
        }

        final String name = Objects.requireNonNull(Files.getNameWithoutExtension(file)).replace('_', ' ');
        final AudioFile[] audioFiles = audioFileList.toArray(new AudioFile[0]);
        return new AudioPlaylist(name, audioFiles);
    }
}
