package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.database.data.Artist;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class ArtistSelector extends HBox {

    @FXML private SearchableComboBox<String> _comboBox;

    private final Map<UUID, Artist> _artistMap;
    private UUID _value;


    /**
     *
     */
    public ArtistSelector() {
        super();

        ResourceLoader.loadFxml("fxml/controls/ArtistSelector.fxml", this);

        _artistMap = new HashMap<>();
        _comboBox.valueProperty().addListener(this::onValueSelected);
    }


    /**
     * @return
     */
    public UUID getValue() { return _value; }


    /**
     * @param artists
     */
    public void setItems(final Iterable<Artist> artists) {
        _artistMap.clear();
        for (Artist artist : artists) {
            _artistMap.put(artist.getID(), artist);
        }

        final ObservableList<String> artistNameList = FXCollections.observableList(_artistMap.values().stream().map(Artist::getName).toList());
        _comboBox.setItems(artistNameList);
    }

    /**
     * @param id
     */
    public void setValue(final UUID id) {
        if (!_artistMap.containsKey(id))
            return;

        _value = id;
        _comboBox.setValue(_artistMap.get(id).getName());
    }


    /**
     * @param e
     */
    @FXML
    private void onNewButtonClicked(ActionEvent e) {

    }

    /**
     * @param observable
     * @param oldVal
     * @param newVal
     */
    private void onValueSelected(ObservableValue<? extends String> observable, String oldVal, String newVal) {
        final List<Artist> artistList = _artistMap.values().stream().filter(artist -> artist.getName().equals(newVal)).toList();
        _value = artistList.getFirst().getID();
    }
}
