package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.database.*;
import com.github.williwadelmawisky.musicplayer.database.SongData;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.RedirectHandler;
import com.github.williwadelmawisky.musicplayer.scene.controls.AudioControlPanel;
import com.github.williwadelmawisky.musicplayer.scene.controls.SearchBar;
import com.github.williwadelmawisky.musicplayer.scene.controls.AudioSequencerSelector;
import com.github.williwadelmawisky.musicplayer.scene.controls.SongNode;
import com.github.williwadelmawisky.musicplayer.stage.ModalWindow;
import com.github.williwadelmawisky.musicplayer.util.Files;
import com.github.williwadelmawisky.musicplayer.util.Lists;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

/**
 *
 */
public class DashboardPage extends VBox implements Page {

    @FXML private MenuItem _playMenuItem;
    @FXML private ListView<SongNode> _songListView;
    @FXML private AudioControlPanel _audioControlPanel;
    @FXML private AudioSequencerSelector _audioSequencerSelector;

    private FetchHandler _fetchHandler;
    private RedirectHandler _redirectHandler;
    private AudioSequencePlayer _audioSequencePlayer;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public DashboardPage() {}

    /**
     * @param fetchHandler
     * @param redirectHandler
     * @param audioSequencePlayer
     */
    public DashboardPage(final FetchHandler fetchHandler, final RedirectHandler redirectHandler, final AudioSequencePlayer audioSequencePlayer) {
        super();

        ResourceLoader.loadFxml("fxml/pages/DashboardPage.fxml", this);

        _audioSequencePlayer = audioSequencePlayer;
        _fetchHandler = fetchHandler;
        _redirectHandler = redirectHandler;

        _audioSequencePlayer.setSequencer(_audioSequencerSelector.getCurrentSequencer());
        _audioSequencePlayer.setOnSelected(this::onSongSelected);
        _audioSequencePlayer.getStatusProperty().getUpdateEvent().addListener(this::onPlayStatusChanged);
        onPlayStatusChanged(_audioSequencePlayer.getStatusProperty().getValue());

        _songListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        _songListView.setOnMouseClicked(this::onListViewClicked);
        _songListView.setOnDragOver(this::onListViewDragOver);
        _songListView.setOnDragDropped(this::onListViewDragDrop);

        _audioControlPanel.setAudioClipPlayer(_audioSequencePlayer);
        _audioControlPanel.setFetchHandler(_fetchHandler);

        _audioControlPanel.setDisable(true);
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() { return this; }

    /**
     *
     */
    @Override
    public void onLoad() { }

    /**
     *
     */
    @Override
    public void onUnload() { }


    /**
     * @param playlistData
     */
    public void open(final PlaylistData playlistData) {
        _audioSequencePlayer.clear();
        _songListView.getItems().clear();

        for (UUID songID : playlistData) {
            final SongData songData = _fetchHandler.fetchGET(Database.TableType.SONG, songID);
            final FileData fileData = _fetchHandler.fetchGET(Database.TableType.FILE, songID);
            final AudioClip audioClip = new AudioClip(fileData.getID(), songData.getName(), songData.getGenre(), fileData.getAbsolutePath(), songData.getArtistID());
            _audioSequencePlayer.add(audioClip);
            addSongNode(audioClip);
        }

        _audioSequencePlayer.selectFirst();
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
        _audioSequencePlayer.clear();
        _songListView.getItems().clear();
        addFile(file);
        _audioSequencePlayer.selectFirst();
    }

    /**
     * @param directory
     */
    private void openDirectory(final File directory) {
        _audioSequencePlayer.clear();
        _songListView.getItems().clear();
        addDirectory(directory);
        _audioSequencePlayer.selectFirst();
    }

    /**
     * @param file
     */
    private void add(final File file) {
        if (file.isDirectory()) {
            addDirectory(file);
            return;
        }

        final String[] extensions = new String[] { ".mp3", ".wav" };
        if (Files.doesMatchExtension(file, extensions))
            addFile(file);
    }

    /**
     * @param file
     */
    private void addFile(final File file) {
        final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
        _audioSequencePlayer.add(audioClip);
        addSongNode(audioClip);
    }

    /**
     * @param directory
     */
    private void addDirectory(final File directory) {
        final String[] extensions = new String[] { ".mp3", ".wav" };
        Files.listFiles(directory, extensions, true, this::addFile);
    }


    /**
     *
     */
    public void shuffle() {
        _audioSequencePlayer.shuffle();
        refresh();
    }

    /**
     * @param searchString
     */
    private void search(final String searchString) {
        _songListView.getItems().clear();

        for (AudioClip audioClip : _audioSequencePlayer) {
            final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, audioClip.getArtistID());
            final String artistName = (artistData != null) ? artistData.getName() : "";
            final boolean matchName = audioClip.getName().toLowerCase().contains(searchString);
            final boolean matchArtist = artistName.toLowerCase().contains(searchString);

            if (matchName || matchArtist) {
                final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName);
                _songListView.getItems().add(songNode);
            }
        }
    }

    /**
     *
     */
    private void refresh() {
        _songListView.getItems().clear();

        for (AudioClip audioClip : _audioSequencePlayer) {
            addSongNode(audioClip);
        }
    }

    /**
     * @param audioClip
     */
    private void addSongNode(final AudioClip audioClip) {
        final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, audioClip.getArtistID());
        final String artistName = (artistData != null) ? artistData.getName() : "";
        final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName);
        _songListView.getItems().add(songNode);
    }


    /**
     * @param audioClip
     */
    private void onSongSelected(final AudioClip audioClip) {
        int index = Lists.indexFunc(_songListView.getItems(), songNode -> audioClip.equalsID(songNode.getSongID()));
        if (index == -1)
            return;

        _songListView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param e
     */
    private void onListViewClicked(MouseEvent e) {
        final SongNode songNode = _songListView.getSelectionModel().getSelectedItem();
        if (songNode == null)
            return;

        _audioSequencePlayer.select(songNode.getSongID());
    }

    /**
     * @param e
     */
    private void onListViewDragOver(DragEvent e) {
        if (!e.getDragboard().hasFiles())
            return;

        e.acceptTransferModes(TransferMode.ANY);
    }

    /**
     * @param e
     */
    private void onListViewDragDrop(DragEvent e) {
        final List<File> fileList = e.getDragboard().getFiles();
        fileList.forEach(this::add);
    }


    /**
     * @param isPlaying
     */
    private void onPlayStatusChanged(final boolean isPlaying) {
        final String text = isPlaying ? "Pause" : "Play";
        _playMenuItem.setText(text);
    }


    /**
     * @param e
     */
    @FXML
    private void onNewPlaylistButtonClicked(ActionEvent e) {
        _audioSequencePlayer.clear();
        _songListView.getItems().clear();
    }


    /**
     * @param e
     */
    @FXML
    private void onOpenFileButtonClicked(ActionEvent e) {
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

        openFile(file);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenDirectoryButtonClicked(ActionEvent e) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open a media directory");

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initialDirectory);

        final File directory = directoryChooser.showDialog(this.getScene().getWindow());
        if (directory == null || !directory.exists() || !directory.isDirectory())
            return;

        openDirectory(directory);
    }

    /**
     * @param e
     */
    @FXML
    private void onOpenPlaylistButtonClicked(ActionEvent e) {
        final Page page = new PlaylistOpenPage(_fetchHandler, this::open);
        final ModalWindow window = new ModalWindow("Open a playlist", page);
        window.show();
    }


    /**
     * @param e
     */
    @FXML
    private void onPlayButtonClicked(ActionEvent e) {
        _audioSequencePlayer.togglePlay();
    }

    /**
     * @param e
     */
    @FXML
    private void onNextSongButtonClicked(ActionEvent e) {
        _audioSequencePlayer.next();
    }

    /**
     * @param e
     */
    @FXML
    private void onPreviousSongButtonClicked(ActionEvent e) {
        _audioSequencePlayer.previous();
    }


    /**
     * @param e
     */
    @FXML
    private void onIncreaseVolumeButtonClicked(ActionEvent e) {
        final double incrementPercent = 5;
        final double volume = ((int)(_audioSequencePlayer.getVolumeProperty().getValue() * 100 / incrementPercent) + 1) * incrementPercent / 100;
        _audioSequencePlayer.getVolumeProperty().setValue(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onDecreaseVolumeButtonClicked(ActionEvent e) {
        final double decrementPercent = 5;
        final double volume = ((int)(_audioSequencePlayer.getVolumeProperty().getValue() * 100 / decrementPercent) - 1) * decrementPercent / 100;
        _audioSequencePlayer.getVolumeProperty().setValue(volume);
    }

    /**
     * @param e
     */
    @FXML
    private void onMuteVolumeButtonClicked(ActionEvent e) {
        final double volume = 0;
        _audioSequencePlayer.getVolumeProperty().setValue(volume);
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
    private void onShuffleButtonClicked(ActionEvent e) {
        shuffle();
    }

    /**
     * @param e
     */
    @FXML
    private void onSequencerChanged(AudioSequencerSelector.SelectEvent e) {
        _audioSequencePlayer.setSequencer(e.getSequencer());
    }

    /**
     * @param e
     */
    @FXML
    private void onSearch(SearchBar.SearchEvent e) {
        search(e.getSearchString().toLowerCase());
    }
}
