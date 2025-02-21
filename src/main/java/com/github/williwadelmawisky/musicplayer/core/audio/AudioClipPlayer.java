package com.github.williwadelmawisky.musicplayer.core.audio;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.Timer;
import com.github.williwadelmawisky.musicplayer.util.event.Action;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 */
public class AudioClipPlayer {

    /**
     *
     */
    @FunctionalInterface
    public interface OnUpdate {

        /**
         * @param playbackPosition
         * @param duration
         */
        void invoke(final Duration playbackPosition, final Duration duration);
    }

    /**
     *
     */
    @FunctionalInterface
    public interface OnAudioClipReady {

        /**
         * @param audioClip
         * @param duration
         */
        void invoke(final AudioClip audioClip, final Duration duration);
    }

    private Action _onAudioClipFinished;
    private MediaPlayer _mediaPlayer;
    private final VolumeProperty _volumeProperty;
    private final StatusProperty _statusProperty;
    private final ProgressProperty _progressProperty;
    private Timer _timer;
    private OnUpdate _onUpdate;
    private OnAudioClipReady _onAudioClipReady;


    /**
     *
     */
    public AudioClipPlayer() {
        _volumeProperty = new VolumeProperty(0.5);
        _statusProperty = new StatusProperty(false);
        _progressProperty = new ProgressProperty(Duration.ZERO, 0);

        _volumeProperty.getUpdateEvent().addListener(this::onVolumeChanged);
        _statusProperty.getUpdateEvent().addListener(this::onStatusChanged);
    }


    /**
     * @return
     */
    public VolumeProperty getVolumeProperty() { return _volumeProperty; }

    /**
     * @return
     */
    public StatusProperty getStatusProperty() { return _statusProperty; }


    public void set_onAudioClipFinished(Action onAudioClipFinished) {
        _onAudioClipFinished = onAudioClipFinished;
    }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        final boolean wasPlaying = _statusProperty.getValue();
        if (wasPlaying) _mediaPlayer.stop();

        final Media media = ResourceLoader.loadMedia(audioClip.getFilePath());
        _mediaPlayer = new MediaPlayer(media);
        _mediaPlayer.setVolume(_volumeProperty.getValue());

        _mediaPlayer.setOnReady(() -> onMediaReady(audioClip, media, wasPlaying));
        _mediaPlayer.setOnEndOfMedia(this::onMediaFinished);
        _mediaPlayer.currentTimeProperty().addListener(this::onTimeChanged);
    }


    /**
     * @param volume
     */
    private void onVolumeChanged(final double volume) {
        if (_mediaPlayer == null)
            return;

        _mediaPlayer.setVolume(volume);
    }

    /**
     * @param isPlaying
     */
    private void onStatusChanged(final boolean isPlaying) {
        if (_mediaPlayer == null)
            return;

        if (isPlaying) _mediaPlayer.play();
        else _mediaPlayer.pause();
    }

    /**
     * @param onUpdate
     */
    public void setOnUpdate(final OnUpdate onUpdate) { _onUpdate = onUpdate; }

    /**
     * @param onAudioClipReady
     */
    public void setOnAudioClipReady(final OnAudioClipReady onAudioClipReady) { _onAudioClipReady = onAudioClipReady; }


    /**
     * @param audioClip
     * @param media
     * @param play
     */
    private void onMediaReady(final AudioClip audioClip, final Media media, final boolean play) {
        _onAudioClipReady.invoke(audioClip, media.getDuration());
        _timer = new Timer(media.getDuration());
        if (play) _mediaPlayer.play();
    }

    /**
     *
     */
    private void onMediaFinished() {
        _onAudioClipFinished.invoke();
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onTimeChanged(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
        _timer.setPlaybackPosition(newValue);
        _onUpdate.invoke(_timer.getPlaybackPosition(), _timer.getDuration());
    }


    /**
     *
     */
    public void togglePlay() {
        final boolean isPlaying = _statusProperty.getValue();
        _statusProperty.setValue(!isPlaying);
    }

    /**
     *
     */
    public void play() { _statusProperty.setValue(true); }

    /**
     *
     */
    public void pause() { _statusProperty.setValue(false); }


    /**
     * @param duration
     */
    public void rewind(final Duration duration) {
        if (_mediaPlayer == null)
            return;

        _mediaPlayer.seek(duration);
        _timer.setPlaybackPosition(duration);
        _onUpdate.invoke(_timer.getPlaybackPosition(), _timer.getDuration());
    }

    /**
     * @param progress
     */
    public void rewind(final double progress) {
        final Duration duration = _timer.getDuration().multiply(progress);
        rewind(duration);
    }
}
