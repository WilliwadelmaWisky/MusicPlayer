package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 */
public class AudioPreviousButton extends Button {

    private AudioClipListPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioPreviousButton() {
        super();

        final ImageView previousButtonImageView = new ImageView(ResourceLoader.loadImage("img/previous_icon.png"));
        previousButtonImageView.setFitWidth(15);
        previousButtonImageView.setPickOnBounds(true);
        previousButtonImageView.setPreserveRatio(true);
        setGraphic(previousButtonImageView);

        setOnAction(this::onClick);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipListPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
    }


    /**
     * @param e
     */
    private void onClick(final ActionEvent e) {
        _audioClipPlayer.getAudioClipSelectorType().getValue().previous(_audioClipPlayer.getSelectionModel());
    }
}
