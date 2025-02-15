package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.db.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.scene.controls.PlaylistNode;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 *
 */
public class PlaylistOpenPage extends VBox implements Page {

    /**
     *
     */
    @FunctionalInterface
    public interface OnOpenPlaylist {
        void invoke(final Playlist playlist);
    }

    @FXML
    private VBox _playlistBox;

    private OnOpenPlaylist _onOpenPlaylist;
    private FetchGetHandler _fetchHandler;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public PlaylistOpenPage() {}

    /**
     * @param fetchHandler
     * @param onOpenPlaylist
     */
    public PlaylistOpenPage(final FetchGetHandler fetchHandler, final OnOpenPlaylist onOpenPlaylist) {
        super();

        ResourceLoader.loadFxml("fxml/pages/OpenPlaylistPage.fxml", this);

        _fetchHandler = fetchHandler;
        _onOpenPlaylist = onOpenPlaylist;

        refresh();
    }


    /**
     *
     */
    public void refresh() {
        final Iterable<Playlist> playlists = _fetchHandler.fetchGET(URL.PLAYLIST);
        for (final Playlist playlist : playlists) {
            PlaylistNode playlistNode = new PlaylistNode(playlist.getName(), () -> onPlaylistClicked(playlist));
            _playlistBox.getChildren().add(playlistNode);
        }
    }


    /**
     * @param playlist
     */
    private void onPlaylistClicked(final Playlist playlist) {
        _onOpenPlaylist.invoke(playlist);
    }

    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }
}
