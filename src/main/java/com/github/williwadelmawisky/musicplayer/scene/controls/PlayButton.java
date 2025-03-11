package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.StatusProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 */
public class PlayButton extends Button {

    private static final String PLAY_ICON = "img/play_icon.png";
    private static final String PAUSE_ICON = "img/pause_icon.png";

    private final ImageView _imageView;
    private StatusProperty _statusProperty;


    /**
     *
     */
    public PlayButton() {
        super();

        _imageView = new ImageView();
        _imageView.setFitWidth(15);
        _imageView.setPickOnBounds(true);
        _imageView.setPreserveRatio(true);
        this.setGraphic(_imageView);
        this.setOnAction(this::onClicked);
    }

    /**
     * @param statusProperty
     */
    public PlayButton(final StatusProperty statusProperty) {
        this();
        setStatusProperty(statusProperty);
    }


    /**
     * @param statusProperty
     */
    public void setStatusProperty(final StatusProperty statusProperty) {
        if (_statusProperty != null) _statusProperty.getUpdateEvent().removeListener(this::onStatusChanged);

        _statusProperty = statusProperty;
        updateView(_statusProperty.getValue());
        _statusProperty.getUpdateEvent().addListener(this::onStatusChanged);
    }

    /**
     * @param isPlaying
     */
    private void updateView(boolean isPlaying) {
        String iconPath = isPlaying ? PAUSE_ICON : PLAY_ICON;
        _imageView.setImage(ResourceLoader.loadImage(iconPath));
    }

    /**
     * @param isPlaying
     */
    private void onStatusChanged(final boolean isPlaying) {
        updateView(isPlaying);
    }

    /**
     * @param e
     */
    private void onClicked(ActionEvent e) {
        final boolean isPlaying = _statusProperty.getValue();
        _statusProperty.setValue(!isPlaying);
    }
}
