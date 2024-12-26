package com.github.williwadelmawisky.musicplayer.core.control.audio;

import java.util.List;

/**
 * @param <T>
 */
public interface Sequencer<T> {

    /**
     * @param sequence
     * @return
     */
    T next(final List<T> sequence);

    /**
     * @param sequence
     * @return
     */
    T previous(final List<T> sequence);
}
