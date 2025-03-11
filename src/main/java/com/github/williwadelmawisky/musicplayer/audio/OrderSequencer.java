package com.github.williwadelmawisky.musicplayer.audio;

import java.util.List;

/**
 *
 */
public class OrderSequencer implements Sequencer<AudioClip> {

    private int _currentIndex;


    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip previous(final List<AudioClip> sequence) {
        if (sequence == null || sequence.isEmpty())
            return null;

        _currentIndex = (_currentIndex <= 0) ? sequence.size() - 1 : _currentIndex - 1;
        return sequence.get(_currentIndex);
    }

    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip next(final List<AudioClip> sequence) {
        if (sequence == null || sequence.isEmpty())
            return null;

        _currentIndex = (_currentIndex + 1) % sequence.size();
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
