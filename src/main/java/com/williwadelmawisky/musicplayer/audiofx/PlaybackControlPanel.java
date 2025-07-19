package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.fx.GraphicButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 *
 */
public class PlaybackControlPanel extends HBox {

    private static final String PLAY_ICON_PATH = "img/play_icon.png";
    private static final String PAUSE_ICON_PATH = "img/pause_icon.png";

    private final AudioPlaybackButton _playButton;
    private final AudioPlaybackButton _previousButton;
    private final AudioPlaybackButton _nextButton;
    private EventHandler<ActionEvent> _onPrevious;
    private EventHandler<ActionEvent> _onNext;
    private AudioClip _audioClip;


    /**
     *
     */
    public PlaybackControlPanel() {
        super();

        _previousButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/previous_icon.png"));
        getChildren().add(_previousButton);

        _playButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/play_icon.png"));
        getChildren().add(_playButton);

        _nextButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/next_icon.png"));
        getChildren().add(_nextButton);

        setSpacing(5);
        setDisable(true);

        _previousButton.setOnAction(this::onClicked_PreviousButton);
        _playButton.setOnAction(this::onClicked_PlayButton);
        _nextButton.setOnAction(this::onClicked_NextButton);
    }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        _audioClip = audioClip;

        updateImage(_audioClip.isPlaying());
        setDisable(false);

        _audioClip.OnPlay.addListener(this::onPlay_AudioClip);
        _audioClip.OnPause.addListener(this::onPause_AudioClip);
        _audioClip.OnStop.addListener(this::onStop_AudioClip);
    }

    /**
     *
     */
    public void clear() {
        updateImage(false);
        setDisable(true);
        if (_audioClip == null)
            return;

        _audioClip.OnPlay.removeListener(this::onPlay_AudioClip);
        _audioClip.OnPause.removeListener(this::onPause_AudioClip);
        _audioClip.OnStop.removeListener(this::onStop_AudioClip);
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
     * @return
     */
    public EventHandler<ActionEvent> getOnPrevious() { return _onPrevious; }

    /**
     * @return
     */
    public EventHandler<ActionEvent> getOnNext() { return _onNext; }

    /**
     * @param onPrevious
     */
    public void setOnPrevious(final EventHandler<ActionEvent> onPrevious) { _onPrevious = onPrevious; }

    /**
     * @param onNext
     */
    public void setOnNext(final EventHandler<ActionEvent> onNext) { _onNext = onNext; }


    /**
     * @param e
     */
    private void onClicked_PlayButton(final ActionEvent e) {
        if (_audioClip == null)
            return;

        switch (_audioClip.getStatus()) {
            case PLAYING -> _audioClip.pause();
            case PAUSED -> _audioClip.play();
        }
    }

    /**
     * @param e
     */
    private void onClicked_PreviousButton(final ActionEvent e) {
        if (_audioClip != null && _onPrevious != null)
            _onPrevious.handle(e);
    }

    /**
     * @param e
     */
    private void onClicked_NextButton(final ActionEvent e) {
        if (_audioClip != null && _onNext != null)
            _onNext.handle(e);
    }


    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClip(final Object sender, final EventArgs args) {
        updateImage(true);
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClip(final Object sender, final EventArgs args) {
        updateImage(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClip(final Object sender, final EventArgs args) {
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
