package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.Files;

import java.io.File;

/**
 *
 */
public class AudioClipLoader {

    private final AudioClipListPlayer _audioClipPlayer;


    /**
     * @param audioClipPlayer
     */
    public AudioClipLoader(final AudioClipListPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
    }


    /**
     * @param playlist
     */
    public void openPlaylist(final AudioPlaylist playlist) {
        _audioClipPlayer.getAudioClipList().clear();
        playlist.forEach(audioFile -> {
            final AudioClip audioClip = new AudioClip(audioFile);
            _audioClipPlayer.getAudioClipList().add(audioClip);
        });

        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param file
     */
    public void openUnknownFile(final File file) {
        if (file.isDirectory()) {
            openDirectory(file);
            return;
        }

        final String[] extensions = new String[] { ".mp3", ".wav" };
        if (Files.doesMatchExtension(file, extensions))
            openFile(file);
    }

    /**
     * @param file
     */
    public void openFile(final File file) {
        _audioClipPlayer.getAudioClipList().clear();
        addAudioFile(file);
        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param directory
     */
    public void openDirectory(final File directory) {
        _audioClipPlayer.getAudioClipList().clear();
        addDirectory(directory);
        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param file
     */
    public void addUnknownFile(final File file) {
        if (file.isDirectory()) {
            addDirectory(file);
            return;
        }

        final String[] extensions = new String[] { ".mp3", ".wav" };
        if (Files.doesMatchExtension(file, extensions))
            addAudioFile(file);
    }

    /**
     * @param file
     */
    public void addAudioFile(final File file) {
        final AudioFile audioFile = new AudioFile(file);
        final AudioClip audioClip = new AudioClip(audioFile);
        _audioClipPlayer.getAudioClipList().add(audioClip);
    }

    /**
     * @param directory
     */
    public void addDirectory(final File directory) {
        final String[] extensions = new String[] { ".mp3", ".wav" };
        Files.listFiles(directory, extensions, true, this::addAudioFile);
    }
}
