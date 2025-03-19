package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.fxutils.SearchField;
import com.github.williwadelmawisky.musicplayer.utils.Lists;
import com.github.williwadelmawisky.musicplayer.utils.ObservableList;
import com.github.williwadelmawisky.musicplayer.utils.SelectionModel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.*;

/**
 *
 */
public class AudioListView extends VBox {

    private final ListView<AudioClipView> _listView;
    private final AudioClipSelectorTypeComboBox _audioClipSelectorTypeComboBox;

    private AudioClipListPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioListView() {
        super();
        setSpacing(5);

        final SearchField searchField = new SearchField();
        searchField.setOnSearch(this::onSearch);
        this.getChildren().add(searchField);

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        this.getChildren().add(hbox);

        _audioClipSelectorTypeComboBox = new AudioClipSelectorTypeComboBox();
        _audioClipSelectorTypeComboBox.setOnSelect(this::onSequencerChanged);
        hbox.getChildren().add(_audioClipSelectorTypeComboBox);

        final Button shuffleButton = new Button("Shuffle");
        shuffleButton.setOnAction(this::onShuffleButtonClicked);
        hbox.getChildren().add(shuffleButton);

        _listView = new ListView<>();
        VBox.setVgrow(_listView, Priority.ALWAYS);
        _listView.setMaxHeight(Double.POSITIVE_INFINITY);
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _listView.setOnMouseClicked(this::onListViewClicked);
        _listView.setOnDragOver(this::onListViewDragOver);
        _listView.setOnDragDropped(this::onListViewDragDrop);
        this.getChildren().add(_listView);
    }


    /**
     * @return
     */
    public AudioClipSelectorType getSelectorType() { return _audioClipSelectorTypeComboBox.getValue(); }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipListPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        _audioClipPlayer.getAudioClipList().OnItemAdded.addListener(this::onAudioClipAdded);
        _audioClipPlayer.getAudioClipList().OnItemRemoved.addListener(this::onAudioClipRemoved);
        _audioClipPlayer.getAudioClipList().OnCleared.addListener(this::onAudioClipListCleared);
        _audioClipPlayer.getAudioClipList().OnSorted.addListener(this::onAudioClipListSorted);
        _audioClipPlayer.getSelectionModel().OnSelected.addListener(this::onAudioClipSelected);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipAdded(final Object sender, final ObservableList.OnItemAddedEventArgs<AudioClip> args) {
        final AudioClipView audioClipView = new AudioClipView(args.Item);
        _listView.getItems().add(audioClipView);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipRemoved(final Object sender, final ObservableList.OnItemRemovedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipView -> args.Item.equals(audioClipView.getAudioClip()));
        if (index == -1)
            return;

        _listView.getItems().remove(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListCleared(final Object sender, final ObservableList.OnClearedEventArgs args) {
        _listView.getItems().clear();
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListSorted(final Object sender, final ObservableList.OnSortedEventArgs args) {
        _listView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final AudioClipView audioClipView = new AudioClipView(audioClip);
            _listView.getItems().add(audioClipView);
        });
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipSelected(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipView -> args.Item.equals(audioClipView.getAudioClip()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }


    /**
     * @param e
     */
    private void onListViewClicked(final MouseEvent e) {
        final AudioClipView audioClipView = _listView.getSelectionModel().getSelectedItem();
        if (audioClipView == null)
            return;

        final int index = _audioClipPlayer.getAudioClipList().indexOf(audioClip -> audioClip.equals(audioClipView.getAudioClip()));
        _audioClipPlayer.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param e
     */
    private void onListViewDragOver(final DragEvent e) {
        if (!e.getDragboard().hasFiles())
            return;

        e.acceptTransferModes(TransferMode.ANY);
    }

    /**
     * @param e
     */
    private void onListViewDragDrop(final DragEvent e) {
        final List<File> fileList = e.getDragboard().getFiles();
        final AudioClipLoader audioClipLoader = new AudioClipLoader(_audioClipPlayer);
        fileList.forEach(audioClipLoader::addUnknownFile);
    }


    /**
     * @param e
     */
    private void onShuffleButtonClicked(ActionEvent e) {
        _audioClipPlayer.getAudioClipList().shuffle();
    }

    /**
     * @param e
     */
    private void onSequencerChanged(AudioClipSelectorTypeComboBox.SelectEvent e) {
        _audioClipPlayer.setAudioClipSelectorType(e.getAudioClipSelectorType());
    }

    /**
     * @param e
     */
    private void onSearch(final SearchField.SearchEvent e) {
        _listView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final boolean matchName = audioClip.getName().getValue().toLowerCase().contains(e.getSearchString());
            final boolean matchArtist = audioClip.getArtist().getValue().toLowerCase().contains(e.getSearchString());

            if (matchName || matchArtist) {
                final AudioClipView audioClipView = new AudioClipView(audioClip);
                _listView.getItems().add(audioClipView);
            }
        });
    }
}
