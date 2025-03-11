package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.database.*;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.scene.controls.ArtistSelector;
import com.github.williwadelmawisky.musicplayer.util.Action;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class SongEditPage extends VBox implements Page {


    @FXML private TextField _nameTextField;
    @FXML private ComboBox<String> _genreComboBox;
    @FXML private ArtistSelector _artistSelector;

    private SongData _songData;
    private Action _onApply;
    private FetchGetHandler _fetchHandler;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongEditPage() {}

    /**
     * @param songData
     * @param fetchHandler
     * @param onApply
     */
    public SongEditPage(final SongData songData, final FetchGetHandler fetchHandler, final Action onApply) {
        super();

        ResourceLoader.loadFxml("fxml/pages/SongEditPage.fxml", this);

        _songData = songData;
        _onApply = onApply;
        _fetchHandler = fetchHandler;

        _nameTextField.setText(_songData.getName());

        Iterable<ArtistData> artists = fetchHandler.fetchGET(Database.TableType.ARTIST);
        _artistSelector.setItems(artists);
        _artistSelector.setValue(_songData.getArtistID());

        List<String> values = Arrays.stream(Genre.values()).map(Enum::name).toList();
        ObservableList<String> itemList = FXCollections.observableList(values);
        _genreComboBox.setItems(itemList);
        _genreComboBox.setValue(songData.getGenre().name());
    }

    /**
     * @param e
     */
    @FXML
    private void onApply(ActionEvent e) {
        final String songName = _nameTextField.getText().trim();
        if (songName.isEmpty())
            return;

        final UUID artistID = _artistSelector.getValue();
        if (artistID == null)
            return;

        final Genre genre = Genre.valueOf(_genreComboBox.getValue());

        _songData.setName(songName);
        _songData.setArtistID(artistID);
        _songData.setGenre(genre);

        _onApply.invoke();
    }

    /**
     * @param e
     */
    @FXML
    private void onCancel(ActionEvent e) {

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
