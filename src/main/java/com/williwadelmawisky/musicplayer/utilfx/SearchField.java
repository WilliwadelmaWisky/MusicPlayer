package com.williwadelmawisky.musicplayer.utilfx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 */
public class SearchField extends HBox {

    private final TextField _textField;
    private EventHandler<SearchEvent> _searchEventHandler;


    /**
     *
     */
    public SearchField() {
        super();

        _textField = new TextField();
        _textField.setPromptText("Search...");
        final Button button = new Button("\uD83D\uDD0D");

        setSpacing(5);
        getChildren().addAll(_textField, button);

        button.setOnAction(this::onClick_SearchButton);
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
    private void onClick_SearchButton(ActionEvent e) {
        final SearchEvent searchEvent = new SearchEvent(Event.ANY, _textField.getText().trim());
        _searchEventHandler.handle(searchEvent);
    }


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
}
