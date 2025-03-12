package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
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
     * @param volumeProperty
     */
    public VolumeSliderView(final VolumeProperty volumeProperty) {
        this();
        setVolumeProperty(volumeProperty);
    }

    /**
     * @param volumeProperty
     */
    public void setVolumeProperty(final VolumeProperty volumeProperty) {
        _volumeSlider.setVolumeProperty(volumeProperty);
        _volumeLabel.setVolumeProperty(volumeProperty);
    }
}
