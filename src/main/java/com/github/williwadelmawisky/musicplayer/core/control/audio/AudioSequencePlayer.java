package com.github.williwadelmawisky.musicplayer.core.control.audio;

import com.github.williwadelmawisky.musicplayer.util.Util;

import java.util.*;

/**
 *
 */
public class AudioSequencePlayer {

    private final List<AudioClip> _audioClipList;
    private final AudioClipPlayer _audioClipPlayer;
    private Sequencer<AudioClip> _sequencer;


    /**
     * @param sequencer
     */
    public AudioSequencePlayer(final Sequencer<AudioClip> sequencer) {
        _sequencer = sequencer;
        _audioClipPlayer = new AudioClipPlayer(this::onAudioClipFinished);
        _audioClipList = new ArrayList<>();
    }


    /**
     * @return
     */
    public AudioClipPlayer getAudioClipPlayer() {
        return _audioClipPlayer;
    }


    /**
     * @param sequencer
     */
    public void setSequencer(final Sequencer<AudioClip> sequencer) {
        _sequencer = sequencer;
    }


    /**
     * @param audioClip
     */
    public void add(final AudioClip audioClip) {
        _audioClipList.add(audioClip);
    }

    /**
     * @param audioClip
     */
    public void remove(final AudioClip audioClip) {
        _audioClipList.remove(audioClip);
    }

    /**
     *
     */
    public void clear() {
        _audioClipList.clear();
    }

    /**
     *
     */
    public void shuffle() {
        Collections.shuffle(_audioClipList, new Random());
    }


    /**
     *
     */
    public void next() {
        final AudioClip audioClip = _sequencer.next(_audioClipList);
        _audioClipPlayer.setAudioClip(audioClip);
    }

    /**
     *
     */
    public void previous() {
        final AudioClip audioClip = _sequencer.previous(_audioClipList);
        _audioClipPlayer.setAudioClip(audioClip);
    }

    /**
     * @param id
     */
    public void select(final UUID id) {
        final AudioClip audioClip = Util.find(_audioClipList, a -> a.equalsID(id));
        _audioClipPlayer.setAudioClip(audioClip);
    }


    /**
     *
     */
    private void onAudioClipFinished() {
        next();
    }
}
