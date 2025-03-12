package com.github.williwadelmawisky.musicplayer.audio;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.utils.EventArgs;
import com.github.williwadelmawisky.musicplayer.utils.EventHandler;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 */
public class AudioClipPlayer {

    public final EventHandler<OnAudioClipStartedEventArgs> OnAudioClipStarted;
    public final EventHandler<OnAudioClipFinishedEventArgs> OnAudioClipFinished;
    public final EventHandler<OnProgressUpdatedEventArgs> OnProgressUpdated;
    public final EventHandler<OnStatusChangedEventArgs> OnStatusChanged;
    public final EventHandler<OnVolumeChangedEventArgs> OnVolumeChanged;

    private MediaPlayer _mediaPlayer;
    private AudioStatus _audioStatus;
    private AudioClip _audioClip;
    private double _volume;


    /**
     *
     */
    public AudioClipPlayer() {
        OnAudioClipStarted = new EventHandler<>();
        OnAudioClipFinished = new EventHandler<>();
        OnProgressUpdated = new EventHandler<>();
        OnStatusChanged = new EventHandler<>();
        OnVolumeChanged = new EventHandler<>();

        setAudioStatus(AudioStatus.STOPPED);
        setVolume(0.5);
    }


    /**
     * @return
     */
    public double getVolume() { return _volume; }

    /**
     * @return
     */
    public AudioStatus getAudioStatus() { return _audioStatus; }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        _audioClip = audioClip;
        final boolean wasPlaying = _audioStatus == AudioStatus.PLAYING;
        if (wasPlaying) _mediaPlayer.stop();

        final Media media = ResourceLoader.loadMedia(audioClip.getFilePath());
        _mediaPlayer = new MediaPlayer(media);
        _mediaPlayer.setVolume(_volume);
        _mediaPlayer.setOnReady(() -> onMediaReady(audioClip, media, wasPlaying));
        _mediaPlayer.setOnEndOfMedia(this::onMediaFinished);
        _mediaPlayer.currentTimeProperty().addListener(this::onTimeChanged);
    }

    /**
     * @param audioStatus
     */
    public void setAudioStatus(final AudioStatus audioStatus) {
        if (_audioStatus == audioStatus || (_mediaPlayer == null && audioStatus != AudioStatus.STOPPED))
            return;

        _audioStatus = audioStatus;
        switch (_audioStatus) {
            case STOPPED -> {
                if (_mediaPlayer != null) _mediaPlayer.stop();
                _audioClip = null;
            }
            case PAUSED -> {
                _mediaPlayer.pause();
            }
            case PLAYING -> {
                _mediaPlayer.play();
            }
        }

        OnStatusChanged.invoke(this, new OnStatusChangedEventArgs(_audioStatus));
    }

    /**
     * @param volume
     */
    public void setVolume(final double volume) {
        if (Math.abs(_volume - volume) <= 1e-6)
            return;

        _volume = Math.clamp(volume, 0, 1);
        if (_mediaPlayer != null) _mediaPlayer.setVolume(_volume);
        OnVolumeChanged.invoke(this, new OnVolumeChangedEventArgs(_volume));
    }


    /**
     * @param playbackPosition
     */
    public void rewind(final Duration playbackPosition) {
        if (_mediaPlayer != null) _mediaPlayer.seek(playbackPosition);
    }

    /**
     * @param progress
     */
    public void rewind(final double progress) {
        if (_mediaPlayer == null)
            return;

        final Duration playbackPosition = _mediaPlayer.getTotalDuration().multiply(Math.clamp(progress, 0, 1));
        _mediaPlayer.seek(playbackPosition);
    }


    /**
     * @param audioClip
     * @param media
     * @param play
     */
    private void onMediaReady(final AudioClip audioClip, final Media media, final boolean play) {
        OnAudioClipStarted.invoke(this, new OnAudioClipStartedEventArgs(audioClip));
        if (play) _mediaPlayer.play();
    }

    /**
     *
     */
    private void onMediaFinished() {
        OnAudioClipFinished.invoke(this, new OnAudioClipFinishedEventArgs(_audioClip));
        setAudioStatus(AudioStatus.STOPPED);
    }

    /**
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void onTimeChanged(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
        final double progress = newValue.toMillis() / _mediaPlayer.getTotalDuration().toMillis();
        OnProgressUpdated.invoke(this, new OnProgressUpdatedEventArgs(progress, newValue, _mediaPlayer.getTotalDuration()));
    }


    /**
     *
     */
    public static class OnAudioClipStartedEventArgs extends EventArgs {

        public final AudioClip AudioClip;

        /**
         * @param audioClip
         */
        public OnAudioClipStartedEventArgs(final AudioClip audioClip) {
            AudioClip = audioClip;
        }
    }

    /**
     *
     */
    public static class OnAudioClipFinishedEventArgs extends EventArgs {

        public final AudioClip AudioClip;

        /**
         * @param audioClip
         */
        public OnAudioClipFinishedEventArgs(final AudioClip audioClip) {
            AudioClip = audioClip;
        }
    }

    /**
     *
     */
    public static class OnProgressUpdatedEventArgs extends EventArgs {

        public final double Progress;
        public final Duration PlaybackPosition;
        public final Duration TotalDuration;

        /**
         * @param progress
         * @param playbackPosition
         * @param totalDuration
         */
        public OnProgressUpdatedEventArgs(final double progress, final Duration playbackPosition, final Duration totalDuration) {
            Progress = progress;
            PlaybackPosition = playbackPosition;
            TotalDuration = totalDuration;
        }
    }

    /**
     *
     */
    public static class OnStatusChangedEventArgs extends EventArgs {

        public final AudioStatus AudioStatus;

        /**
         * @param audioStatus
         */
        public OnStatusChangedEventArgs(final AudioStatus audioStatus) {
            AudioStatus = audioStatus;
        }
    }

    /**
     *
     */
    public static class OnVolumeChangedEventArgs extends EventArgs {

        public final double Volume;

        /**
         * @param volume
         */
        public OnVolumeChangedEventArgs(final double volume) {
            Volume = volume;
        }
    }
}
