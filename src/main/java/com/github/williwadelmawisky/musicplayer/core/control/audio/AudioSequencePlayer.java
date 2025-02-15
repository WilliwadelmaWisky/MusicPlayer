package com.github.williwadelmawisky.musicplayer.core.control.audio;

import com.github.williwadelmawisky.musicplayer.util.Lists;
import com.github.williwadelmawisky.musicplayer.util.event.EventHandler;

import java.util.*;

/**
 *
 */
public class AudioSequencePlayer implements Iterable<AudioClip> {

    private final List<AudioClip> _audioClipList;
    private final AudioClipPlayer _audioClipPlayer;
    private Sequencer<AudioClip> _sequencer;
    private EventHandler<AudioClip> _onSelected;


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
        final int currentIndex = _sequencer.getCurrentIndex();
        sequencer.setCurrentIndex(currentIndex);

        _sequencer = sequencer;
    }

    /**
     * @param onSelected
     */
    public void setOnSelected(EventHandler<AudioClip> onSelected) {
        _onSelected = onSelected;
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
        _onSelected.invoke(audioClip);
    }

    /**
     *
     */
    public void previous() {
        final AudioClip audioClip = _sequencer.previous(_audioClipList);
        _audioClipPlayer.setAudioClip(audioClip);
        _onSelected.invoke(audioClip);
    }

    /**
     * @param index
     */
    public void select(final int index) {
        final AudioClip audioClip = _audioClipList.get(index);
        _audioClipPlayer.setAudioClip(audioClip);
        _sequencer.setCurrentIndex(index);
        _onSelected.invoke(audioClip);
    }

    /**
     * @param id
     */
    public void select(final UUID id) {
        final int index = Lists.indexFunc(_audioClipList, audioClip -> audioClip.equalsID(id));
        select(index);
    }

    /**
     *
     */
    public void selectFirst() { select(0); }


    /**
     *
     */
    private void onAudioClipFinished() {
        next();
    }


    /**
     * @return
     */
    @Override
    public Iterator<AudioClip> iterator() {
        return _audioClipList.iterator();
    }
}
