package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.audio.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AudioSequencerSelector extends ComboBox<String> {

    /**
     *
     */
    public static class SelectEvent extends Event {

        private final Selector<AudioClip> _selector;

        /**
         * @param eventType
         * @param selector
         */
        public SelectEvent(final EventType<? extends Event> eventType, final Selector<AudioClip> selector) {
            super(eventType);
            _selector = selector;
        }

        /**
         * @return
         */
        public Selector<AudioClip> getSequencer() { return _selector; }
    }

    /**
     *
     */
    public enum SequencerType {
        Order,
        Random,
        Repeat
    }

    private final Map<SequencerType, Selector<AudioClip>> _sequencerMap;
    private EventHandler<SelectEvent> _selectEventHandler;


    /**
     *
     */
    public AudioSequencerSelector() {
        super();

        _sequencerMap = new HashMap<>();
        _sequencerMap.put(SequencerType.Order, new OrderSelector());
        _sequencerMap.put(SequencerType.Random, new RandomSelector());
        _sequencerMap.put(SequencerType.Repeat, new RepeatSelector());

        final List<String> nameList = _sequencerMap.keySet().stream().map(Enum::name).toList();
        final ObservableList<String> items = FXCollections.observableList(nameList);
        setItems(items);
        setCurrentSequencer(SequencerType.Order);
        valueProperty().addListener(this::onValueChanged);
    }


    /**
     * @return
     */
    public Selector<AudioClip> getCurrentSequencer() {
        final SequencerType type = SequencerType.valueOf(getValue());
        return _sequencerMap.get(type);
    }

    /**
     * @param sequencerType
     */
    public void setCurrentSequencer(final SequencerType sequencerType) {
        if (_sequencerMap.containsKey(sequencerType))
            setValue(sequencerType.name());
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

        final SequencerType type = SequencerType.valueOf(newVal);
        final Selector<AudioClip> selector = _sequencerMap.get(type);
        final SelectEvent event = new SelectEvent(Event.ANY, selector);
        _selectEventHandler.handle(event);
    }
}
