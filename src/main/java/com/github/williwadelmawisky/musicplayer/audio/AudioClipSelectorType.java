package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.OrderedSelector;
import com.github.williwadelmawisky.musicplayer.utils.RandomizedSelector;
import com.github.williwadelmawisky.musicplayer.utils.RepeatedSelector;
import com.github.williwadelmawisky.musicplayer.utils.Selector;

/**
 *
 */
public enum AudioClipSelectorType {
    ORDERED(new OrderedSelector<>()),
    RANDOMIZED(new RandomizedSelector<>()),
    REPEATED(new RepeatedSelector<>());

    private Selector<AudioClip> _value;

    /**
     * @param value
     */
    AudioClipSelectorType(final Selector<AudioClip> value) { _value = value; }

    /**
     * @return
     */
    public Selector<AudioClip> getValue() { return _value; }
}
