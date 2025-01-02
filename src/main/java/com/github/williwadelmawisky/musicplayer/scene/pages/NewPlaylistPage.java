package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import com.github.williwadelmawisky.musicplayer.core.db.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.core.db.URL;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.scene.controls.SongSelector;
import com.github.williwadelmawisky.musicplayer.util.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.UUID;

/**
 *
 */
public class NewPlaylistPage extends VBox implements Page {

    @FXML private TextField _nameTextField;
    @FXML private ListView<SongSelector> _songVBox;

    private FetchGetHandler _fetchHandler;
    private EventHandler<Playlist> _onApply;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public NewPlaylistPage() {}

    /**
     * @param fetchHandler
     * @param onApply
     */
    public NewPlaylistPage(final FetchGetHandler fetchHandler, final EventHandler<Playlist> onApply) {
        super();

        ResourceLoader.loadFxml("fxml/pages/NewPlaylistPage.fxml", this);

        _fetchHandler = fetchHandler;
        _onApply = onApply;
    }


    /**
     * @param e
     */
    @FXML
    private void onAddButtonClicked(ActionEvent e) {
        final SongSelector songSelector = new SongSelector();
        final Iterable<Song> songs = _fetchHandler.fetchGET(URL.SONG);
        songSelector.setItems(songs);
        _songVBox.getItems().add(songSelector);
    }

    /**
     * @param e
     */
    @FXML
    private void onRemoveButtonClicked(ActionEvent e) {

    }

    /**
     * @param e
     */
    @FXML
    private void onApplyButtonClicked(ActionEvent e) {
        final String name = _nameTextField.getText().trim();
        if (name.isEmpty())
            return;

        final Playlist playlist = new Playlist(UUID.randomUUID(), name);
        _onApply.invoke(playlist);
    }

    /**
     * @param e
     */
    @FXML
    private void onCancelButtonClicked(ActionEvent e) {
        ((Node)e.getTarget()).getScene().getWindow().hide();
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
