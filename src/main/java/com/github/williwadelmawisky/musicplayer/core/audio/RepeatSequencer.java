package com.github.williwadelmawisky.musicplayer.core.audio;

import java.util.List;

/**
 *
 */
public class RepeatSequencer implements Sequencer<AudioClip> {

    private int _currentIndex;


    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip next(final List<AudioClip> sequence) {
        return sequence.get(_currentIndex);
    }

    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip previous(final List<AudioClip> sequence) {
        return sequence.get(_currentIndex);
    }


    /**
     * @return
     */
    @Override
    public int getCurrentIndex() {
        return _currentIndex;
    }

    /**
     * @param index
     */
    @Override
    public void setCurrentIndex(final int index) {
        _currentIndex = index;
    }
}
