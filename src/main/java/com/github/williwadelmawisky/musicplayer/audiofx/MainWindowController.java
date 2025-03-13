package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.utils.Files;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 */
public class MainWindowController {

    @FXML private AudioControlPanel _audioControlPanel;
    @FXML private VBox _viewVBox;

    private AudioClipListPlayer _audioClipPlayer;
    private File _homeDirectory;


    /**
     *
     */
    @FXML
    private void initialize() {
        _audioClipPlayer = new AudioClipListPlayer();
        _homeDirectory = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toFile();

        _audioControlPanel.bindTo(_audioClipPlayer);
    }


    /**
     * @param controlPanel
     */
    public void setView(final ControlPanel controlPanel) {
        controlPanel.bindTo();
        if (_viewVBox.getChildren().getFirst() instanceof ControlPanel cp) {
            _viewVBox.getChildren().removeFirst();
            cp.unbind();
        }

        final Pane controlPanelRoot = (Pane) controlPanel;
        VBox.setVgrow(controlPanelRoot, Priority.ALWAYS);
        controlPanelRoot.setMaxHeight(Double.POSITIVE_INFINITY);
        _viewVBox.getChildren().add(controlPanelRoot);
    }


    /**
     * @param audioPlaylist
     */
    public void open(final AudioPlaylist audioPlaylist) {
        _audioClipPlayer.getAudioClipList().clear();
        audioPlaylist.forEach(audioFile -> {
            final AudioClip audioClip = new AudioClip(audioFile);
            _audioClipPlayer.getAudioClipList().add(audioClip);
        });

        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param file
     */
    public void open(final File file) {
        if (file.isDirectory()) {
            openDirectory(file);
            return;
        }

        final String[] extensions = new String[] { ".mp3", ".wav" };
        if (Files.doesMatchExtension(file, extensions))
            openFile(file);
    }

    /**
     * @param file
     */
    private void openFile(final File file) {
        _audioClipPlayer.getAudioClipList().clear();
        addAudioFile(file);
        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param directory
     */
    private void openDirectory(final File directory) {
        _audioClipPlayer.getAudioClipList().clear();
        addDirectory(directory);
        _audioClipPlayer.getSelectionModel().clearAndSelect(0);
    }

    /**
     * @param file
     */
    private void addUnknownFile(final File file) {
        if (file.isDirectory()) {
            addDirectory(file);
            return;
        }

        final String[] extensions = new String[] { ".mp3", ".wav" };
        if (Files.doesMatchExtension(file, extensions))
            addAudioFile(file);
    }

    /**
     * @param file
     */
    private void addAudioFile(final File file) {
        final AudioFile audioFile = new AudioFile(file);
        final AudioClip audioClip = new AudioClip(audioFile);
        _audioClipPlayer.getAudioClipList().add(audioClip);
    }

    /**
     * @param directory
     */
    private void addDirectory(final File directory) {
        final String[] extensions = new String[] { ".mp3", ".wav" };
        Files.listFiles(directory, extensions, true, this::addAudioFile);
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

        openFile(file);
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

        openDirectory(directory);
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

        open(playlist);
    }


    /**
     * @param e
     */
    @FXML
    private void onPlayButtonClicked(ActionEvent e) {
        final AudioStatus audioStatus = _audioClipPlayer.getAudioStatus() == AudioStatus.PLAYING ? AudioStatus.PAUSED : AudioStatus.PLAYING;
        _audioClipPlayer.setAudioStatus(audioStatus);
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
