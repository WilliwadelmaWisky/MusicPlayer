package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.control.audio.*;
import com.github.williwadelmawisky.musicplayer.core.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.data.AudioFile;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import com.github.williwadelmawisky.musicplayer.core.db.FetchHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

/**
 *
 */
public class DashboardPage extends VBox implements Page {

    @FXML private VBox _songVBox;
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
     */
    public DashboardPage(final FetchHandler fetchHandler, final RedirectHandler redirectHandler) {
        super();

        ResourceLoader.loadFxml("fxml/pages/DashboardPage.fxml", this);

        _audioSequencePlayer = new AudioSequencePlayer(_audioSequencerSelector.getCurrentSequencer());
        _fetchHandler = fetchHandler;
        _redirectHandler = redirectHandler;

        _audioControlPanel.setAudioClipPlayer(_audioSequencePlayer.getAudioClipPlayer());
        _audioControlPanel.setFetchHandler(_fetchHandler);
        _audioControlPanel.setOnPrevious(this::onPrevious);
        _audioControlPanel.setOnNext(this::onNext);
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

        _audioSequencePlayer.selectFirst();
        updateSongList();
    }

    /**
     * @param file
     */
    public void loadFile(final File file) {
        _audioSequencePlayer.clear();

        final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
        _audioSequencePlayer.add(audioClip);
        _audioSequencePlayer.selectFirst();
        updateSongList();
    }

    /**
     * @param directory
     */
    public void loadDirectory(final File directory) {
        _audioSequencePlayer.clear();

        final String[] extensions = new String[] { ".mp3", ".wav" };
        Files.listFiles(directory, extensions, true, file -> {
            final AudioClip audioClip = new AudioClip(UUID.randomUUID(), file.getName(), null, file.getAbsolutePath(), null);
            _audioSequencePlayer.add(audioClip);
        });

        _audioSequencePlayer.selectFirst();
        updateSongList();
    }


    /**
     *
     */
    public void shuffle() {
        _audioSequencePlayer.shuffle();
        _audioSequencePlayer.selectFirst();
        updateSongList();
    }


    /**
     *
     */
    private void updateSongList() {
        _songVBox.getChildren().clear();

        for (AudioClip audioClip : _audioSequencePlayer) {
            final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, audioClip.getArtistID());
            final String artistName = (artist != null) ? artist.getName() : "";
            final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName, audioClip.getDuration(), this::onSongNodeClicked);
            _songVBox.getChildren().add(songNode);

            /*
            final Media media = ResourceLoader.loadMedia(audioClip.getFilePath());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                songNode.setDuration(media.getDuration());
            });
             */
        }
    }

    /**
     * @param searchString
     */
    private void searchSongList(final String searchString) {
        _songVBox.getChildren().clear();

        for (AudioClip audioClip : _audioSequencePlayer) {
            final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, audioClip.getArtistID());
            final String artistName = (artist != null) ? artist.getName() : "";
            final boolean matchName = audioClip.getName().toLowerCase().contains(searchString);
            final boolean matchArtist = artistName.toLowerCase().contains(searchString);

            if (matchName || matchArtist) {
                final SongNode songNode = new SongNode(audioClip.getID(), audioClip.getName(), artistName, audioClip.getDuration(), this::onSongNodeClicked);
                _songVBox.getChildren().add(songNode);
            }

            /*
            final Media media = ResourceLoader.loadMedia(audioClip.getFilePath());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                songNode.setDuration(media.getDuration());
            });
             */
        }
    }

    /**
     * @param e
     */
    private void onSongNodeClicked(final SongNode.ClickEvent e) {
        if (e.getMouseButton() != MouseButton.PRIMARY)
            return;

        if (e.getTarget().isSelected()) {
            _audioSequencePlayer.select(e.getTarget().getSongID());
        }

        if (e.isShortcutDown()) {
            final int index = Lists.indexFunc(_songVBox.getChildren(), node -> ((SongNode)node).isSelected());
            ((SongNode)_songVBox.getChildren().get(index)).select();
            return;
        }

        if (e.isShiftDown()) {
            return;
        }

        _songVBox.getChildren().forEach(node -> ((SongNode)node).deselect());
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files (.mp3)", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files (.wav)", "*.wav"));

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            fileChooser.setInitialDirectory(initialDirectory);

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

        final File initialDirectory = new File(System.getProperty("user.home"), "Music");
        if (initialDirectory.exists() && initialDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initialDirectory);

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
    private void onIncreaseVolumeButtonClicked(ActionEvent e) {
        final double incrementPercent = 5;
        final double volume = ((int)(_audioSequencePlayer.getAudioClipPlayer().getVolume() * 100 / incrementPercent) + 1) * incrementPercent / 100;
        _audioSequencePlayer.getAudioClipPlayer().setVolume(volume);
        _audioControlPanel.updateVolume();
    }

    /**
     * @param e
     */
    @FXML
    private void onDecreaseVolumeButtonClicked(ActionEvent e) {
        final double decrementPercent = 5;
        final double volume = ((int)(_audioSequencePlayer.getAudioClipPlayer().getVolume() * 100 / decrementPercent) - 1) * decrementPercent / 100;
        _audioSequencePlayer.getAudioClipPlayer().setVolume(volume);
        _audioControlPanel.updateVolume();
    }

    /**
     * @param e
     */
    @FXML
    private void onMuteVolumeButtonClicked(ActionEvent e) {
        final double volume = 0;
        _audioSequencePlayer.getAudioClipPlayer().setVolume(volume);
        _audioControlPanel.updateVolume();
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
        searchSongList(e.getSearchString().toLowerCase());
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
