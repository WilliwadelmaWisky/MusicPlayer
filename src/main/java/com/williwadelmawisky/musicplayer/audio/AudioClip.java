package com.williwadelmawisky.musicplayer.audio;

import java.io.File;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.utilfx.Durations;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 */
public class AudioClip {

    private static final double DEFAULT_VOLUME = 0.5;

    /**
     *
     */
    public enum Status {
        PLAYING,
        PAUSED,
        STOPPED
    }

    public final EventHandler<EventArgs> OnReady;
    public final EventHandler<EventArgs> OnFinish;
    public final EventHandler<EventArgs> OnPlay;
    public final EventHandler<EventArgs> OnPause;
    public final EventHandler<EventArgs> OnStop;
    public final EventHandler<EventArgs_SingleValue<Progress>> OnProgressChanged;
    public final EventHandler<EventArgs_SingleValue<Double>> OnVolumeChanged;

    private final MediaPlayer _mediaPlayer;
    private final String _absoluteFilePath;
    private boolean _isReady;
    private Status _status;


    /**
     * @param audioClip
     * @return
     */
    public static String nameof(final AudioClip audioClip) {
        final File file = new File(audioClip.getAbsoluteFilePath());
        return Files.getNameWithoutExtension(file);
    }


    /**
     * @param file
     */
    public AudioClip(final File file) {
        _absoluteFilePath = file.getAbsolutePath();

        OnReady = new EventHandler<>();
        OnFinish = new EventHandler<>();
        OnPlay = new EventHandler<>();
        OnPause = new EventHandler<>();
        OnStop = new EventHandler<>();
        OnVolumeChanged = new EventHandler<>();
        OnProgressChanged = new EventHandler<>();

        final Media media = ResourceLoader.loadMedia(file.getPath());
        _mediaPlayer = new MediaPlayer(media);
        _mediaPlayer.setVolume(Math.clamp(DEFAULT_VOLUME, 0, 1));
        _mediaPlayer.setOnReady(this::onReady_MediaPlayer);
        _mediaPlayer.setOnEndOfMedia(this::onEndOfMedia_MediaPlayer);
        _mediaPlayer.currentTimeProperty().addListener(this::onCurrentTimeChanged_MediaPlayer);
    }


    /**
     * @return
     */
    private String getID() { return _absoluteFilePath; }

    /**
     * @return
     */
    String getAbsoluteFilePath() { return _absoluteFilePath; }

    /**
     * @return
     */
    public boolean isReady() { return _isReady; }

    /**
     * @return
     */
    public boolean isPlaying() { return _status == Status.PLAYING; }

    /**
     * @return
     */
    public Status getStatus() { return _status; }

    /**
     * @return
     */
    public Duration getTotalDuration() { return _mediaPlayer.getTotalDuration(); }

    /**
     * @return
     */
    public double getVolume() { return _mediaPlayer.getVolume(); }


    /**
     * @param volume
     */
    void setVolume(final double volume) {
        final double clampedVolume = Math.clamp(volume, 0, 1);
        if (Math.abs(_mediaPlayer.getVolume() - clampedVolume) <= 1e-6)
            return;

       _mediaPlayer.setVolume(clampedVolume);
        OnVolumeChanged.invoke(this, new EventArgs_SingleValue<>(clampedVolume));
    }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof AudioClip audioClip))
            return false;

        return getID().equals(audioClip.getID());
    }


    /**
     * @return
     */
    boolean play() {
        if (!isReady() || _status == Status.PLAYING)
            return false;

        _mediaPlayer.play();
        _status = Status.PLAYING;
        OnPlay.invoke(this, EventArgs.EMPTY);
        return true;
    }

    /**
     * @return
     */
    boolean pause() {
        if (!isReady() || _status == Status.PAUSED)
            return false;

        _mediaPlayer.pause();
        _status = Status.PAUSED;
        OnPause.invoke(this, EventArgs.EMPTY);
        return true;
    }

    /**
     * @return
     */
    boolean stop() {
        if (!isReady() || _status == Status.STOPPED)
            return false;

        _mediaPlayer.stop();
        _status = Status.STOPPED;
        OnStop.invoke(this, EventArgs.EMPTY);
        return true;
    }


    /**
     * @param clampedNormalizedPlaybackPosition
     * @param clampedPlaybackPosition
     */
    private void _seek(final double clampedNormalizedPlaybackPosition, final Duration clampedPlaybackPosition) {
        _mediaPlayer.seek(clampedPlaybackPosition);
        final Progress progress = new Progress(clampedNormalizedPlaybackPosition, clampedPlaybackPosition, _mediaPlayer.getTotalDuration());
        OnProgressChanged.invoke(this, new EventArgs_SingleValue<>(progress));
    }

    /**
     * @param normalizedPlaybackPosition
     * @return
     */
    public boolean seek(final double normalizedPlaybackPosition) {
        if (!_isReady || _status == Status.STOPPED)
            return false;

        final double clampedNormalizedPlaybackPosition = Math.clamp(normalizedPlaybackPosition, 0, 1);
        final Duration clampedPlaybackPosition = _mediaPlayer.getTotalDuration().multiply(clampedNormalizedPlaybackPosition);
        _seek(clampedNormalizedPlaybackPosition, clampedPlaybackPosition);
        return true;
    }

    /**
     * @param playbackPosition
     * @return
     */
    public boolean seek(final Duration playbackPosition) {
        if (!_isReady || _status == Status.STOPPED)
            return false;

        final Duration clampedPlaybackPosition = Durations.clamp(playbackPosition, Duration.ZERO, _mediaPlayer.getTotalDuration());
        final double clampedNormalizedPlaybackPosition = clampedPlaybackPosition.toMillis() / _mediaPlayer.getTotalDuration().toMillis();
        _seek(clampedNormalizedPlaybackPosition, clampedPlaybackPosition);
        return true;
    }

    /**
     * @param amount
     * @return
     */
    public boolean jumpForward(final Duration amount) {
        if (!_isReady || _status == Status.STOPPED)
            return false;

        final Duration playbackPosition = _mediaPlayer.getCurrentTime().add(amount);
        return seek(playbackPosition);
    }

    /**
     * @param amount
     * @return
     */
    public boolean jumpBackward(final Duration amount) {
        if (!_isReady || _status == Status.STOPPED)
            return false;

        final Duration playbackPosition = _mediaPlayer.getCurrentTime().subtract(amount);
        return seek(playbackPosition);
    }

    /**
     * @return
     */
    public boolean jumpToStart() {
        if (!_isReady || _status == Status.STOPPED)
            return false;

        return seek(Duration.ZERO);
    }


    /**
     *
     */
    private void onReady_MediaPlayer() {
        _isReady = true;
        OnReady.invoke(this, EventArgs.EMPTY);
    }

    /**
     *
     */
    private void onEndOfMedia_MediaPlayer() {
        _mediaPlayer.stop();
        _status = Status.STOPPED;
        OnFinish.invoke(this, EventArgs.EMPTY);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onCurrentTimeChanged_MediaPlayer(final javafx.beans.value.ObservableValue<? extends Duration> observable, final Duration oldValue, final Duration newValue) {
        final double normalizedPlaybackPosition = newValue.toMillis() / _mediaPlayer.getTotalDuration().toMillis();
        final Progress progress = new Progress(normalizedPlaybackPosition, newValue, _mediaPlayer.getTotalDuration());
        OnProgressChanged.invoke(this, new EventArgs_SingleValue<>(progress));
    }
}
