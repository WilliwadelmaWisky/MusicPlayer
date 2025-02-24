package com.github.williwadelmawisky.musicplayer.core.database.serialization;

import com.github.williwadelmawisky.musicplayer.core.database.data.AudioFile;

import java.util.UUID;

/**
 *
 */
public class AudioFileFormatter implements Formatter<AudioFile> {

    /**
     * @param audioFile
     * @return
     */
    @Override
    public String serialize(final AudioFile audioFile) {
        String[] values = new String[] {
                audioFile.getID().toString(),
                audioFile.getPath()
        };
        return String.join("|", values);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public AudioFile deserialize(final String s) {
        String[] values = s.split("\\|");
        return new AudioFile(
                UUID.fromString(values[0]),
                values[1]
        );
    }
}
