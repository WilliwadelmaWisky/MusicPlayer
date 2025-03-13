package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 */
public class VolumeSliderView extends HBox {

    private final VolumeLabel _volumeLabel;
    private final VolumeSlider _volumeSlider;


    /**
     *
     */
    public VolumeSliderView() {
        super();

        this.setSpacing(5);

        final ImageView imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(ResourceLoader.loadImage("img/volume_icon.png"));
        this.getChildren().add(imageView);

        _volumeSlider = new VolumeSlider();
        this.getChildren().add(_volumeSlider);

        _volumeLabel = new VolumeLabel();
        _volumeLabel.setMinWidth(30);
        this.getChildren().add(_volumeLabel);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _volumeSlider.bindTo(audioClipPlayer);
        _volumeLabel.bindTo(audioClipPlayer);
    }
}
