package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.*;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.SelectionModel;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

/**
 *
 */
public class MainWindowController {

    private static final File HOME_DIRECTORY = Paths.get(System.getProperty("user.home")).toFile();
    private static final File MUSIC_DIRECTORY = Paths.get(HOME_DIRECTORY.getAbsolutePath(), "Music").toFile();
    private static final File CONFIG_DIRECTORY = Paths.get(HOME_DIRECTORY.getAbsolutePath(), ".config", "WilliwadelmaWisky", "MusicPlayer").toFile();
    private static final File PLAYLIST_SAVE_DIRECTORY = Paths.get(CONFIG_DIRECTORY.getAbsolutePath(), "playlists").toFile();
    private static final File PREFERENCES_SAVE_DIRECTORY = Paths.get(CONFIG_DIRECTORY.getAbsolutePath(), "preferences").toFile();
    private static final File AUDIODATA_SAVE_DIRECTORY = Paths.get(CONFIG_DIRECTORY.getAbsolutePath(), "audiodata").toFile();

    private static final Duration SKIP_FORWARD_AMOUNT = Duration.seconds(10);
    private static final Duration SKIP_BACKWARD_AMOUNT = Duration.seconds(10);
    private static final byte INCREASE_VOLUME_AMOUNT = 5;
    private static final byte DECRAESE_VOLUME_AMOUNT = 5;

    @FXML private Menu _playbackMenu;
    @FXML private MenuItem _playMenuItem;
    @FXML private AudioClipListView _audioClipListView;
    @FXML private AudioClipControlPanel _audioClipControlPanel;

    private AudioClipPlayer _audioClipPlayer;
    private SaveManager _saveManager;
    private String _name;


    /**
     * @param audioClipPlayer
     */
    void onCreated(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        _saveManager = new SaveManager();

        _audioClipPlayer.OnPlay.addListener(this::onPlay_AudioClipPlayer);
        _audioClipPlayer.OnPause.addListener(this::onPause_AudioClipPlayer);
        _audioClipPlayer.OnStop.addListener(this::onStop_AudioClipPlayer);
        _audioClipPlayer.AudioClipList.OnChanged.addListener(this::onChanged_AudioClipList);
        _audioClipPlayer.SelectionModel.OnSelected.addListener(this::onSelected_SelectionModel);
        _audioClipPlayer.SelectionModel.OnCleared.addListener(this::onCleared_SelectionModel);

        _audioClipListView.setAudioClipPlayer(_audioClipPlayer);
        _audioClipControlPanel.setOnPrevious(this::onPrevious_AudioControlPanel);
        _audioClipControlPanel.setOnNext(this::onNext_AudioControlPanel);
    }

    /**
     *
     */
    @FXML
    private void initialize() {

    }


    /**
     * @param e
     */
    @FXML
    private void onNewPlaylistButtonClicked(final ActionEvent e) {
        _audioClipPlayer.clear();
    }

    /**
     * @param e
     */
    @FXML
    private void onSaveButtonClicked(final ActionEvent e) {
        if (_name == null) {
            onSaveAsButtonClicked(e);
            return;
        }

        final File saveFile = Paths.get(CONFIG_DIRECTORY.getAbsolutePath(), _name + ".json").toFile();
        savePlaylist(saveFile);
    }

    /**
     * @param e
     */
    @FXML
    private void onSaveAsButtonClicked(final ActionEvent e) {
        final TextInputDialog textInputDialog = new TextInputDialog("new playlist");
        textInputDialog.setTitle("Save the playlist");
        textInputDialog.setHeaderText(null);
        textInputDialog.setGraphic(null);

        final Optional<String> result = textInputDialog.showAndWait();
        if (result.isEmpty())
            return;

        final String playlistName = result.get().trim();
        if (playlistName.isEmpty())
            return;

        final File saveFile = Paths.get(CONFIG_DIRECTORY.getAbsolutePath(), playlistName + ".json").toFile();
        savePlaylist(saveFile);
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

        final File initialDirectory = MUSIC_DIRECTORY;
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

        final File file = fileChooser.showOpenDialog(_audioClipControlPanel.getScene().getWindow());
        if (file == null || !file.exists())
            return;

        load(file);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenDirectoryButtonClicked(final ActionEvent e) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open a media directory");

        final File initialDirectory = MUSIC_DIRECTORY;
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initialDirectory);

        final File directory = directoryChooser.showDialog(_audioClipControlPanel.getScene().getWindow());
        if (directory == null || !directory.exists() || !directory.isDirectory())
            return;

        load(directory);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenPlaylistButtonClicked(final ActionEvent e) {
        final PlaylistChooser playlistChooser = new PlaylistChooser();
        playlistChooser.setTitle("Open a playlist");

        final PlaylistInfo playlist = playlistChooser.showDialog();
        if (playlist == null)
            return;

        _audioClipPlayer.load(playlist);
    }

    /**
     * @param e
     */
    @FXML
    private void onImportPlaylistClicked(final ActionEvent e) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import a playlist from a json file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (.json)", "*.json"));

        final File initialDirectory = HOME_DIRECTORY;
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

        final File file = fileChooser.showOpenDialog(_audioClipControlPanel.getScene().getWindow());
        if (file != null && file.exists())
            loadPlaylist(file);
    }

    /**
     * @param e
     */
    @FXML
    private void onExportPlaylistClicked(final ActionEvent e) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export a playlist as a json file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (.json)", "*.json"));

        final File initialDirectory = HOME_DIRECTORY;
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

        final File file = fileChooser.showSaveDialog(_audioClipControlPanel.getScene().getWindow());
        if (file != null && file.exists())
            savePlaylist(file);
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
    private void onStopButtonClicked(final ActionEvent e) {
        _audioClipPlayer.stop();
    }

    /**
     * @param e
     */
    @FXML
    private void onSkipForwardButtonClicked(final ActionEvent e) { _audioClipPlayer.skipForward(SKIP_FORWARD_AMOUNT); }

    /**
     * @param e
     */
    @FXML
    private void onSkipBackwardButtonClicked(final ActionEvent e) { _audioClipPlayer.skipBackward(SKIP_BACKWARD_AMOUNT); }

    /**
     * @param e
     */
    @FXML
    private void onNextButtonClicked(final ActionEvent e) { _audioClipPlayer.next(); }

    /**
     * @param e
     */
    @FXML
    private void onPreviousButtonClicked(final ActionEvent e) { _audioClipPlayer.previous(); }


    /**
     * @param e
     */
    @FXML
    private void onIncreaseVolumeButtonClicked(final ActionEvent e) {
        final double volume = ((int)(_audioClipPlayer.getVolume() * 100.0 / INCREASE_VOLUME_AMOUNT) + 1) * INCREASE_VOLUME_AMOUNT / 100.0;
        _audioClipPlayer.setVolume(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onDecreaseVolumeButtonClicked(final ActionEvent e) {
        final double volume = ((int)(_audioClipPlayer.getVolume() * 100.0 / DECRAESE_VOLUME_AMOUNT) - 1) * DECRAESE_VOLUME_AMOUNT / 100.0;
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


    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        _playMenuItem.setText("Pause");
        _playMenuItem.setOnAction(e -> _audioClipPlayer.pause());
        _playMenuItem.setDisable(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        _playMenuItem.setText("Play");
        _playMenuItem.setOnAction(e -> _audioClipPlayer.play());
        _playMenuItem.setDisable(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        _playMenuItem.setText("Play");
        _playMenuItem.setOnAction(e -> {});
        _playMenuItem.setDisable(true);
    }

    /**
     * @param sender
     * @param args
     */
    private void onChanged_AudioClipList(final Object sender, final EventArgs args) {
        _playbackMenu.setDisable(_audioClipPlayer.AudioClipList.isEmpty());
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        _audioClipControlPanel.clear();
        _audioClipControlPanel.setAudioClip(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_SelectionModel(final Object sender, final EventArgs args) {
        _audioClipControlPanel.clear();
    }


    /**
     * @param e
     */
    private void onPrevious_AudioControlPanel(final ActionEvent e) { _audioClipPlayer.previous(); }

    /**
     * @param e
     */
    private void onNext_AudioControlPanel(final ActionEvent e) { _audioClipPlayer.next(); }


    /**
     * @param file
     */
    boolean load(final File file) {
        _name = null;

        _audioClipPlayer.clear();
        final FileReader fileReader = new FileReader(file);
        fileReader.read(_audioClipPlayer::add);
        return !_audioClipPlayer.isEmpty();
    }

    
    /**
     * @param file
     * @return
     */
    private boolean savePlaylist(final File file) {
        final PlaylistInfo playlistInfo = new PlaylistInfo(_audioClipPlayer.AudioClipList);
        return _saveManager.save(playlistInfo, file);
    }

    /**
     * @param file
     * @return
     */
    private boolean loadPlaylist(final File file) {
        final PlaylistInfo playlistInfo = new PlaylistInfo();
        boolean success = _saveManager.load(playlistInfo, file);
        if (!success)
            return false;

        _audioClipPlayer.load(playlistInfo);
        return true;
    }
}
