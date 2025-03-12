package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ComboBox;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AudioClipSelectorTypeComboBox extends ComboBox<String> {

    /**
     *
     */
    public static class SelectEvent extends Event {

        private final AudioClipSelectorType _selector;

        /**
         * @param eventType
         * @param selector
         */
        public SelectEvent(final EventType<? extends Event> eventType, final AudioClipSelectorType selector) {
            super(eventType);
            _selector = selector;
        }

        /**
         * @return
         */
        public AudioClipSelectorType getAudioClipSelectorType() { return _selector; }
    }

    private EventHandler<SelectEvent> _selectEventHandler;


    /**
     *
     */
    public AudioClipSelectorTypeComboBox() {
        super();

        final List<String> nameList = Arrays.stream(AudioClipSelectorType.values()).map(Enum::name).toList();
        final ObservableList<String> items = FXCollections.observableList(nameList);
        setItems(items);
        setValue(AudioClipSelectorType.ORDERED.name());
        valueProperty().addListener(this::onValueChanged);
    }


    /**
     * @return
     */
    public EventHandler<SelectEvent> getOnSelect() { return _selectEventHandler; }

    /**
     * @param eventHandler
     */
    public void setOnSelect(final EventHandler<SelectEvent> eventHandler) { _selectEventHandler = eventHandler; }


    /**
     * @param observable
     * @param oldVal
     * @param newVal
     */
    private void onValueChanged(final ObservableValue<? extends String> observable, final String oldVal, final String newVal) {
        if (_selectEventHandler == null)
            return;

        final AudioClipSelectorType audioClipSelectorType = AudioClipSelectorType.valueOf(newVal);
        _selectEventHandler.handle(new SelectEvent(Event.ANY, audioClipSelectorType));
    }
}
