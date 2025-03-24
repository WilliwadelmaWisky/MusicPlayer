package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylist;
import com.github.williwadelmawisky.musicplayer.fxutils.SearchField;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 *
 */
public class AudioPlaylistListView extends VBox {

    private final SearchField _searchField;
    private final ListView<AudioPlaylistListViewEntry> _playlistListView;
    private List<AudioPlaylist> _playlistList;


    /**
     *
     */
    public AudioPlaylistListView() {
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
    public AudioPlaylistListViewEntry getSelected() { return _playlistListView.getSelectionModel().getSelectedItem(); }


    /**
     * @param playlistList
     */
    public void setData(final List<AudioPlaylist> playlistList) {
        _playlistListView.getItems().forEach(AudioPlaylistListViewEntry::destroy);
        _playlistListView.getItems().clear();

        _playlistList = playlistList;
        playlistList.forEach(playlist -> {
            final AudioPlaylistListViewEntry playlistView = new AudioPlaylistListViewEntry(playlist);
            _playlistListView.getItems().add(playlistView);
        });
    }


    /**
     * @param e
     */
    private void onSearch(final SearchField.SearchEvent e) {
        _playlistListView.getItems().forEach(AudioPlaylistListViewEntry::destroy);
        _playlistListView.getItems().clear();
        _playlistList.forEach(playlist -> {
            final boolean matchName = playlist.getName().toLowerCase().contains(e.getSearchString().toLowerCase());

            if (matchName) {
                final AudioPlaylistListViewEntry playlistView = new AudioPlaylistListViewEntry(playlist);
                _playlistListView.getItems().add(playlistView);
            }
        });
    }
}
