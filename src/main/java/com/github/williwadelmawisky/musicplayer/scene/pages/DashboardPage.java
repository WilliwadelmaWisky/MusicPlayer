package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.database.*;
import com.github.williwadelmawisky.musicplayer.database.SongData;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.RedirectHandler;
import com.github.williwadelmawisky.musicplayer.scene.controls.AudioControlPanel;
import com.github.williwadelmawisky.musicplayer.scene.controls.SearchBar;
import com.github.williwadelmawisky.musicplayer.scene.controls.AudioClipSelectorTypeComboBox;
import com.github.williwadelmawisky.musicplayer.scene.controls.SongNode;
import com.github.williwadelmawisky.musicplayer.stage.ModalWindow;
import com.github.williwadelmawisky.musicplayer.utils.Files;
import com.github.williwadelmawisky.musicplayer.utils.Lists;
import com.github.williwadelmawisky.musicplayer.utils.ObservableList;
import com.github.williwadelmawisky.musicplayer.utils.SelectionModel;
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
    @FXML private AudioClipSelectorTypeComboBox _audioClipSelectorTypeComboBox;

    private FetchHandler _fetchHandler;
    private RedirectHandler _redirectHandler;
    private AudioClipListPlayer _audioClipPlayer;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public DashboardPage() {}

    /**
     * @param fetchHandler
     * @param redirectHandler
     * @param audioClipPlayer
     */
    public DashboardPage(final FetchHandler fetchHandler, final RedirectHandler redirectHandler, final AudioClipListPlayer audioClipPlayer) {
        super();

        ResourceLoader.loadFxml("fxml/pages/DashboardPage.fxml", this);

        _audioClipPlayer = audioClipPlayer;
        _fetchHandler = fetchHandler;
        _redirectHandler = redirectHandler;

        final String statusText = _audioClipPlayer.getAudioStatus() == AudioStatus.PLAYING ? "Pause" : "Play";
        _playMenuItem.setText(statusText);

        _audioClipPlayer.OnStatusChanged.addListener(this::onAudioClipPlayerStatusChanged);
        _audioClipPlayer.getAudioClipList().OnItemAdded.addListener(this::onAudioClipAdded);
        _audioClipPlayer.getAudioClipList().OnItemRemoved.addListener(this::onAudioClipRemoved);
        _audioClipPlayer.getAudioClipList().OnCleared.addListener(this::onAudioClipListCleared);
        _audioClipPlayer.getAudioClipList().OnSorted.addListener(this::onAudioClipListSorted);
        _audioClipPlayer.getSelectionModel().OnSelected.addListener(this::onAudioClipSelected);

        _songListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _songListView.setOnMouseClicked(this::onListViewClicked);
        _songListView.setOnDragOver(this::onListViewDragOver);
        _songListView.setOnDragDropped(this::onListViewDragDrop);

        _audioControlPanel.setAudioClipPlayer(_audioClipPlayer);
        _audioControlPanel.setFetchHandler(_fetchHandler);
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
     * @param sender
     * @param args
     */
    private void onAudioClipPlayerStatusChanged(final Object sender, final AudioClipPlayer.OnStatusChangedEventArgs args) {
        final String text = args.AudioStatus == AudioStatus.PLAYING ? "Pause" : "Play";
        _playMenuItem.setText(text);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipAdded(final Object sender, final ObservableList.OnItemAddedEventArgs<AudioClip> args) {
        final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, args.Item.getArtistID());
        final String artistName = (artistData != null) ? artistData.getName() : "";
        final SongNode songNode = new SongNode(args.Item.getID(), args.Item.getName(), artistName);
        _songListView.getItems().add(songNode);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipRemoved(final Object sender, final ObservableList.OnItemRemovedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_songListView.getItems(), songNode -> args.Item.equalsID(songNode.getSongID()));
        if (index == -1)
            return;

        _songListView.getItems().remove(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListCleared(final Object sender, final ObservableList.OnClearedEventArgs args) {
        _songListView.getItems().clear();
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListSorted(final Object sender, final ObservableList.OnSortedEventArgs args) {
        _songListView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, audioClip.getArtistID());
            final String artistName = (artistData != null) ? artistData.getName() : "";
            final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName);
            _songListView.getItems().add(songNode);
        });
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipSelected(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_songListView.getItems(), songNode -> args.Item.equalsID(songNode.getSongID()));
        if (index == -1)
            return;

        _songListView.getSelectionModel().clearAndSelect(index);
    }



    /**
     * @param playlistData
     */
    public void open(final PlaylistData playlistData) {
        _audioClipPlayer.getAudioClipList().clear();

        for (UUID songID : playlistData) {
            final SongData songData = _fetchHandler.fetchGET(Database.TableType.SONG, songID);
            final FileData fileData = _fetchHandler.fetchGET(Database.TableType.FILE, songID);
            final AudioClip audioClip = new AudioClip(fileData.getID(), songData.getName(), songData.getGenre(), fileData.getAbsolutePath(), songData.getArtistID());
            _audioClipPlayer.getAudioClipList().add(audioClip);
        }

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
        final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
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
     * @param searchString
     */
    private void search(final String searchString) {
        _songListView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final ArtistData artistData = _fetchHandler.fetchGET(Database.TableType.ARTIST, audioClip.getArtistID());
            final String artistName = (artistData != null) ? artistData.getName() : "";
            final boolean matchName = audioClip.getName().toLowerCase().contains(searchString);
            final boolean matchArtist = artistName.toLowerCase().contains(searchString);

            if (matchName || matchArtist) {
                final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName);
                _songListView.getItems().add(songNode);
            }
        });
    }


    /**
     * @param e
     */
    private void onListViewClicked(MouseEvent e) {
        final SongNode songNode = _songListView.getSelectionModel().getSelectedItem();
        if (songNode == null)
            return;

        final int index = _audioClipPlayer.getAudioClipList().indexOf(audioClip -> audioClip.equalsID(songNode.getSongID()));
        _audioClipPlayer.getSelectionModel().clearAndSelect(index);
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
        fileList.forEach(this::addUnknownFile);
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
        _audioClipPlayer.getAudioClipList().shuffle();
    }

    /**
     * @param e
     */
    @FXML
    private void onSequencerChanged(AudioClipSelectorTypeComboBox.SelectEvent e) {
        _audioClipPlayer.setAudioClipSelectorType(e.getAudioClipSelectorType());
    }

    /**
     * @param e
     */
    @FXML
    private void onSearch(SearchBar.SearchEvent e) {
        search(e.getSearchString().toLowerCase());
    }
}
