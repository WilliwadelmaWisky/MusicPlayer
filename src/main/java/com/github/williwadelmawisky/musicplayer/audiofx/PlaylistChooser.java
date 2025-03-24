package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylist;
import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylistLoader;
import com.github.williwadelmawisky.musicplayer.utils.Files;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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

    private final AudioPlaylistListView _playlistListView;
    private AudioPlaylist _playlist;
    private String _title;


    /**
     *
     */
    public PlaylistChooser() {
        super();

        _playlistListView = new AudioPlaylistListView();
        this.getChildren().add(_playlistListView);

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(5);
        this.getChildren().add(hbox);

        final Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this::onCancelButtonClicked);
        hbox.getChildren().add(cancelButton);

        final Button openButton = new Button("Open");
        openButton.setOnAction(this::onOpenButtonClicked);
        hbox.getChildren().add(openButton);

        setPadding(new Insets(10));
        setSpacing(10);
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
        final List<AudioPlaylist> playlistList = new ArrayList<>();
        final AudioPlaylistLoader playlistLoader = new AudioPlaylistLoader();
        final File homeDirectory = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toFile();
        final String[] extensions = new String[] { ".dat" };
        Files.listFiles(homeDirectory, extensions, false, file -> {
            final AudioPlaylist playlist = playlistLoader.loadFromFile(file);
            if (playlist != null)
                playlistList.add(playlist);
        });

        _playlistListView.setData(playlistList);
        final ModalWindow modalWindow = new ModalWindow(new Stage(), _title, this);
        modalWindow.show();
        return _playlist;
    }


    /**
     * @param e
     */
    private void onOpenButtonClicked(final ActionEvent e) {
        final AudioPlaylistListViewEntry playlistView = _playlistListView.getSelected();
        if (playlistView == null)
            return;

        _playlist = playlistView.getAudioPlaylist();
        this.getScene().getWindow().hide();
    }

    /**
     * @param e
     */
    private void onCancelButtonClicked(final ActionEvent e) {
        this.getScene().getWindow().hide();
    }
}
