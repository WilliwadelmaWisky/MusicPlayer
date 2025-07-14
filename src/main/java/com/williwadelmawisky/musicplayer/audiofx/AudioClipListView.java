package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.audio.AudioClipSelector;
import com.williwadelmawisky.musicplayer.util.Lists;
import com.williwadelmawisky.musicplayer.util.ObservableList;
import com.williwadelmawisky.musicplayer.util.SelectionModel;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.fx.SearchField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class AudioClipListView extends VBox {

    private final ListView<AudioClipListViewEntry> _listView;
    private final AudioClipSelectorModeComboBox _audioClipSelectorModeComboBox;

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioClipListView() {
        super();
        setSpacing(5);

        final SearchField searchField = new SearchField();
        searchField.setOnSearch(this::onSearch_SearchField);
        this.getChildren().add(searchField);

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(5);
        this.getChildren().add(hbox);

        _audioClipSelectorModeComboBox = new AudioClipSelectorModeComboBox();
        hbox.getChildren().add(_audioClipSelectorModeComboBox);

        final Button shuffleButton = new Button("Shuffle");
        shuffleButton.setOnAction(this::onClicked_ShuffleButton);
        hbox.getChildren().add(shuffleButton);

        final Label nameLabel = new Label("Name");
        this.getChildren().add(nameLabel);

        _listView = new ListView<>();
        VBox.setVgrow(_listView, Priority.ALWAYS);
        _listView.setMaxHeight(Double.POSITIVE_INFINITY);
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _listView.setOnMouseClicked(this::onClicked_ListView);
        _listView.setOnDragOver(this::onDragOver_ListView);
        _listView.setOnDragDropped(this::onDragDrop_ListView);
        this.getChildren().add(_listView);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _audioClipPlayer.AudioClipList.OnItemAdded.addListener(this::onAdded_AudioClipList);
        _audioClipPlayer.AudioClipList.OnItemRemoved.addListener(this::onRemoved_AudioClipList);
        _audioClipPlayer.AudioClipList.OnCleared.addListener(this::onCleared_AudioClipList);
        _audioClipPlayer.AudioClipList.OnSorted.addListener(this::onSorted_AudioClipList);
        _audioClipPlayer.SelectionModel.OnSelected.addListener(this::onSelected_SelectionModel);

        _audioClipSelectorModeComboBox.bindTo(_audioClipPlayer.AudioClipSelector);
    }

    /**
     *
     */
    public void unbind() {
        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.AudioClipList.OnItemAdded.removeListener(this::onAdded_AudioClipList);
        _audioClipPlayer.AudioClipList.OnItemRemoved.removeListener(this::onRemoved_AudioClipList);
        _audioClipPlayer.AudioClipList.OnCleared.removeListener(this::onCleared_AudioClipList);
        _audioClipPlayer.AudioClipList.OnSorted.removeListener(this::onSorted_AudioClipList);
        _audioClipPlayer.SelectionModel.OnSelected.removeListener(this::onSelected_SelectionModel);

        _audioClipSelectorModeComboBox.unbind();
    }


    /**
     * @return
     */
    public AudioClipSelector.Mode getMode() { return _audioClipSelectorModeComboBox.getValue(); }


    /**
     * @param audioClip
     */
    private void addEntry(final AudioClip audioClip) {
        final AudioClipListViewEntry audioClipListViewEntry = new AudioClipListViewEntry(audioClip);
        _listView.getItems().add(audioClipListViewEntry);
    }

    /**
     * @param index
     */
    private void removeEntry(final int index) {
        _listView.getItems().get(index).onDestroy();
        _listView.getItems().remove(index);
    }

    /**
     *
     */
    private void clearEntries() {
        _listView.getItems().forEach(AudioClipListViewEntry::onDestroy);
        _listView.getItems().clear();
    }


    /**
     * @param sender
     * @param args
     */
    private void onAdded_AudioClipList(final Object sender, final ObservableList.OnItemAddedEventArgs<AudioClip> args) {
        addEntry(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onRemoved_AudioClipList(final Object sender, final ObservableList.OnItemRemovedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> args.Item.equals(audioClipListViewEntry.getAudioClip()));
        if (index == -1)
            return;

        removeEntry(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_AudioClipList(final Object sender, final ObservableList.OnClearedEventArgs<AudioClip> args) {
        clearEntries();
    }

    /**
     * @param sender
     * @param args
     */
    private void onSorted_AudioClipList(final Object sender, final EventArgs args) {
        clearEntries();
        _audioClipPlayer.AudioClipList.forEach(this::addEntry);
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> args.Item.equals(audioClipListViewEntry.getAudioClip()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param e
     */
    private void onClicked_ListView(final MouseEvent e) {
        final AudioClipListViewEntry audioClipListViewEntry = _listView.getSelectionModel().getSelectedItem();
        if (audioClipListViewEntry == null)
            return;

        final int index = _audioClipPlayer.AudioClipList.indexOf(audioClip -> audioClip.equals(audioClipListViewEntry.getAudioClip()));
        _audioClipPlayer.SelectionModel.select(index);
    }

    /**
     * @param e
     */
    private void onDragOver_ListView(final DragEvent e) {
        if (!e.getDragboard().hasFiles())
            return;

        e.acceptTransferModes(TransferMode.ANY);
    }

    /**
     * @param e
     */
    private void onDragDrop_ListView(final DragEvent e) {
        final List<File> fileList = e.getDragboard().getFiles();
        fileList.forEach(file -> {
            final AudioClip audioClip = new AudioClip(file);
            _audioClipPlayer.AudioClipList.add(audioClip);
        });
    }


    /**
     * @param e
     */
    private void onClicked_ShuffleButton(final ActionEvent e) {
        _audioClipPlayer.AudioClipList.shuffle();
    }

    /**
     * @param e
     */
    private void onSearch_SearchField(final SearchField.SearchEvent e) {
        clearEntries();

        _audioClipPlayer.AudioClipList.forEach(audioClip -> {
            final File audioFile = new File(audioClip.getAbsoluteFilePath());
            final boolean matchName = audioFile.getName().toLowerCase().contains(e.getSearchString().toLowerCase());
            //final boolean matchArtist = audioClip.getArtist().getValue().toLowerCase().contains(e.getSearchString().toLowerCase());

            if (matchName) {
                addEntry(audioClip);
            }
        });
    }
}
