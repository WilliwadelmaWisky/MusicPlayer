package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.*;
import com.github.williwadelmawisky.musicplayer.fxutils.SearchField;
import com.github.williwadelmawisky.musicplayer.utils.Lists;
import com.github.williwadelmawisky.musicplayer.utils.ObservableList;
import com.github.williwadelmawisky.musicplayer.utils.SelectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.*;

/**
 *
 */
public class DashboardPage extends VBox {

    private final ListView<AudioClipView> audioClipListView;
    private final AudioClipSelectorTypeComboBox _audioClipSelectorTypeComboBox;

    private AudioClipListPlayer _audioClipPlayer;


    /**
     *
     */
    public DashboardPage() {
        super();

        audioClipListView = new ListView<>();
        audioClipListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        audioClipListView.setOnMouseClicked(this::onListViewClicked);
        audioClipListView.setOnDragOver(this::onListViewDragOver);
        audioClipListView.setOnDragDropped(this::onListViewDragDrop);
        this.getChildren().add(audioClipListView);
    }


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
        audioClipListView.getItems().add(audioClipView);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipRemoved(final Object sender, final ObservableList.OnItemRemovedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(audioClipListView.getItems(), songNode -> args.Item.equalsID(songNode.getSongID()));
        if (index == -1)
            return;

        audioClipListView.getItems().remove(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListCleared(final Object sender, final ObservableList.OnClearedEventArgs args) {
        audioClipListView.getItems().clear();
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipListSorted(final Object sender, final ObservableList.OnSortedEventArgs args) {
        audioClipListView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final AudioClipView audioClipView = new AudioClipView(audioClip);
            audioClipListView.getItems().add(audioClipView);
        });
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipSelected(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        int index = Lists.indexFunc(audioClipListView.getItems(), songNode -> args.Item.equalsID(songNode.getSongID()));
        if (index == -1)
            return;

        audioClipListView.getSelectionModel().clearAndSelect(index);
    }


    /**
     * @param e
     */
    private void onListViewClicked(final MouseEvent e) {
        final AudioClipView songNode = audioClipListView.getSelectionModel().getSelectedItem();
        if (songNode == null)
            return;

        final int index = _audioClipPlayer.getAudioClipList().indexOf(audioClip -> audioClip.equalsID(songNode.getSongID()));
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
        fileList.forEach(this::addUnknownFile);
    }


    /**
     * @param e
     */
    @FXML
    private void onShuffleButtonClicked(ActionEvent e) {
        _audioClipPlayer.getAudioClipList().shuffle();
    }

    /**
     * @param e
     */
    @FXML
    private void onSequencerChanged(AudioClipSelectorTypeComboBox.SelectEvent e) {
        _audioClipPlayer.setAudioClipSelectorType(e.getAudioClipSelectorType());
    }

    /**
     * @param e
     */
    @FXML
    private void onSearch(SearchField.SearchEvent e) {
        audioClipListView.getItems().clear();
        _audioClipPlayer.getAudioClipList().forEach(audioClip -> {
            final boolean matchName = audioClip.getName().toLowerCase().contains(e.getSearchString());
            final boolean matchArtist = audioClip.getArtist().toLowerCase().contains(e.getSearchString());

            if (matchName || matchArtist) {
                final AudioClipView audioClipView = new AudioClipView(audioClip);
                audioClipListView.getItems().add(audioClipView);
            }
        });
    }
}
