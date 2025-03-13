package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.utils.ObservableList;
import com.github.williwadelmawisky.musicplayer.utils.SelectionModel;

/**
 *
 */
public class AudioClipListPlayer extends AudioClipPlayer {

    private static final AudioClipSelectorType DEFAULT_AUDIO_CLIP_SELECTOR_TYPE = AudioClipSelectorType.ORDERED;

    private final ObservableList<AudioClip> _audioClipList;
    private final SelectionModel<AudioClip> _selectionModel;
    private AudioClipSelectorType _audioClipSelectorType;


    /**
     *
     */
    public AudioClipListPlayer() {
        super();

        _audioClipList = new ObservableList<>();
        _selectionModel = new SelectionModel<>(_audioClipList, SelectionModel.SelectionMode.SINGLE);
        _audioClipSelectorType = DEFAULT_AUDIO_CLIP_SELECTOR_TYPE;

        _audioClipList.OnSorted.addListener(this::onAudioClipListSorted);
        _selectionModel.OnSelected.addListener(this::onSelectionModelSelected);
        OnAudioClipFinished.addListener(this::onAudioClipFinished);
    }


    /**
     * @return
     */
    public ObservableList<AudioClip> getAudioClipList() { return _audioClipList; }

    /**
     * @return
     */
    public SelectionModel<AudioClip> getSelectionModel() { return _selectionModel; }

    /**
     * @return
     */
    public AudioClipSelectorType getAudioClipSelectorType() { return _audioClipSelectorType; }


    /**
     * @param audioClipSelectorType
     */
    public void setAudioClipSelectorType(final AudioClipSelectorType audioClipSelectorType) { _audioClipSelectorType = audioClipSelectorType; }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListSorted(final Object sender, ObservableList.OnSortedEventArgs args) {
        _selectionModel.clearAndSelect(0);
    }

    /**
     * @param sender
     * @param args
     */
    private void onSelectionModelSelected(final Object sender, SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        setAudioClip(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipFinished(final Object sender, OnAudioClipFinishedEventArgs args) {
        _audioClipSelectorType.getValue().next(_selectionModel);
    }
}
