package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.core.Timer;
import com.github.williwadelmawisky.musicplayer.core.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.db.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.util.event.Action;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.MouseInfo;

/**
 *
 */
public class AudioControlPanel extends VBox {

    @FXML private Label _playbackPositionLabel;
    @FXML private Label _durationLabel;
    @FXML private Label _titleLabel;
    @FXML private Label _artistLabel;
    @FXML private ProgressBar _progressBar;
    @FXML private VolumeSlider _volumeSlider;
    @FXML private PlayButton _playButton;

    private AudioClipPlayer _audioClipPlayer;
    private FetchGetHandler _fetchHandler;
    private Action _onPrevious, _onNext;


    /**
     *
     */
    public AudioControlPanel() {
        super();

        ResourceLoader.loadFxml("fxml/controls/AudioControlPanel.fxml", this);

        _titleLabel.setText("");
        _artistLabel.setText("");
        _playbackPositionLabel.setText("0:00");
        _durationLabel.setText("0:00");
        _progressBar.setProgress(0);
        setDisable(true);

        _progressBar.setOnMouseClicked(this::onProgressBarClicked);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _volumeSlider.setVolumeProperty(_audioClipPlayer.getVolumeProperty());
        _playButton.setStatusProperty(_audioClipPlayer.getStatusProperty());

        _audioClipPlayer.setOnUpdate(this::onUpdate);
        _audioClipPlayer.setOnAudioClipReady(this::onAudioClipReady);
    }

    /**
     *
     * @param fetchHandler
     */
    public void setFetchHandler(final FetchGetHandler fetchHandler) {
        _fetchHandler = fetchHandler;
    }


    /**
     * @param onPrevious
     */
    public void setOnPrevious(final Action onPrevious) { _onPrevious = onPrevious; }

    /**
     * @param onNext
     */
    public void setOnNext(final Action onNext) { _onNext = onNext; }


    /**
     * @param audioClip
     * @param duration
     */
    private void onAudioClipReady(final AudioClip audioClip, final Duration duration) {
        final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, audioClip.getArtistID());
        final String artistName = (artist == null) ? "" : artist.getName();

        _titleLabel.setText(audioClip.getName());
        _artistLabel.setText(artistName);
        _durationLabel.setText(Timer.durationToString(duration));
        _playbackPositionLabel.setText("0:00");
        _progressBar.setProgress(0);
        setDisable(false);
    }

    /**
     * @param playbackPosition
     * @param duration
     */
    private void onUpdate(final Duration playbackPosition, final Duration duration) {
        _playbackPositionLabel.setText(Timer.durationToString(playbackPosition));
        final double progress = playbackPosition.toMillis() / duration.toMillis();
        _progressBar.setProgress(progress);
    }


    /**
     * @param e
     */
    @FXML
    private void onPlayButtonClicked(ActionEvent e) {
       _audioClipPlayer.togglePlay();
    }

    /**
     * @param e
     */
    @FXML
    private void onPreviousButtonClicked(ActionEvent e) { _onPrevious.invoke(); }

    /**
     * @param e
     */
    @FXML
    private void onNextButtonClicked(ActionEvent e) { _onNext.invoke(); }


    /**
     * @param e
     */
    private void onProgressBarClicked(MouseEvent e) {
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        Bounds progressBarBounds = _progressBar.localToScreen(_progressBar.getBoundsInLocal());
        double rewindPosition = (mouseX - progressBarBounds.getMinX()) / (progressBarBounds.getMaxX() - progressBarBounds.getMinX());
        _audioClipPlayer.rewind(rewindPosition);
    }
}
