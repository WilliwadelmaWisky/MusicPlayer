package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioStatus;
import com.github.williwadelmawisky.musicplayer.fxutils.GraphicButton;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 *
 */
public class AudioPlaybackControlView extends HBox {

    private final AudioPlaybackButton _playButton;
    private AudioClipListPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioPlaybackControlView() {
        super();

        final AudioPlaybackButton previousButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/previous_icon.png"));
        previousButton.setOnAction(this::onPreviousButtonClicked);
        this.getChildren().add(previousButton);

        _playButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/play_icon.png"));
        _playButton.setOnAction(this::onPlayButtonClicked);
        this.getChildren().add(_playButton);

        final AudioPlaybackButton nextButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/next_icon.png"));
        nextButton.setOnAction(this::onNextButtonClicked);
        this.getChildren().add(nextButton);

        setSpacing(5);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipListPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        audioClipPlayer.OnStatusChanged.addListener(this::onStatusChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onStatusChanged(final Object sender, final AudioClipPlayer.OnStatusChangedEventArgs args) {
        final String iconPath = args.AudioStatus == AudioStatus.PLAYING ? "img/pause_icon.png" : "img/play_icon.png";
        _playButton.setImage(ResourceLoader.loadImage(iconPath));
    }


    /**
     * @param e
     */
    private void onPreviousButtonClicked(final ActionEvent e) {
        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.getAudioClipSelectorType().getValue().previous(_audioClipPlayer.getSelectionModel());
    }

    /**
     * @param e
     */
    private void onNextButtonClicked(final ActionEvent e) {
        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.getAudioClipSelectorType().getValue().next(_audioClipPlayer.getSelectionModel());
    }

    /**
     * @param e
     */
    private void onPlayButtonClicked(final ActionEvent e) {
        if (_audioClipPlayer == null)
            return;

        final AudioStatus audioStatus = _audioClipPlayer.getAudioStatus() == AudioStatus.PLAYING ? AudioStatus.PAUSED : AudioStatus.PLAYING;
        _audioClipPlayer.setAudioStatus(audioStatus);
    }


    /**
     *
     */
    private static class AudioPlaybackButton extends GraphicButton {

        private static final double WIDTH = 15;


        /**
         * @param image
         */
        public AudioPlaybackButton(final Image image) {
            super(image, WIDTH);
        }
    }
}
