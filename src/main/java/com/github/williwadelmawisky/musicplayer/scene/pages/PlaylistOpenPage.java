package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.database.Database;
import com.github.williwadelmawisky.musicplayer.core.database.PlaylistData;
import com.github.williwadelmawisky.musicplayer.core.database.FetchGetHandler;
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
        void invoke(final PlaylistData playlistData);
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
        final Iterable<PlaylistData> playlists = _fetchHandler.fetchGET(Database.TableType.PLAYLIST);
        for (final PlaylistData playlistData : playlists) {
            PlaylistNode playlistNode = new PlaylistNode(playlistData.getName(), () -> onPlaylistClicked(playlistData));
            _playlistBox.getChildren().add(playlistNode);
        }
    }


    /**
     * @param playlistData
     */
    private void onPlaylistClicked(final PlaylistData playlistData) {
        _onOpenPlaylist.invoke(playlistData);
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
