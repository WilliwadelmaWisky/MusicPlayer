package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.data.Artist;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import com.github.williwadelmawisky.musicplayer.core.db.FetchHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.scene.controls.SearchBar;
import com.github.williwadelmawisky.musicplayer.scene.controls.SongNode;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.util.UUID;

/**
 *
 */
public class PlaylistSongPage extends VBox implements Page {

    @FXML private VBox _songVBox;

    private Playlist _playlist;
    private FetchHandler _fetchHandler;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public PlaylistSongPage() {}

    /**
     * @param playlist
     */
    public PlaylistSongPage(final Playlist playlist, final FetchHandler fetchHandler) {
        super();

        _playlist = playlist;
        _fetchHandler = fetchHandler;

        ResourceLoader.loadFxml("fxml/pages/PlaylistSongPage.fxml", this);

        for (UUID songID : _playlist) {
            final Song song = _fetchHandler.fetchGET(URL.SONG, songID);
            final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, song.getArtistID());
            final SongNode songNode = new SongNode(song.getName(), artist.getName(), (ev) -> onSongSelected(song));
            _songVBox.getChildren().add(songNode);
        }
    }


    /**
     * @param song
     */
    private void onSongSelected(final Song song) {
        //_songVBox.getChildren().forEach(node -> ((SongNode)node).highlight(false));
    }


    /**
     * @param e
     */
    @FXML
    private void onSearch(SearchBar.SearchEvent e) {
        _songVBox.getChildren().clear();
        final String searchString = e.getSearchString().toLowerCase();

        for (UUID songID : _playlist) {
            final Song song = _fetchHandler.fetchGET(URL.SONG, songID);
            final Artist artist = _fetchHandler.fetchGET(URL.ARTIST, song.getArtistID());
            final boolean matchName = song.getName().toLowerCase().contains(searchString);
            final boolean matchArtist = artist.getName().toLowerCase().contains(searchString);

            if (matchName || matchArtist) {
                final SongNode songNode = new SongNode(song.getName(), artist.getName(), (ev) -> onSongSelected(song));
                _songVBox.getChildren().add(songNode);
            }
        }
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
