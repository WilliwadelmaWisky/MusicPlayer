package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;
import com.williwadelmawisky.musicplayer.audio.SaveManager;
import com.williwadelmawisky.musicplayer.util.Files;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PlaylistChooser extends VBox {

    private static final File PLAYLIST_SAVE_DIRECTORY = Paths.get(System.getProperty("user.home"), ".config", "WilliwadelmaWisky", "MusicPlayer", "playlists").toFile();
    private static final String[] ALLOWED_EXTENSIONS = new String[] { ".json" };

    @FXML private PlaylistListView _playlistListView;

    private File _selectedPlaylistFile;
    private String _windowTitle;


    /**
     *
     */
    public PlaylistChooser() {
        super();
        ResourceLoader.loadFxml("fxml/playlist_chooser.fxml");
    }

    /**
     * @param title
     */
    public PlaylistChooser(final String title) {
        this();
        setTitle(title);
    }


    /**
     * @param title
     */
    public void setTitle(final String title) { _windowTitle = title; }

    /**
     * @return
     */
    public PlaylistInfo showOpenDialog() {
        final List<File> playlistFileList = new ArrayList<>();
        Files.listFiles(PLAYLIST_SAVE_DIRECTORY, ALLOWED_EXTENSIONS, false, playlistFileList::add);
        _playlistListView.setData(playlistFileList);

        final ModalWindow modalWindow = new ModalWindow(new Stage(), _windowTitle, this);
        modalWindow.showAndWait();

        if (_selectedPlaylistFile == null)
            return null;

        final PlaylistInfo playlistInfo = new PlaylistInfo();
        final SaveManager saveManager = new SaveManager();
        if (saveManager.load(playlistInfo, _selectedPlaylistFile))
            return playlistInfo;

        return null;
    }


    /**
     * @param e
     */
    @FXML
    private void onOpenButtonClicked(final ActionEvent e) {
        final PlaylistListViewEntry playlistView = _playlistListView.getSelected();
        if (playlistView == null)
            return;

        _selectedPlaylistFile = playlistView.getFile();
        this.getScene().getWindow().hide();
    }

    /**
     * @param e
     */
    @FXML
    private void onCancelButtonClicked(final ActionEvent e) {
        _selectedPlaylistFile = null;
        this.getScene().getWindow().hide();
    }
}
