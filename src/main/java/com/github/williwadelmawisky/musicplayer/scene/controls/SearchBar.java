package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 */
public class SearchBar extends HBox {

    /**
     *
     */
    public static class SearchEvent extends Event {

        private final String _searchString;

        /**
         * @param eventType
         * @param searchString
         */
        public SearchEvent(final EventType<? extends Event> eventType, final String searchString) {
            super(eventType);
            _searchString = searchString;
        }

        /**
         * @return
         */
        public String getSearchString() { return _searchString; }
    }

    @FXML private TextField _searchField;

    private EventHandler<SearchEvent> _searchEventHandler;


    /**
     *
     */
    public SearchBar() {
        super();

        ResourceLoader.loadFxml("fxml/controls/SearchBar.fxml", this);
    }


    /**
     * @return
     */
    public EventHandler<SearchEvent> getOnSearch() { return _searchEventHandler; }

    /**
     * @param eventHandler
     */
    public void setOnSearch(final EventHandler<SearchEvent> eventHandler) { _searchEventHandler = eventHandler; }


    /**
     * @param e
     */
    @FXML
    private void onButtonClicked(ActionEvent e) {
        if (_searchField.getText().isEmpty() || _searchField.getText().isBlank())
            return;

        final SearchEvent searchEvent = new SearchEvent(Event.ANY, _searchField.getText().trim());
        _searchEventHandler.handle(searchEvent);
    }
}
