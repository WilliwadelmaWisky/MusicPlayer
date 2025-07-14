package com.williwadelmawisky.musicplayer.util.fx;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 */
public abstract class GraphicButton extends Button {
    
    private final ImageView _imageView;


    /**
     * @param image
     */
    public GraphicButton(final Image image) {
        this(image, 30);
    }

    /**
     * @param image
     * @param fitWidth
     */
    public GraphicButton(final Image image, final double fitWidth) {
        super();

        _imageView = new ImageView(image);
        _imageView.setFitWidth(fitWidth);
        _imageView.setPickOnBounds(true);
        _imageView.setPreserveRatio(true);
        setGraphic(_imageView);
    }


    /**
     * @param image
     */
    public void setImage(final Image image) {
        _imageView.setImage(image);
    }
}
