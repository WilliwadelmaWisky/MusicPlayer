package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.fx.GraphicButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 *
 */
public class PlaybackControlPanel extends HBox {

    private static final String PLAY_ICON_PATH = "img/play_icon.png";
    private static final String PAUSE_ICON_PATH = "img/pause_icon.png";

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
        _previousButton.setOnAction(this::onPreviousButtonClicked);
        this.getChildren().add(_previousButton);

        _playButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/play_icon.png"));
        _playButton.setOnAction(this::onPlayButtonClicked);
        this.getChildren().add(_playButton);

        _nextButton = new AudioPlaybackButton(ResourceLoader.loadImage("img/next_icon.png"));
        _nextButton.setOnAction(this::onNextButtonClicked);
        this.getChildren().add(_nextButton);

        setSpacing(5);
    }


    /**
     * @param audioClip
     */
    public void bindTo(final AudioClip audioClip) {
        _audioClip = audioClip;

        _audioClip.OnPlay.addListener(this::onPlay_AudioClip);
        _audioClip.OnPause.addListener(this::onPause_AudioClip);
        _audioClip.OnStop.addListener(this::onStop_AudioClip);
    }

    /**
     *
     */
    public void unbind() {
        if (_audioClip == null)
            return;

        _audioClip.OnPlay.removeListener(this::onPlay_AudioClip);
        _audioClip.OnPause.removeListener(this::onPause_AudioClip);
        _audioClip.OnStop.removeListener(this::onStop_AudioClip);
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
    private void onPlayButtonClicked(final ActionEvent e) {
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
    private void onPreviousButtonClicked(final ActionEvent e) {
        if (_audioClip != null && _onNext != null)
            _onPrevious.handle(e);
    }

    /**
     * @param e
     */
    private void onNextButtonClicked(final ActionEvent e) {
        if (_audioClip != null && _onNext != null)
            _onNext.handle(e);
    }


    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClip(final Object sender, final EventArgs args) {
        final Image image = ResourceLoader.loadImage(PAUSE_ICON_PATH);
        _playButton.setImage(image);

        _playButton.setDisable(false);
        _previousButton.setDisable(false);
        _nextButton.setDisable(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClip(final Object sender, final EventArgs args) {
        final Image image = ResourceLoader.loadImage(PLAY_ICON_PATH);
        _playButton.setImage(image);

        _playButton.setDisable(false);
        _previousButton.setDisable(false);
        _nextButton.setDisable(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClip(final Object sender, final EventArgs args) {
        final Image image = ResourceLoader.loadImage(PLAY_ICON_PATH);
        _playButton.setImage(image);

        _playButton.setDisable(true);
        _previousButton.setDisable(true);
        _nextButton.setDisable(true);
    }
}
