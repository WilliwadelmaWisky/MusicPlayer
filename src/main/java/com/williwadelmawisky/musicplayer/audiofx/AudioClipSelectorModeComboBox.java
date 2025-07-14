package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClipSelector;
import com.williwadelmawisky.musicplayer.util.ObservableValue;
import com.williwadelmawisky.musicplayer.util.fx.EnumComboBox;

/**
 *
 */
public class AudioClipSelectorModeComboBox extends EnumComboBox<AudioClipSelector.Mode> {

    private AudioClipSelector _audioClipSelector;


    /**
     *
     */
    public AudioClipSelectorModeComboBox() {
        super(AudioClipSelector.Mode.class, AudioClipSelector.Mode.ORDERED);
        valueProperty().addListener(this::onValueChanged);
    }


    /**
     * @param audioClipSelector
     */
    public void bindTo(final AudioClipSelector audioClipSelector) {
        _audioClipSelector = audioClipSelector;

        _audioClipSelector.ModeProperty.OnChanged.addListener(this::onChanged_ModeProperty);
    }

    /**
     *
     */
    public void unbind() {
        if (_audioClipSelector == null)
            return;

        _audioClipSelector.ModeProperty.OnChanged.removeListener(this::onChanged_ModeProperty);
    }


    /**
     * @param observable
     * @param oldVal
     * @param newVal
     */
    private void onValueChanged(final javafx.beans.value.ObservableValue<? extends AudioClipSelector.Mode> observable, final AudioClipSelector.Mode oldVal, final AudioClipSelector.Mode newVal) {
        _audioClipSelector.ModeProperty.setValue(newVal);
    }

    /**
     * @param sender
     * @param args
     */
    private void onChanged_ModeProperty(final Object sender, final ObservableValue.OnChangedEventArgs<AudioClipSelector.Mode> args) {
        if (getValue() != args.Value)
            setValue(args.Value);
    }
}
