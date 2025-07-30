package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.audio.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class VolumeControlPanel extends HBox {

    private final Label _label;
    private final Slider _slider;
    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public VolumeControlPanel() {
        super();

        final ImageView imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(ResourceLoader.loadImage("img/volume_icon.png"));

        _slider = new Slider();
        HBox.setHgrow(_slider, Priority.ALWAYS);
        _slider.setMaxWidth(Double.POSITIVE_INFINITY);
        _slider.setMin(0);
        _slider.setMax(1);

        _label = new Label();
        _label.setMinWidth(30);

        updateView(0.5);
        setSpacing(5);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(imageView, _slider, _label);

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
        updateSlider(volume);
        updateLabel(volume);
    }

    /**
     * @param volume
     */
    private void updateLabel(final double volume) {
        final int percentage = (int)(volume * 100);
        _label.setText(percentage + "%");
    }

    /**
     * @param volume
     */
    private void updateSlider(final double volume) {
        _slider.setValue(volume);
    }


    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onValueChanged_Slider(final javafx.beans.value.ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
        updateLabel(newValue.doubleValue());
        _audioClipPlayer.setVolume(newValue.doubleValue());
    }

    /**
     * @param sender
     * @param args
     */
    private void onChanged_VolumeProperty(final Object sender, final ObservableValue.ChangeEventArgs<Double> args) {
        if (Math.abs(_slider.getValue() - args.Value) <= 1e-6)
            return;

        updateView(args.Value);
    }
}
