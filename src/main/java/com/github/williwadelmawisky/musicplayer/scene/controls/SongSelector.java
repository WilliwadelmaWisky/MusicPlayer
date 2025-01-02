package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.data.Song;
import com.github.williwadelmawisky.musicplayer.core.db.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;

import java.util.List;

/**
 *
 */
public class SongSelector extends HBox {

    @FXML private SearchableComboBox<String> _comboBox;

    private FetchGetHandler _fetchHandler;


    /**
     *
     */
    public SongSelector() {
        super();

        ResourceLoader.loadFxml("fxml/controls/SongSelector.fxml", this);
    }


    public void setItems(final Iterable<Song> songs) {
        final ObservableList<String> items = FXCollections.emptyObservableList();
        songs.forEach(song -> items.add(song.getName()));
        _comboBox.setItems(items);
    }
}
