package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.util.Lists;
import com.github.williwadelmawisky.musicplayer.util.EventHandler;
import com.github.williwadelmawisky.utils.ObservableList;
import com.github.williwadelmawisky.utils.SelectionModel;

import java.util.*;

/**
 *
 */
public class AudioSequencePlayer extends AudioClipPlayer implements Iterable<AudioClip> {

    private final List<AudioClip> _audioClipList;
    private Selector<AudioClip> _selector;
    private EventHandler<AudioClip> _onSelected;


    private final ObservableList<AudioClip> a;
    private final SelectionModel<AudioClip> _selectionModel;


    /**
     * @param selector
     */
    public AudioSequencePlayer(final Selector<AudioClip> selector) {
        super();

        _selector = selector;
        _audioClipList = new ArrayList<>();
        set_onAudioClipFinished(this::onAudioClipFinished);

        a = new ObservableList<>();
        _selectionModel = new SelectionModel<>(a, SelectionModel.SelectionMode.SINGLE);
    }


    /**
     * @param selector
     */
    public void setSequencer(final Selector<AudioClip> selector) {
        final int currentIndex = _selector.getCurrentIndex();
        selector.setCurrentIndex(currentIndex);
        _selector = selector;
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
        if (_audioClipList.isEmpty())
            return;

        Collections.shuffle(_audioClipList, new Random());
        selectFirst();
    }


    /**
     *
     */
    public void next() {
        final AudioClip audioClip = _selector.next(_audioClipList);
        setAudioClip(audioClip);
        _onSelected.invoke(audioClip);
    }

    /**
     *
     */
    public void previous() {
        final AudioClip audioClip = _selector.previous(_audioClipList);
        setAudioClip(audioClip);
        _onSelected.invoke(audioClip);
    }

    /**
     * @param index
     */
    public void select(final int index) {
        final AudioClip audioClip = _audioClipList.get(index);
        setAudioClip(audioClip);
        _selector.setCurrentIndex(index);
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
