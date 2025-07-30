package com.williwadelmawisky.musicplayer.utilfx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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
        HBox.setHgrow(_textField, Priority.ALWAYS);
        _textField.setMaxWidth(Double.POSITIVE_INFINITY);
        this.getChildren().add(_textField);

        final Button button = new Button("Search");
        button.setOnAction(this::onClick);
        this.getChildren().add(button);

        setSpacing(5);
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
    private void onClick(ActionEvent e) {
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
