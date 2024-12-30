package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.core.control.audio.*;
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

        private final Sequencer<AudioClip> _sequencer;

        /**
         * @param eventType
         * @param sequencer
         */
        public SelectEvent(final EventType<? extends Event> eventType, final Sequencer<AudioClip> sequencer) {
            super(eventType);
            _sequencer = sequencer;
        }

        /**
         * @return
         */
        public Sequencer<AudioClip> getSequencer() { return _sequencer; }
    }

    /**
     *
     */
    private enum SequencerType {
        Order,
        Random,
        Repeat
    }

    private final Map<SequencerType, Sequencer<AudioClip>> _sequencerMap;
    private EventHandler<SelectEvent> _selectEventHandler;


    /**
     *
     */
    public AudioSequencerSelector() {
        super();

        _sequencerMap = new HashMap<>();
        _sequencerMap.put(SequencerType.Order, new OrderSequencer());
        _sequencerMap.put(SequencerType.Random, new RandomSequencer());
        _sequencerMap.put(SequencerType.Repeat, new RepeatSequencer());

        final List<String> nameList = _sequencerMap.keySet().stream().map(Enum::name).toList();
        final ObservableList<String> items = FXCollections.observableList(nameList);
        setItems(items);
        setValue(SequencerType.Order.name());
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
        final SequencerType type = SequencerType.valueOf(newVal);
        final Sequencer<AudioClip> sequencer = _sequencerMap.get(type);
        final SelectEvent event = new SelectEvent(Event.ANY, sequencer);
        _selectEventHandler.handle(event);
    }
}
