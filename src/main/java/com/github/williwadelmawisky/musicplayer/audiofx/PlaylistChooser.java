package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylist;
import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylistLoader;
import com.github.williwadelmawisky.musicplayer.fxutils.SearchField;
import com.github.williwadelmawisky.musicplayer.utils.Files;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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

    private final SearchField _searchField;
    private final ListView<AudioPlaylistView> _playlistListView;
    private final List<AudioPlaylist> _playlistList;
    private AudioPlaylist _playlist;
    private String _title;


    /**
     *
     */
    public PlaylistChooser() {
        super();

        _searchField = new SearchField();
        _searchField.setOnSearch(this::onSearch);
        this.getChildren().add(_searchField);

        _playlistListView = new ListView<>();
        _playlistListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.getChildren().add(_playlistListView);

        final Button button = new Button("Open");
        button.setOnAction(this::onClick);
        this.getChildren().add(button);

        setPadding(new Insets(10));
        setSpacing(10);

        _playlistList = new ArrayList<>();
        final AudioPlaylistLoader playlistLoader = new AudioPlaylistLoader();
        final File homeDirectory = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toFile();
        final String[] extensions = new String[] { ".dat" };
        Files.listFiles(homeDirectory, extensions, false, file -> {
            final AudioPlaylist playlist = playlistLoader.loadFromFile(file);
            if (playlist == null)
                return;

            _playlistList.add(playlist);
            final AudioPlaylistView playlistView = new AudioPlaylistView(playlist);
            _playlistListView.getItems().add(playlistView);
        });
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
    public void setTitle(final String title) { _title = title; }

    /**
     * @return
     */
    public AudioPlaylist showDialog() {
        ModalWindow modalWindow = new ModalWindow(new Stage(), _title, this);
        modalWindow.show();
        return _playlist;
    }


    /**
     * @param e
     */
    private void onSearch(final SearchField.SearchEvent e) {
        _playlistListView.getItems().clear();
        _playlistList.forEach(playlist -> {
            final boolean matchName = playlist.getName().toLowerCase().contains(e.getSearchString());
            if (!matchName)
                return;

            final AudioPlaylistView playlistView = new AudioPlaylistView(playlist);
            _playlistListView.getItems().add(playlistView);
        });
    }

    /**
     * @param e
     */
    private void onClick(final ActionEvent e) {
        final AudioPlaylistView playlistView = _playlistListView.getSelectionModel().getSelectedItem();
        if (playlistView == null)
            return;

        _playlist = playlistView.getAudioPlaylist();
        this.getScene().getWindow().hide();
    }
}
