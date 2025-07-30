package com.williwadelmawisky.musicplayer.audio;

import java.util.Random;

/**
 *
 */
public class AudioClipSelector {

    private static final Mode DEFAULT_MODE = Mode.ORDERED;

    /**
     *
     */
    public enum Mode {
        ORDERED,
        RANDOMIZED,
        REPEATED
    }

    public final ObservableValue<Mode> ModeProperty;
    private final SelectionModel<AudioClip> _selectionModel;
    private final Random _random;


    /**
     * @param selectionModel
     */
    public AudioClipSelector(final SelectionModel<AudioClip> selectionModel) {
        this(selectionModel, DEFAULT_MODE);
    }

    /**
     * @param selectionModel
     * @param mode
     */
    public AudioClipSelector(final SelectionModel<AudioClip> selectionModel, final Mode mode) {
        _selectionModel = selectionModel;
        ModeProperty = new ObservableValue<>(mode);
        _random = new Random();
    }



    /**
     * @return
     */
    public void previous() {
        if (_selectionModel.isEmpty()) {
            _selectionModel.clear();
            return;
        }

        int index = 0;
        switch (ModeProperty.getValue()) {
            case ORDERED -> index = (_selectionModel.getSelectedIndex() <= 0) ? _selectionModel.size() - 1 : _selectionModel.getSelectedIndex() - 1;
            case RANDOMIZED -> {
                index = _random.nextInt(_selectionModel.size());
                while (index == _selectionModel.getSelectedIndex() && _selectionModel.size() > 1)
                    index = _random.nextInt(_selectionModel.size());
            }
            case REPEATED -> index = _selectionModel.getSelectedIndex();
        }

        _selectionModel.clearAndSelect(index);
    }

    /**
     * @return
     */
    public void next() {
        if (_selectionModel.isEmpty()) {
            _selectionModel.clear();
            return;
        }

        int index = 0;
        switch (ModeProperty.getValue()) {
            case ORDERED -> index = (_selectionModel.getSelectedIndex() + 1) % _selectionModel.size();
            case RANDOMIZED -> {
                index = _random.nextInt(_selectionModel.size());
                while (index == _selectionModel.getSelectedIndex() && _selectionModel.size() > 1)
                    index = _random.nextInt(_selectionModel.size());
            }
            case REPEATED -> index = _selectionModel.getSelectedIndex();
        }


        _selectionModel.clearAndSelect(index);
    }
}
