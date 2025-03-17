package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.utils.Files;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 */
public class MainWindowController {

    @FXML private AudioListView _audioListView;
    @FXML private AudioControlPanel _audioControlPanel;

    private AudioClipListPlayer _audioClipPlayer;
    private File _homeDirectory;


    /**
     *
     */
    @FXML
    private void initialize() {
        _audioClipPlayer = new AudioClipListPlayer();
        _homeDirectory = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toFile();

        _audioListView.bindTo(_audioClipPlayer);
        _audioControlPanel.bindTo(_audioClipPlayer);
    }


    /**
     * @param e
     */
    @FXML
    private void onNewPlaylistButtonClicked(ActionEvent e) {
        _audioClipPlayer.getAudioClipList().clear();
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenFileButtonClicked(final ActionEvent e) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a media file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files (.mp3)", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (.wav)", "*.wav"));

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

        final File file = fileChooser.showOpenDialog(_audioControlPanel.getScene().getWindow());
        if (file == null || !file.exists())
            return;

        final AudioClipLoader audioClipLoader = new AudioClipLoader(_audioClipPlayer);
        audioClipLoader.openFile(file);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenDirectoryButtonClicked(final ActionEvent e) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open a media directory");

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initialDirectory);

        final File directory = directoryChooser.showDialog(_audioControlPanel.getScene().getWindow());
        if (directory == null || !directory.exists() || !directory.isDirectory())
            return;

        final AudioClipLoader audioClipLoader = new AudioClipLoader(_audioClipPlayer);
        audioClipLoader.openDirectory(directory);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenPlaylistButtonClicked(final ActionEvent e) {
        final PlaylistChooser playlistChooser = new PlaylistChooser();
        playlistChooser.setTitle("Open a playlist");

        final AudioPlaylist playlist = playlistChooser.showDialog();
        if (playlist == null)
            return;

        final AudioClipLoader audioClipLoader = new AudioClipLoader(_audioClipPlayer);
        audioClipLoader.openPlaylist(playlist);
    }

    /**
     * @param e
     */
    @FXML
    private void onImportPlaylistClicked(final ActionEvent e) {

    }

    /**
     * @param e
     */
    @FXML
    private void onExportPlaylistClicked(final ActionEvent e) {

    }


    /**
     * @param e
     */
    @FXML
    private void onPlayButtonClicked(final ActionEvent e) {
        _audioClipPlayer.setAudioStatus(AudioStatus.PLAYING);
    }

    /**
     * @param e
     */
    @FXML
    private void onPauseButtonClicked(final ActionEvent e) {
        _audioClipPlayer.setAudioStatus(AudioStatus.PAUSED);
    }

    /**
     * @param e
     */
    @FXML
    private void onStopButtonClicked(final ActionEvent e) {
        _audioClipPlayer.setAudioStatus(AudioStatus.STOPPED);
    }

    /**
     * @param e
     */
    @FXML
    private void onNextSongButtonClicked(ActionEvent e) {
        final AudioClipSelectorType audioClipSelectorType = AudioClipSelectorType.valueOf(_audioClipSelectorTypeComboBox.getValue());
        audioClipSelectorType.getValue().next(_audioClipPlayer.getSelectionModel());
    }

    /**
     * @param e
     */
    @FXML
    private void onPreviousSongButtonClicked(ActionEvent e) {
        final AudioClipSelectorType audioClipSelectorType = AudioClipSelectorType.valueOf(_audioClipSelectorTypeComboBox.getValue());
        audioClipSelectorType.getValue().previous(_audioClipPlayer.getSelectionModel());
    }


    /**
     * @param e
     */
    @FXML
    private void onIncreaseVolumeButtonClicked(ActionEvent e) {
        final double incrementPercent = 5;
        final double volume = ((int)(_audioClipPlayer.getVolumeProperty().getValue() * 100 / incrementPercent) + 1) * incrementPercent / 100;
        _audioClipPlayer.getVolumeProperty().setValue(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onDecreaseVolumeButtonClicked(ActionEvent e) {
        final double decrementPercent = 5;
        final double volume = ((int)(_audioClipPlayer.getVolumeProperty().getValue() * 100 / decrementPercent) - 1) * decrementPercent / 100;
        _audioClipPlayer.getVolumeProperty().setValue(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onMuteVolumeButtonClicked(ActionEvent e) {
        final double volume = 0;
        _audioClipPlayer.getVolumeProperty().setValue(volume);
    }
}
