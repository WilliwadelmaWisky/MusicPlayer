package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.*;
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

    @FXML private AudioClipListView _audioClipListView;
    @FXML private AudioControlPanel _audioControlPanel;

    private final File _homeDirectory;
    private AudioClipListPlayer _audioClipPlayer;
    private String _playlistName;


    /**
     *
     */
    public MainWindowController() {
        _playlistName = null;
        _homeDirectory = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toFile();
        if (!_homeDirectory.exists())
            _homeDirectory.mkdirs();
    }


    /**
     *
     */
    @FXML
    private void initialize() {
        _audioClipPlayer = new AudioClipListPlayer();

        _audioClipListView.bindTo(_audioClipPlayer);
        _audioControlPanel.bindTo(_audioClipPlayer);
    }


    /**
     * @param file
     */
    public void open(final File file) {
        final AudioClipLoader audioClipLoader = new AudioClipLoader(_audioClipPlayer);
        audioClipLoader.openUnknownFile(file);
        _playlistName = null;
    }


    /**
     * @param e
     */
    @FXML
    private void onNewPlaylistButtonClicked(final ActionEvent e) {
        _audioClipPlayer.getAudioClipList().clear();
        _playlistName = null;
    }

    /**
     * @param e
     */
    @FXML
    private void onSaveButtonClicked(final ActionEvent e) {
        if (_playlistName == null) {
            onSaveAsButtonClicked(e);
            return;
        }

        //final File saveFile = Paths.get(_homeDirectory.getAbsolutePath(), _playlistName + ".txt").toFile();
        //Files.write(saveFile, "");
    }

    /**
     * @param e
     */
    @FXML
    private void onSaveAsButtonClicked(final ActionEvent e) {
        final PlaylistEditor playlistEditor = new PlaylistEditor();
        playlistEditor.setTitle("Save a playlist");
        playlistEditor.setInitialDirectory(_homeDirectory);

        playlistEditor.showDialog();
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
        audioClipLoader.openAudioFile(file);
        _playlistName = null;
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
        _playlistName = null;
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
        _playlistName = playlist.getName();
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
    private void onQuitButtonClicked(final ActionEvent e) {

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
    private void onForwardButtonClicked(final ActionEvent e) {

    }

    /**
     * @param e
     */
    @FXML
    private void onBackwardButtonClicked(final ActionEvent e) {

    }

    /**
     * @param e
     */
    @FXML
    private void onNextButtonClicked(final ActionEvent e) {
        final AudioClipSelectorType audioClipSelectorType = _audioClipListView.getSelectorType();
        audioClipSelectorType.getValue().next(_audioClipPlayer.getSelectionModel());
    }

    /**
     * @param e
     */
    @FXML
    private void onPreviousButtonClicked(final ActionEvent e) {
        final AudioClipSelectorType audioClipSelectorType =  _audioClipListView.getSelectorType();
        audioClipSelectorType.getValue().previous(_audioClipPlayer.getSelectionModel());
    }


    /**
     * @param e
     */
    @FXML
    private void onIncreaseVolumeButtonClicked(final ActionEvent e) {
        final double incrementPercent = 5;
        final double volume = ((int)(_audioClipPlayer.getVolume() * 100 / incrementPercent) + 1) * incrementPercent / 100;
        _audioClipPlayer.setVolume(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onDecreaseVolumeButtonClicked(final ActionEvent e) {
        final double decrementPercent = 5;
        final double volume = ((int)(_audioClipPlayer.getVolume() * 100 / decrementPercent) - 1) * decrementPercent / 100;
        _audioClipPlayer.setVolume(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onMuteVolumeButtonClicked(final ActionEvent e) {
        final double volume = 0;
        _audioClipPlayer.setVolume(volume);
    }
}
