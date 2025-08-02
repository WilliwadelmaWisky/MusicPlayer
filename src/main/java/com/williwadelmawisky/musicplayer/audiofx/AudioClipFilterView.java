package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;

/**
 *
 */
public class AudioClipFilterView extends HBox {

    private AudioClipPlayer _audioClipPlayer;
    private EventHandler<FilterEvent> _filterEvent;


    /**
     *
     */
    public AudioClipFilterView() {
        super();

        final Button shuffleButton = new Button("Shuffle");
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMaxWidth(Double.POSITIVE_INFINITY);
        final SearchField searchField = new SearchField();

        getChildren().addAll(shuffleButton, region, searchField);

        shuffleButton.setOnAction(this::onClicked_ShuffleButton);
        searchField.setOnSearch(this::onSearch_SearchField);
    }


    /**
     * @param audioClipPlayer
     */
    void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
    }


    /**
     * @return
     */
    public EventHandler<FilterEvent> getOnFilter() { return _filterEvent; }

    /**
     * @param filterEvent
     */
    public void setOnFilter(final EventHandler<FilterEvent> filterEvent) { _filterEvent = filterEvent; }


    /**
     * @param e
     */
    private void onSearch_SearchField(final SearchField.SearchEvent e) {
        final List<AudioClip> searchList = _audioClipPlayer.AudioClipList.filter(audioClip -> AudioClip.nameof(audioClip).toLowerCase().contains(e.getSearchString().toLowerCase()));
        _filterEvent.handle(new FilterEvent(Event.ANY, searchList));
    }

    /**
     * @param e
     */
    private void onClicked_ShuffleButton(final ActionEvent e) {
        _audioClipPlayer.AudioClipList.shuffle();
    }


    /**
     *
     */
    public static class FilterEvent extends Event {

        public final List<AudioClip> FilterList;

        /**
         * @param eventType
         * @param filterList
         */
        public FilterEvent(final EventType<? extends Event> eventType, final List<AudioClip> filterList) {
            super(eventType);
            FilterList = filterList;
        }
    }
}
