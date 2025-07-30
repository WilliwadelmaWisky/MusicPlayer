package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

/**
 *
 */
public class PlaylistListView extends VBox {

    private final SearchField _searchField;
    private final ListView<PlaylistListViewEntry> _playlistListView;
    private List<File> _playlistFileList;


    /**
     *
     */
    public PlaylistListView() {
        super();

        _searchField = new SearchField();
        _searchField.setOnSearch(this::onSearch);
        this.getChildren().add(_searchField);

        _playlistListView = new ListView<>();
        _playlistListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.getChildren().add(_playlistListView);

        setSpacing(10);
    }


    /**
     * @return
     */
    public PlaylistListViewEntry getSelected() { return _playlistListView.getSelectionModel().getSelectedItem(); }


    /**
     * @param playlistFileList
     */
    public void setData(final List<File> playlistFileList) {
        _playlistFileList = playlistFileList;
        _playlistListView.getItems().forEach(PlaylistListViewEntry::destroy);
        _playlistListView.getItems().clear();

        _playlistFileList.forEach(playlist -> {
            final PlaylistListViewEntry playlistListViewEntry = new PlaylistListViewEntry(playlist);
            _playlistListView.getItems().add(playlistListViewEntry);
        });
    }


    /**
     * @param e
     */
    private void onSearch(final SearchField.SearchEvent e) {
        _playlistListView.getItems().forEach(PlaylistListViewEntry::destroy);
        _playlistListView.getItems().clear();

        _playlistFileList.forEach(playlistFile -> {
            final String name = Files.getNameWithoutExtension(playlistFile);
            if (name == null)
                return;

            final boolean matchName = name.toLowerCase().contains(e.getSearchString().toLowerCase());

            if (matchName) {
                final PlaylistListViewEntry playlistView = new PlaylistListViewEntry(playlistFile);
                _playlistListView.getItems().add(playlistView);
            }
        });
    }
}
