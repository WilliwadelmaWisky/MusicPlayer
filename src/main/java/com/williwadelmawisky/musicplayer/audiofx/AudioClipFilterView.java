package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

/**
 *
 */
public class AudioClipFilterView extends VBox {

    private final Button _expandButton;
    private final HBox _expandHBox;
    private final SelectionModeComboBox _selectionModeComboBox;

    private AudioClipPlayer _audioClipPlayer;
    private EventHandler<FilterEvent> _filterEvent;
    private boolean _isExpanded;


    /**
     *
     */
    public AudioClipFilterView() {
        super();

        final HBox searchHBox = new HBox();
        _expandButton = new Button("→");
        _expandButton.setOnAction(this::onClick_ExpandButton);
        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMaxWidth(Double.POSITIVE_INFINITY);
        final SearchField searchField = new SearchField();
        searchField.setOnSearch(this::onSearch_SearchField);
        searchField.setFillHeight(true);
        searchHBox.getChildren().addAll(_expandButton, region, searchField);

        _expandHBox = new HBox();
        _selectionModeComboBox = new SelectionModeComboBox();
        final Button shuffleButton = new Button("Shuffle");
        _expandHBox.setSpacing(5);
        _expandHBox.setFillHeight(true);
        _expandHBox.getChildren().addAll(_selectionModeComboBox, shuffleButton);

        _isExpanded = false;
        setSpacing(5);
        getChildren().addAll(searchHBox, _expandHBox);
    }


    /**
     * @param audioClipPlayer
     */
    void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _selectionModeComboBox.setSelectionModeProperty(_audioClipPlayer.SelectionModel.SelectionModeProperty);
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
    private void onClick_ExpandButton(final ActionEvent e) {
        _isExpanded = !_isExpanded;

        _expandButton.setText(_isExpanded ? "↓" : "→");
        _expandHBox.setVisible(_isExpanded);

        final double expandHeight = _isExpanded ? 28 : 0;
        _expandHBox.setMinHeight(expandHeight);
        _expandHBox.setMaxHeight(expandHeight);

        final double totalHeight = _isExpanded ? 28 * 2 + 5 : 28;
        setMinHeight(totalHeight);
        setMaxHeight(totalHeight);
    }

    /**
     * @param e
     */
    private void onSearch_SearchField(final SearchField.SearchEvent e) {
        final List<AudioClip> searchList = _audioClipPlayer.AudioClipList.filter(audioClip -> {
            final File audioFile = new File(audioClip.getAbsoluteFilePath());
            final boolean matchName = Files.getNameWithoutExtension(audioFile).toLowerCase().contains(e.getSearchString().toLowerCase());
            return matchName;
        });

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
