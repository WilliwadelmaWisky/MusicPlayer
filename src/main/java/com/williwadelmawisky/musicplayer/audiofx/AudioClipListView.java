package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.util.Lists;
import com.williwadelmawisky.musicplayer.audio.ObservableList;
import com.williwadelmawisky.musicplayer.audio.SelectionModel;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
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

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioClipListView() {
        super();

        final HBox labelHBox = new HBox();
        final Label nameLabel = new Label("Name");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        final Label durationLabel = new Label("Duration");
        labelHBox.getChildren().addAll(nameLabel, durationLabel);

        _listView = new ListView<>();
        VBox.setVgrow(_listView, Priority.ALWAYS);
        _listView.setMaxHeight(Double.POSITIVE_INFINITY);
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setSpacing(5);
        getChildren().addAll(labelHBox, _listView);

        _listView.setOnMouseClicked(this::onClicked_ListView);
        _listView.setOnDragOver(this::onDragOver_ListView);
        _listView.setOnDragDropped(this::onDragDrop_ListView);
        _listView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (e.isSecondaryButtonDown())
                e.consume();
        });
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        _audioClipPlayer.AudioClipList.OnItemAdded.addListener(this::onAdded_AudioClipList);
        _audioClipPlayer.AudioClipList.OnItemRemoved.addListener(this::onRemoved_AudioClipList);
        _audioClipPlayer.AudioClipList.OnCleared.addListener(this::onCleared_AudioClipList);
        _audioClipPlayer.AudioClipList.OnSorted.addListener(this::onSorted_AudioClipList);
        _audioClipPlayer.SelectionModel.OnSelected.addListener(this::onSelected_SelectionModel);
        _audioClipPlayer.SelectionModel.OnCleared.addListener(this::onCleared_SelectionModel);
    }

    /**
     *
     */
    public void clear() {
        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.AudioClipList.OnItemAdded.removeListener(this::onAdded_AudioClipList);
        _audioClipPlayer.AudioClipList.OnItemRemoved.removeListener(this::onRemoved_AudioClipList);
        _audioClipPlayer.AudioClipList.OnCleared.removeListener(this::onCleared_AudioClipList);
        _audioClipPlayer.AudioClipList.OnSorted.removeListener(this::onSorted_AudioClipList);
        _audioClipPlayer.SelectionModel.OnSelected.removeListener(this::onSelected_SelectionModel);
        _audioClipPlayer.SelectionModel.OnCleared.removeListener(this::onCleared_SelectionModel);
    }


    /**
     * @param audioClipList
     */
    public void generateEntries(final List<AudioClip> audioClipList) {
        clearEntries();
        audioClipList.forEach(this::addEntry);

        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> audioClipListViewEntry.getAudioClip().equals(_audioClipPlayer.SelectionModel.getValue()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param audioClip
     */
    private void addEntry(final AudioClip audioClip) {
        final AudioClipListViewEntry audioClipListViewEntry = new AudioClipListViewEntry(audioClip);
        audioClipListViewEntry.setOnDelete(this::onDelete_ListViewEntry);
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
    private void onAdded_AudioClipList(final Object sender, final ObservableList.AddEventArgs<AudioClip> args) {
        addEntry(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onRemoved_AudioClipList(final Object sender, final ObservableList.RemoveEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> args.Item.equals(audioClipListViewEntry.getAudioClip()));
        if (index == -1)
            return;

        removeEntry(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_AudioClipList(final Object sender, final ObservableList.ClearEventArgs<AudioClip> args) {
        clearEntries();
    }

    /**
     * @param sender
     * @param args
     */
    private void onSorted_AudioClipList(final Object sender, final EventArgs args) {
        clearEntries();
        _audioClipPlayer.AudioClipList.forEach(this::addEntry);

        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> audioClipListViewEntry.getAudioClip().equals(_audioClipPlayer.SelectionModel.getValue()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.SelectEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(_listView.getItems(), audioClipListViewEntry -> args.Item.equals(audioClipListViewEntry.getAudioClip()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_SelectionModel(final Object sender, final EventArgs args) {
        _listView.getSelectionModel().clearSelection();
    }


    /**
     * @param e
     */
    private void onClicked_ListView(final MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY)
            return;

        final AudioClipListViewEntry audioClipListViewEntry = _listView.getSelectionModel().getSelectedItem();
        if (audioClipListViewEntry == null || audioClipListViewEntry.getAudioClip().equals(_audioClipPlayer.SelectionModel.getValue()))
            return;

        final int index = _audioClipPlayer.AudioClipList.indexOf(audioClip -> audioClip.equals(audioClipListViewEntry.getAudioClip()));
        _audioClipPlayer.SelectionModel.selectIndex(index);
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
    private void onDelete_ListViewEntry(final AudioClipListViewEntry.ContextMenuEvent e) {
        _audioClipPlayer.AudioClipList.remove(e.ListViewEntry.getAudioClip());
    }
}
