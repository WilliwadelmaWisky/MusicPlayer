package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 */
public class VolumeControlPanel extends HBox {

    private final Label _label;
    private final Slider _slider;
    private AudioClip _audioClip;


    /**
     *
     */
    public VolumeControlPanel() {
        super();

        this.setSpacing(5);

        final ImageView imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(ResourceLoader.loadImage("img/volume_icon.png"));
        this.getChildren().add(imageView);

        _slider = new Slider();
        _slider.setMin(0);
        _slider.setMax(1);
        _slider.valueProperty().addListener(this::onValueChanged_Slider);
        this.getChildren().add(_slider);

        _label = new Label();
        _label.setMinWidth(30);
        this.getChildren().add(_label);
    }


    /**
     * @param audioClip
     */
    public void bindTo(final AudioClip audioClip) {
        _audioClip = audioClip;

        _audioClip.OnVolumeChanged.addListener(this::onVolumeChanged_AudioClip);
    }

    /**
     *
     */
    public void unbind() {
        if (_audioClip == null)
            return;

        _audioClip.OnVolumeChanged.removeListener(this::onVolumeChanged_AudioClip);
    }


    /**
     * @param sender
     * @param args
     */
    private void onVolumeChanged_AudioClip(final Object sender, final EventArgs_SingleValue<Double> args) {
        _slider.setValue(args.Value);

        final int percentage = (int)(args.Value * 100);
        _label.setText(percentage + "%");
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onValueChanged_Slider(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) {
        if (_audioClip != null)
            _audioClip.setVolume(newValue.doubleValue());
    }
}
