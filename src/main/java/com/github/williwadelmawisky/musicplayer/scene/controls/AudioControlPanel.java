package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.control.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.core.control.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.core.control.Timer;
import com.github.williwadelmawisky.musicplayer.core.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.db.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.util.Action;
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

    private static final String PLAY_ICON = "img/play_icon.png";
    private static final String PAUSE_ICON = "img/pause_icon.png";

    @FXML private Label _playbackPositionLabel;
    @FXML private Label _durationLabel;
    @FXML private Label _titleLabel;
    @FXML private Label _artistLabel;
    @FXML private Label _volumeLabel;
    @FXML private ProgressBar _progressBar;
    @FXML private Slider _volumeSlider;
    @FXML private ImageView _statusImageView;

    private AudioClipPlayer _audioClipPlayer;
    private FetchGetHandler _fetchHandler;
    private Action _onPrevious, _onNext;


    /**
     *
     */
    public AudioControlPanel() {
        super();

        ResourceLoader.loadFxml("fxml/controls/AudioControlPanel.fxml", this);

        updatePlayIcon(false);
        _titleLabel.setText("");
        _artistLabel.setText("");
        _playbackPositionLabel.setText("0:00");
        _durationLabel.setText("0:00");
        _progressBar.setProgress(0);
        setDisable(true);

        _progressBar.setOnMouseClicked(this::onProgressBarClicked);
        _volumeSlider.valueProperty().addListener(this::onVolumeSliderChanged);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        updateVolume();

        _audioClipPlayer.setOnUpdate(this::onUpdate);
        _audioClipPlayer.setOnAudioClipReady(this::onAudioClipReady);
        _audioClipPlayer.setOnStatusChanged(this::onStatusChanged);
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
     * @param isPlaying
     */
    private void onStatusChanged(final boolean isPlaying) {
        updatePlayIcon(isPlaying);
    }


    /**
     * @param isPlaying
     */
    private void updatePlayIcon(boolean isPlaying) {
        String iconPath = isPlaying ? PAUSE_ICON : PLAY_ICON;
        _statusImageView.setImage(ResourceLoader.loadImage(iconPath));
    }

    /**
     * @param volume
     */
    private void updateVolumeLabel(final double volume) {
        _volumeLabel.setText((int)(volume * 100) + "%");
    }

    /**
     *
     */
    public void updateVolume() {
        _volumeSlider.setValue(_audioClipPlayer.getVolume());
        updateVolumeLabel(_audioClipPlayer.getVolume());
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

    /**
     * @param newValue
     */
    private void onVolumeSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        final double volume = newValue.doubleValue();
        _audioClipPlayer.setVolume(volume);
        updateVolumeLabel(volume);
    }
}
