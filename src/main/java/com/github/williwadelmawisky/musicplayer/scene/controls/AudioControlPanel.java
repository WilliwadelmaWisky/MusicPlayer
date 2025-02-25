package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.core.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.core.audio.ProgressProperty;
import com.github.williwadelmawisky.musicplayer.core.database.ArtistData;
import com.github.williwadelmawisky.musicplayer.core.database.Database;
import com.github.williwadelmawisky.musicplayer.core.database.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.util.Action;
import com.github.williwadelmawisky.musicplayer.util.Strings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.MouseInfo;

/**
 *
 */
public class AudioControlPanel extends VBox {

    @FXML private Label _titleLabel;
    @FXML private Label _artistLabel;
    @FXML private AudioProgressView _audioProgressView;
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
        setDisable(true);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _audioProgressView.setProgressProperty(_audioClipPlayer.getProgressProperty(), _audioClipPlayer.getAudioProperty());
        _volumeSliderView.setVolumeProperty(_audioClipPlayer.getVolumeProperty());
        _playButton.setStatusProperty(_audioClipPlayer.getStatusProperty());

        _audioClipPlayer.getAudioProperty().getUpdateEvent().addListener(this::onAudioPropertyChanged);
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
    private void onAudioPropertyChanged(AudioProperty.ChangeEvent changeEvent) {
        final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, changeEvent.AudioClip.getArtistID());
        final String artistName = (artistData == null) ? "" : artistData.getName();

        _titleLabel.setText(changeEvent.AudioClip.getName());
        _artistLabel.setText(artistName);
        setDisable(false);
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
}
