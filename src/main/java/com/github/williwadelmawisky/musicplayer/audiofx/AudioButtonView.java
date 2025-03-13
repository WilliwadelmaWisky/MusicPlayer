package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import javafx.scene.layout.HBox;

/**
 *
 */
public class AudioButtonView extends HBox {

    private final AudioPreviousButton _audioPreviousButton;
    private final AudioStatusButton _audioStatusButton;
    private final AudioNextButton _audioNextButton;


    /**
     *
     */
    public AudioButtonView() {
        super();

        _audioPreviousButton = new AudioPreviousButton();
        this.getChildren().add(_audioPreviousButton);

        _audioStatusButton = new AudioStatusButton();
        this.getChildren().add(_audioStatusButton);

        _audioNextButton = new AudioNextButton();
        this.getChildren().add(_audioNextButton);

        setSpacing(5);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipListPlayer audioClipPlayer) {
        _audioPreviousButton.bindTo(audioClipPlayer);
        _audioStatusButton.bindTo(audioClipPlayer);
        _audioNextButton.bindTo(audioClipPlayer);
    }
}
