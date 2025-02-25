package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.database.Database;
import com.github.williwadelmawisky.musicplayer.core.database.PlaylistData;
import com.github.williwadelmawisky.musicplayer.core.database.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.RedirectHandler;
import com.github.williwadelmawisky.musicplayer.routing.SessionStorage;
import com.github.williwadelmawisky.musicplayer.scene.controls.SongNode;
import com.github.williwadelmawisky.musicplayer.util.Files;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class EditPlaylistPage extends VBox implements Page {

    @FXML private TextField _nameTextField;
    @FXML private ListView<SongNode> _songListView;

    private FetchGetHandler _fetchHandler;
    private RedirectHandler _redirectHandler;
    private SessionStorage _sessionStorage;
    private PlaylistData _playlistData;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public EditPlaylistPage() {}

    /**
     * @param fetchHandler
     * @param redirectHandler
     */
    public EditPlaylistPage(final FetchGetHandler fetchHandler, final RedirectHandler redirectHandler, final SessionStorage sessionStorage) {
        super();

        ResourceLoader.loadFxml("fxml/pages/EditPlaylistPage.fxml", this);

        _fetchHandler = fetchHandler;
        _redirectHandler = redirectHandler;
        _sessionStorage = sessionStorage;

        _songListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }

    /**
     *
     */
    @Override
    public void onLoad() {
        if (_sessionStorage.hasKey("playlist")) {
            final String playlistID = _sessionStorage.get("playlist");
            _playlistData = _fetchHandler.fetchGET(Database.TableType.PLAYLIST, UUID.fromString(playlistID));
        } else {
            _playlistData = new PlaylistData(UUID.randomUUID(), "New Playlist");
        }

        generate();
    }

    /**
     *
     */
    @Override
    public void onUnload() {

    }


    /**
     *
     */
    private void generate() {
        final List<SongNode> list = new ArrayList<>();
        _playlistData.forEach(songID -> {
            final SongNode songNode = new SongNode(songID, "Song", "Artist");
            list.add(songNode);
        });

        final ObservableList<SongNode> items = FXCollections.observableList(list);
        _songListView.setItems(items);
    }


    /**
     * @param e
     */
    @FXML
    private void onDashboardPageButtonClicked(ActionEvent e) {
        _redirectHandler.setRoute("/");
    }

    /**
     * @param e
     */
    @FXML
    private void onLibraryPageButtonClicked(ActionEvent e) {
        _redirectHandler.setRoute("/library");
    }


    /**
     * @param e
     */
    @FXML
    private void onAddFileButtonClicked(ActionEvent e) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a media file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files (.mp3)", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (.wav)", "*.wav"));

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

        final File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (file == null || !file.exists())
            return;

        final SongNode songNode = new SongNode(UUID.randomUUID(), file.getName(), "");
        _songListView.getItems().add(songNode);
    }

    /**
     * @param e
     */
    @FXML
    private void onAddDirectoryButtonClicked(ActionEvent e) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open a media directory");

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initialDirectory);

        final File directory = directoryChooser.showDialog(this.getScene().getWindow());
        if (directory == null || !directory.exists() || !directory.isDirectory())
            return;

        final String[] extensions = new String[] { ".mp3", ".wav" };
        Files.listFiles(directory, extensions, true, file -> {
            final SongNode songNode = new SongNode(UUID.randomUUID(), file.getName(), "");
            _songListView.getItems().add(songNode);
        });
    }

    /**
     * @param e
     */
    @FXML
    private void onAddSongButtonClicked(ActionEvent e) {
        final SongNode songNode = new SongNode(UUID.randomUUID(), "Song", "");
        _songListView.getItems().add(songNode);
    }

    /**
     * @param e
     */
    @FXML
    private void onRemoveButtonClicked(ActionEvent e) {
        for (int index : _songListView.getSelectionModel().getSelectedIndices()) {
            _songListView.getItems().remove(index);
        }
    }


    /**
     * @param e
     */
    @FXML
    private void onApplyButtonClicked(ActionEvent e) {
        final String name = _nameTextField.getText().trim();
        if (name.isEmpty())
            return;

        final PlaylistData playlistData = new PlaylistData(UUID.randomUUID(), name);
        //_onApply.invoke(playlist);
    }
}
