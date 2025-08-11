package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.*;
import com.williwadelmawisky.musicplayer.util.Lists;
import com.williwadelmawisky.musicplayer.util.event.Event;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public class PlaylistListView extends VBox {

    private final ListView<PlaylistListViewEntry> _listView;
    private PlaylistInfoModel _playlistInfoModel;


    /**
     *
     */
    public PlaylistListView() {
        super();

        final HBox labelHBox = new HBox();
        final Label nameLabel = new Label("Name");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        final Label durationLabel = new Label("Duration");
        labelHBox.getChildren().addAll(nameLabel, durationLabel);

        _listView = new ListView<>();
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setSpacing(5);
        getChildren().addAll(labelHBox, _listView);

        _listView.setOnMouseClicked(this::onClicked_ListView);
        _listView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (!e.isPrimaryButtonDown())
                e.consume();
        });
    }


    /**
     * @param playlistInfoModel
     */
    void onCreated(final PlaylistInfoModel playlistInfoModel) {
        _playlistInfoModel = playlistInfoModel;

        generateEntries(_playlistInfoModel.PlaylistInfoList.filter(playlistInfo -> true));

        _playlistInfoModel.PlaylistInfoList.OnItemRemoved.addListener(this::onRemoved_PlaylistInfoList);
    }


    /**
     * @param playlistInfo
     */
    public void addEntry(final PlaylistInfo playlistInfo) {
        final PlaylistListViewEntry playlistListViewEntry = new PlaylistListViewEntry(playlistInfo);
        playlistListViewEntry.setOnRename(this::onRename_ListViewEntry);
        playlistListViewEntry.setOnDelete(this::onDelete_ListViewEntry);
        _listView.getItems().add(playlistListViewEntry);
    }

    /**
     * @param playlistInfoList
     */
    public void generateEntries(final List<PlaylistInfo> playlistInfoList) {
        clearEntries();
        playlistInfoList.forEach(this::addEntry);

        int index = Lists.indexFunc(_listView.getItems(), playlistListViewEntry -> playlistListViewEntry.getPlaylistInfo().equals(_playlistInfoModel.SelectionModel.getValue()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param index
     */
    public void removeEntry(final int index) {
        _listView.getItems().get(index).onDestroy();
        _listView.getItems().remove(index);
    }

    /**
     *
     */
    public void clearEntries() {
        _listView.getItems().forEach(PlaylistListViewEntry::onDestroy);
        _listView.getItems().clear();
    }


    /**
     * @param sender
     * @param args
     */
    private void onRemoved_PlaylistInfoList(final Object sender, final ObservableList.RemoveEventArgs<PlaylistInfo> args) {
        int index = Lists.indexFunc(_listView.getItems(), playlistListViewEntry -> args.Item.equals(playlistListViewEntry.getPlaylistInfo()));
        if (index == -1)
            return;

        removeEntry(index);
    }


    /**
     * @param e
     */
    private void onClicked_ListView(final MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY)
            return;

        final PlaylistListViewEntry playlistListViewEntry = _listView.getSelectionModel().getSelectedItem();
        if (playlistListViewEntry == null || playlistListViewEntry.getPlaylistInfo().equals(_playlistInfoModel.SelectionModel.getValue()))
            return;

        final int index = _playlistInfoModel.PlaylistInfoList.indexOf(playlistInfo -> playlistInfo.equals(playlistListViewEntry.getPlaylistInfo()));
        _playlistInfoModel.SelectionModel.selectIndex(index);
    }


    /**
     * @param e
     */
    private void onRename_ListViewEntry(final PlaylistListViewEntry.ContextMenuEvent e) {
        final String oldName = e.ListViewEntry.getPlaylistInfo().name();
        final TextInputDialog textInputDialog = new TextInputDialog(oldName);
        textInputDialog.setTitle("Rename the playlist");
        textInputDialog.setHeaderText(null);
        textInputDialog.setGraphic(null);

        final Optional<String> result = textInputDialog.showAndWait();
        if (result.isEmpty())
            return;

        final String newName = result.get().trim();
        if (newName.isEmpty() || oldName.equals(newName))
            return;

        if (_playlistInfoModel.rename(e.ListViewEntry.getPlaylistInfo(), newName))
            e.ListViewEntry.updateNameLabel();
    }

    /**
     * @param e
     */
    private void onDelete_ListViewEntry(final PlaylistListViewEntry.ContextMenuEvent e) {
        _playlistInfoModel.delete(e.ListViewEntry.getPlaylistInfo());
    }
}
