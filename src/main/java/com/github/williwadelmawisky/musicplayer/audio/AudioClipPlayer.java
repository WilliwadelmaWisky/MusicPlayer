package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.util.Action;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 */
public class AudioClipPlayer {

    private Action _onAudioClipFinished;
    private MediaPlayer _mediaPlayer;
    private final VolumeProperty _volumeProperty;
    private final StatusProperty _statusProperty;
    private final ProgressProperty _progressProperty;
    private final AudioProperty _audioProperty;


    /**
     *
     */
    public AudioClipPlayer() {
        _volumeProperty = new VolumeProperty(0.5);
        _statusProperty = new StatusProperty(false);
        _progressProperty = new ProgressProperty(0);
        _audioProperty = new AudioProperty(null, Duration.ZERO);

        _volumeProperty.getUpdateEvent().addListener(this::onVolumeChanged);
        _statusProperty.getUpdateEvent().addListener(this::onStatusChanged);
        _progressProperty.getUpdateEvent().addListener(this::onProgressChanged);
    }


    /**
     * @return
     */
    public VolumeProperty getVolumeProperty() { return _volumeProperty; }

    /**
     * @return
     */
    public StatusProperty getStatusProperty() { return _statusProperty; }

    /**
     * @return
     */
    public ProgressProperty getProgressProperty() { return _progressProperty; }

    /**
     * @return
     */
    public AudioProperty getAudioProperty() { return _audioProperty; }


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
     * @param changeEvent
     */
    private void onProgressChanged(final ProgressProperty.ChangeEvent changeEvent) {
        if (_mediaPlayer == null || changeEvent.EventType == ProgressProperty.ChangeEvent.Type.IGNORED)
            return;

        final Duration playbackPosition = _audioProperty.getDuration().multiply(changeEvent.Progress);
        _mediaPlayer.seek(playbackPosition);
    }


    /**
     * @param audioClip
     * @param media
     * @param play
     */
    private void onMediaReady(final AudioClip audioClip, final Media media, final boolean play) {
        _audioProperty.setValue(audioClip, media.getDuration());
        _progressProperty.setValue(0, ProgressProperty.ChangeEvent.Type.IGNORED);
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
        final double progress = newValue.toMillis() / _audioProperty.getDuration().toMillis();
        _progressProperty.setValue(progress, ProgressProperty.ChangeEvent.Type.IGNORED);
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
        final double progress = duration.toMillis() / _audioProperty.getDuration().toMillis();
        rewind(progress);
    }

    /**
     * @param progress
     */
    public void rewind(final double progress) {
        _progressProperty.setValue(progress);
    }
}
