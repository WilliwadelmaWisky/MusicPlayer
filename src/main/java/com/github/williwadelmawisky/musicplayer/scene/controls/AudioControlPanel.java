package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.core.Timer;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.core.audio.ProgressProperty;
import com.github.williwadelmawisky.musicplayer.core.database.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.database.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.core.database.URL;
import com.github.williwadelmawisky.musicplayer.util.event.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    @FXML private VolumeSliderView _volumeSliderView;
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

        _volumeSliderView.setVolumeProperty(_audioClipPlayer.getVolumeProperty());
        _playButton.setStatusProperty(_audioClipPlayer.getStatusProperty());

        _audioClipPlayer.getAudioProperty().getUpdateEvent().addListener(this::onAudioProprtyChanged);
        _audioClipPlayer.getProgressProperty().getUpdateEvent().addListener(this::onProgressPropertyChanged);
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
     * @param changeEvent
     */
    private void onAudioProprtyChanged(AudioProperty.ChangeEvent changeEvent) {
        final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, changeEvent.AudioClip.getArtistID());
        final String artistName = (artist == null) ? "" : artist.getName();

        _titleLabel.setText(changeEvent.AudioClip.getName());
        _artistLabel.setText(artistName);
        _durationLabel.setText(Timer.durationToString(changeEvent.Duration));
        _playbackPositionLabel.setText("0:00");
        _progressBar.setProgress(0);
        setDisable(false);
    }

    /**
     * @param changeEvent
     */
    private void onProgressPropertyChanged(ProgressProperty.ChangeEvent changeEvent) {
        _progressBar.setProgress(changeEvent.Progress);
        final Duration playbackPosition = _audioClipPlayer.getAudioProperty().getDuration().multiply(changeEvent.Progress);
        _playbackPositionLabel.setText(Timer.durationToString(playbackPosition));
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
