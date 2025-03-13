package com.github.williwadelmawisky.musicplayer.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 */
public class AudioPlaylist {

    private final String _name;
    private final List<AudioFile> _audioFileList;


    /**
     * @param name
     * @param audioFiles
     */
    public AudioPlaylist(final String name, final AudioFile... audioFiles) {
        _name = name;
        _audioFileList = new ArrayList<>();
        _audioFileList.addAll(Arrays.asList(audioFiles));
    }


    /**
     * @return
     */
    public String getName() { return _name; }


    /**
     * @param action
     */
    public void forEach(final Consumer<? super AudioFile> action) { _audioFileList.forEach(action); }
}
