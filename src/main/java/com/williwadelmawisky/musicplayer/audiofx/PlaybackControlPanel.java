package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;
import com.williwadelmawisky.musicplayer.utilfx.GraphicButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 *
 */
public class PlaybackControlPanel extends HBox {

    private static final String PLAY_ICON_PATH = "img/play_icon.png";
    private static final String PAUSE_ICON_PATH = "img/pause_icon.png";

    private final AudioPlaybackButton _playButton;
    private final AudioPlaybackButton _previousButton;
    private final AudioPlaybackButton _nextButton;
    private final AudioPlaybackButton _stopButton;
    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public PlaybackControlPanel() {
        super();

        _playButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/play_icon.png"));
        _stopButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/pause_icon.png"));
        _previousButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/previous_icon.png"));
        _nextButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/next_icon.png"));

        final Region region = new Region();
        region.setMinWidth(10);
        region.setMaxWidth(10);

        setSpacing(5);
        getChildren().addAll(_playButton, region, _previousButton, _stopButton, _nextButton);

        _playButton.setOnAction(this::onClicked_PlayButton);
        _stopButton.setOnAction(this::onClicked_StopButton);
        _previousButton.setOnAction(this::onClicked_PreviousButton);
        _nextButton.setOnAction(this::onClicked_NextButton);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        final boolean isPlaying = _audioClipPlayer.SelectionModel.hasValue() && _audioClipPlayer.SelectionModel.getValue().isPlaying();
        updateImage(isPlaying);

        _audioClipPlayer.OnPlay.addListener(this::onPlay_AudioClipPlayer);
        _audioClipPlayer.OnPause.addListener(this::onPause_AudioClipPlayer);
        _audioClipPlayer.OnStop.addListener(this::onStop_AudioClipPlayer);
    }

    /**
     *
     */
    public void clear() {
        updateImage(false);

        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.OnPlay.removeListener(this::onPlay_AudioClipPlayer);
        _audioClipPlayer.OnPause.removeListener(this::onPause_AudioClipPlayer);
        _audioClipPlayer.OnStop.removeListener(this::onStop_AudioClipPlayer);
    }


    /**
     * @param image
     */
    private void updateImage(final Image image) {
        _playButton.setImage(image);
    }

    /**
     * @param imageResourcePath
     */
    private void updateImage(final String imageResourcePath) {
        final Image image = ResourceLoader.loadImage(imageResourcePath);
        updateImage(image);
    }

    /**
     * @param isPlaying
     */
    private void updateImage(final boolean isPlaying) {
        final String imageResourcePath = isPlaying ? PAUSE_ICON_PATH : PLAY_ICON_PATH;
        updateImage(imageResourcePath);
    }


    /**
     * @param e
     */
    private void onClicked_PlayButton(final ActionEvent e) {
        final AudioClip audioClip = _audioClipPlayer.SelectionModel.getValue();
        switch (audioClip.getStatus()) {
            case PLAYING -> _audioClipPlayer.pause();
            case PAUSED -> _audioClipPlayer.play();
        }
    }

    /**
     * @param e
     */
    private void onClicked_StopButton(final ActionEvent e) {
        _audioClipPlayer.stop();
    }

    /**
     * @param e
     */
    private void onClicked_PreviousButton(final ActionEvent e) {
        _audioClipPlayer.SelectionModel.selectPrevious();
    }

    /**
     * @param e
     */
    private void onClicked_NextButton(final ActionEvent e) {
        _audioClipPlayer.SelectionModel.selectNext();
    }


    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updateImage(true);
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updateImage(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updateImage(false);
    }


    /**
     *
     */
    private static final class AudioPlaybackButton extends GraphicButton {

        private static final double WIDTH = 15;

        /**
         * @param image
         */
        public AudioPlaybackButton(final Image image) {
            super(image, WIDTH);
        }
    }
}
