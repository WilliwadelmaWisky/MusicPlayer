package com.github.williwadelmawisky.musicplayer.audio;

import java.util.List;
import java.util.Random;

/**
 *
 */
public class RandomSequencer implements Sequencer<AudioClip> {

    private final Random _random;
    private int _currentIndex;


    /**
     *
     */
    public RandomSequencer() {
        _random = new Random();
    }


    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip next(final List<AudioClip> sequence) {
        int index = _random.nextInt(sequence.size());
        while (index == _currentIndex && sequence.size() > 1) {
            index = _random.nextInt(sequence.size());
        }

        _currentIndex = index;
        return sequence.get(_currentIndex);
    }

    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip previous(final List<AudioClip> sequence) {
        int index = _random.nextInt(sequence.size());
        while (index == _currentIndex && sequence.size() > 1) {
            index = _random.nextInt(sequence.size());
        }

        _currentIndex = index;
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
