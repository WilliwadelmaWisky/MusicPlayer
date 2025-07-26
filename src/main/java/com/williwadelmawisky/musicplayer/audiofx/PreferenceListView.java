package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.util.fx.SearchField;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 */
public class PreferenceListView extends VBox {

    private final SearchField _searchField;
    private final ListView<PreferenceListViewEntry> _listView;


    /**
     *
     */
    public PreferenceListView() {
        super();

        _searchField = new SearchField();

        _listView = new ListView<>();
        VBox.setVgrow(_listView, Priority.ALWAYS);
        _listView.setMaxHeight(Double.POSITIVE_INFINITY);
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        getChildren().addAll(_searchField, _listView);

        _searchField.setOnSearch(this::onSearch_SearchField);
    }


    /**
     * @param e
     */
    private void onSearch_SearchField(final SearchField.SearchEvent e) {

    }
}
