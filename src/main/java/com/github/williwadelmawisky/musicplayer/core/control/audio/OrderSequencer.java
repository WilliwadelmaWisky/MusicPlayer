package com.github.williwadelmawisky.musicplayer.core.control.audio;

import java.util.List;

/**
 *
 */
public class OrderSequencer implements Sequencer<AudioClip> {

    private int _index;


    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip previous(final List<AudioClip> sequence) {
        if (sequence == null || sequence.isEmpty())
            return null;

        _index = (_index <= 0) ? sequence.size() - 1 : _index - 1;
        return sequence.get(_index);
    }

    /**
     * @param sequence
     * @return
     */
    @Override
    public AudioClip next(final List<AudioClip> sequence) {
        if (sequence == null || sequence.isEmpty())
            return null;

        _index = (_index + 1) % sequence.size();
        return sequence.get(_index);
    }
}
