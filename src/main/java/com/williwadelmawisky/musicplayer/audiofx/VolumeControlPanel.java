package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.audio.ObservableValue;
import com.williwadelmawisky.musicplayer.util.event.ChangeEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class VolumeControlPanel extends HBox {

    private static final String[] VOLUME_SYMBOL_ARRAY = new String[] {
            "\uD83D\uDD08",
            "\uD83D\uDD09",
            "\uD83D\uDD0A"
    };

    private final Label _volumeIconLabel;
    private final Label _volumePercentLabel;
    private final Slider _slider;

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public VolumeControlPanel() {
        super();

        _volumeIconLabel = new Label();
        _volumeIconLabel.setAlignment(Pos.CENTER);
        _volumeIconLabel.setMinWidth(20);
        _volumeIconLabel.setMaxWidth(20);
        _slider = new Slider();
        HBox.setHgrow(_slider, Priority.ALWAYS);
        _slider.setMaxWidth(Double.POSITIVE_INFINITY);
        _slider.setMin(0);
        _slider.setMax(1);
        _volumePercentLabel = new Label();
        _volumePercentLabel.setAlignment(Pos.CENTER_RIGHT);
        _volumePercentLabel.setMinWidth(40);
        _volumePercentLabel.setMaxWidth(40);

        setSpacing(5);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(_volumeIconLabel, _slider, _volumePercentLabel);

        _volumeIconLabel.setOnMouseClicked(this::onClicked_VolumeIconLabel);
        _slider.valueProperty().addListener(this::onValueChanged_Slider);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        updateView(_audioClipPlayer.VolumeProperty.getValue());
        _audioClipPlayer.VolumeProperty.OnChanged.addListener(this::onChanged_VolumeProperty);
    }

    /**
     *
     */
    public void clear() {
        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.VolumeProperty.OnChanged.removeListener(this::onChanged_VolumeProperty);
    }


    /**
     * @param volume
     */
    private void updateView(final double volume) {
        updateIconLabel(volume);
        _slider.setValue(volume);
        updatePercentLabel(volume);
    }

    /**
     * @param volume
     */
    private void updateIconLabel(final double volume) {
        final int index = volume <= 1e-6
                ? 0 : (volume <= 0.7
                ? 1
                : 2);
        final String textString = VOLUME_SYMBOL_ARRAY[index];
        _volumeIconLabel.setText(textString);
    }

    /**
     * @param volume
     */
    private void updatePercentLabel(final double volume) {
        final int percentage = (int)(volume * 100);
        _volumePercentLabel.setText(percentage + "%");
    }


    /**
     * @param e
     */
    private void onClicked_VolumeIconLabel(final MouseEvent e) {
        _audioClipPlayer.setVolume(0);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onValueChanged_Slider(final javafx.beans.value.ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
        updateIconLabel(newValue.doubleValue());
        updatePercentLabel(newValue.doubleValue());
        _audioClipPlayer.setVolume(newValue.doubleValue());
    }

    /**
     * @param sender
     * @param e
     */
    private void onChanged_VolumeProperty(final Object sender, final ChangeEvent<Double> e) {
        if (Math.abs(_slider.getValue() - e.Value) <= 1e-6)
            return;

        updateView(e.Value);
    }
}
