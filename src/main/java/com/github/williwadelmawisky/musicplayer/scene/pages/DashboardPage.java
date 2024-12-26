package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.control.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.core.control.audio.AudioSequencePlayer;
import com.github.williwadelmawisky.musicplayer.core.control.audio.Sequencer;
import com.github.williwadelmawisky.musicplayer.core.data.AudioFile;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import com.github.williwadelmawisky.musicplayer.core.db.FetchHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.Router;
import com.github.williwadelmawisky.musicplayer.scene.controls.AudioControlPanel;
import com.github.williwadelmawisky.musicplayer.stage.ModalWindow;
import com.github.williwadelmawisky.musicplayer.stage.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 *
 */
public class DashboardPage extends VBox implements Page {

    @FXML private Label _playlistLabel;
    @FXML private AudioControlPanel _audioControlPanel;

    private FetchHandler _fetchHandler;
    private AudioSequencePlayer _audioSequencePlayer;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public DashboardPage() {}

    /**
     * @param sequencer
     * @param fetchHandler
     */
    public DashboardPage(final Sequencer<AudioClip> sequencer, final FetchHandler fetchHandler) {
        super();

        ResourceLoader.loadFxml("fxml/pages/DashboardPage.fxml", this);

        _audioSequencePlayer = new AudioSequencePlayer(sequencer);
        _fetchHandler = fetchHandler;
        _playlistLabel.setText("");

        _audioControlPanel.setAudioClipPlayer(_audioSequencePlayer.getAudioClipPlayer());
        _audioControlPanel.setFetchHandler(_fetchHandler);
        _audioControlPanel.setOnPrevious(this::onPrevious);
        _audioControlPanel.setOnNext(this::onNext);
    }


    /**
     *
     */
    private void onPrevious() {
        _audioSequencePlayer.previous();
    }

    /**
     *
     */
    private void onNext() {
       _audioSequencePlayer.next();
    }


    /**
     * @param playlist
     */
    public void loadPlaylist(final Playlist playlist) {
        _audioSequencePlayer.clear();

        for (UUID songID : playlist) {
            final Song song = _fetchHandler.fetchGET(URL.SONG, songID);
            final AudioFile audioFile = _fetchHandler.fetchGET(URL.AUDIO_FILE, songID);
            final AudioClip audioClip = new AudioClip(song, audioFile);
            _audioSequencePlayer.add(audioClip);
        }
    }

    /**
     * @param file
     */
    public void loadFile(final File file) {
        _audioSequencePlayer.clear();

        final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
        _audioSequencePlayer.add(audioClip);
    }

    /**
     * @param directory
     */
    public void loadDirectory(final File directory) {
        _audioSequencePlayer.clear();

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
            _audioSequencePlayer.add(audioClip);
        }
    }


    /**
     * @param e
     */
    @FXML
    private void onNewPlaylistButtonClicked(ActionEvent e) {
        final Playlist playlist = new Playlist(UUID.randomUUID(), "");
        final Page page =  new PlaylistEditPage(playlist, () -> loadPlaylist(playlist));
        final ModalWindow window = new ModalWindow("Create a new playlist", page);
        window.show();
    }


    /**
     * @param e
     */
    @FXML
    private void onOpenFileButtonClicked(ActionEvent e) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a media file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".wav"));

        final File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (file == null || !file.exists())
            return;

        loadFile(file);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenDirectoryButtonClicked(ActionEvent e) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open a media directory");

        final File directory = directoryChooser.showDialog(this.getScene().getWindow());
        if (directory == null || !directory.exists() || !directory.isDirectory())
            return;

        loadDirectory(directory);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenPlaylistButtonClicked(ActionEvent e) {
        final Page page = new PlaylistOpenPage(_fetchHandler, this::loadPlaylist);
        final ModalWindow window = new ModalWindow("Open a playlist", page);
        window.show();
    }


    /**
     * @param e
     */
    @FXML
    private void onListButtonClicked(ActionEvent e) {
        final Stage stage = new Stage();
        final Window window = new Window(stage, Router.singlePageRouter(new PlaylistSongPage()));
        window.show();
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
