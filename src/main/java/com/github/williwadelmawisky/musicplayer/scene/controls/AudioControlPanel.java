package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioProperty;
import com.github.williwadelmawisky.musicplayer.database.ArtistData;
import com.github.williwadelmawisky.musicplayer.database.Database;
import com.github.williwadelmawisky.musicplayer.database.FetchGetHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 */
public class AudioControlPanel extends VBox {

    @FXML private Label _titleLabel;
    @FXML private Label _artistLabel;
    @FXML private AudioProgressView _audioProgressView;
    @FXML private AudioControlButtonView _audioControlButtonView;
    @FXML private VolumeSliderView _volumeSliderView;

    private EventHandler<ActionEvent> _onPrevious, _onNext;
    private AudioClipPlayer _audioClipPlayer;
    private FetchGetHandler _fetchHandler;


    /**
     *
     */
    public AudioControlPanel() {
        super();

        ResourceLoader.loadFxml("fxml/controls/AudioControlPanel.fxml", this);

        _titleLabel.setText("");
        _artistLabel.setText("");
    }

    /**
     * @param audioClipPlayer
     */
    public AudioControlPanel(final AudioClipPlayer audioClipPlayer) {
        this();
        setAudioClipPlayer(audioClipPlayer);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _audioProgressView.setProgressAndAudioProperty(_audioClipPlayer.getProgressProperty(), _audioClipPlayer.getAudioProperty());
        _volumeSliderView.setVolumeProperty(_audioClipPlayer.getVolumeProperty());
        _audioControlButtonView.setStatusProperty(_audioClipPlayer.getStatusProperty());
        _audioControlButtonView.setOnPrevious(_onPrevious);
        _audioControlButtonView.setOnNext(_onNext);

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
     * @param changeEvent
     */
    private void onAudioPropertyChanged(final AudioProperty.ChangeEvent changeEvent) {
        final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, changeEvent.AudioClip.getArtistID());
        final String artistName = (artistData == null) ? "" : artistData.getName();

        _titleLabel.setText(changeEvent.AudioClip.getName());
        _artistLabel.setText(artistName);
        setDisable(false);
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
    public void setOnPrevious(EventHandler<ActionEvent> onPrevious) { _onPrevious = onPrevious; }

    /**
     * @param onNext
     */
    public void setOnNext(EventHandler<ActionEvent> onNext) { _onNext = onNext; }
}
