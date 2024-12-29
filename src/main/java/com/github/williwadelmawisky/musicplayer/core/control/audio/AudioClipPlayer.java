package com.github.williwadelmawisky.musicplayer.core.control.audio;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.control.Timer;
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

    /**
     *
     */
    @FunctionalInterface
    public interface OnStatusChanged {

        /**
         * @param isPlaying
         */
        void invoke(final boolean isPlaying);
    }

    private final Action _onAudioClipFinished;
    private MediaPlayer _mediaPlayer;
    private double _volume;
    private Timer _timer;
    private OnUpdate _onUpdate;
    private OnAudioClipReady _onAudioClipReady;
    private OnStatusChanged _onStatusChanged;


    /**
     * @param onAudioClipFinished
     */
    public AudioClipPlayer(final Action onAudioClipFinished) {
        _onAudioClipFinished = onAudioClipFinished;
        _volume = 0.5f;
    }


    /**
     * @return
     */
    public double getVolume() { return _volume; }

    /**
     * @return
     */
    public boolean isPlaying() { return _mediaPlayer != null &&_mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING; }


    /**
     * @param volume
     */
    public void setVolume(final double volume) {
        _volume = Math.clamp(volume, 0, 1);
        if (_mediaPlayer != null) _mediaPlayer.setVolume(_volume);
    }

    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        final boolean wasPlaying = isPlaying();
        if (wasPlaying) _mediaPlayer.stop();

        final Media media = ResourceLoader.loadMedia(audioClip.getFilePath());
        _mediaPlayer = new MediaPlayer(media);
        _mediaPlayer.setVolume(_volume);

        _mediaPlayer.setOnReady(() -> onAudioClipReady(audioClip, media, wasPlaying));
        _mediaPlayer.setOnEndOfMedia(this::onAudioFinished);
        _mediaPlayer.currentTimeProperty().addListener(this::onUpdate);
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
     * @param onStatusChanged
     */
    public void setOnStatusChanged(final OnStatusChanged onStatusChanged) { _onStatusChanged = onStatusChanged; }


    /**
     * @param audioClip
     * @param media
     * @param play
     */
    private void onAudioClipReady(final AudioClip audioClip, final Media media, final boolean play) {
        _onAudioClipReady.invoke(audioClip, media.getDuration());
        _timer = new Timer(media.getDuration());
        if (play) play();
    }

    /**
     *
     */
    private void onAudioFinished() {
        _onAudioClipFinished.invoke();
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onUpdate(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
        _timer.setPlaybackPosition(newValue);
        _onUpdate.invoke(_timer.getPlaybackPosition(), _timer.getDuration());
    }


    /**
     *
     */
    public void togglePlay() {
        if (isPlaying()) {
            pause();
            return;
        }

        play();
    }


    /**
     *
     */
    public void play() {
        if (_mediaPlayer == null)
            return;

        _mediaPlayer.play();
        _onStatusChanged.invoke(true);
    }

    /**
     *
     */
    public void pause() {
        if (_mediaPlayer == null)
            return;

        _mediaPlayer.pause();
        _onStatusChanged.invoke(false);
    }

    /**
     *
     */
    public void stop() {
        if (_mediaPlayer == null)
            return;

        _mediaPlayer.stop();
        _onStatusChanged.invoke(false);
        _mediaPlayer = null;
    }


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
