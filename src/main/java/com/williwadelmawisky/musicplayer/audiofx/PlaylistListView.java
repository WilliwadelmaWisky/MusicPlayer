package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfoModel;
import com.williwadelmawisky.musicplayer.audio.SelectionModel;
import com.williwadelmawisky.musicplayer.util.Lists;
import com.williwadelmawisky.musicplayer.util.event.Event;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;

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

        _listView = new ListView<>();
        _listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setSpacing(10);
        getChildren().add(_listView);

        _listView.setOnMouseClicked(this::onClicked_ListView);
        _listView.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (!e.isPrimaryButtonDown())
                e.consume();
        });
    }


    /**
     * @param playlistInfoModel
     */
    public void setPlaylistModel(final PlaylistInfoModel playlistInfoModel) {
        _playlistInfoModel = playlistInfoModel;
        generateEntries(_playlistInfoModel.PlaylistInfoList.filter(playlistInfo -> true));
    }


    /**
     * @param playlistInfo
     */
    public void addEntry(final PlaylistInfo playlistInfo) {
        final PlaylistListViewEntry playlistListViewEntry = new PlaylistListViewEntry(playlistInfo);
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
    private void onSorted_PlaylistInfoList(final Object sender, final Event args) {
        generateEntries(_playlistInfoModel.PlaylistInfoList.filter(playlistInfo -> true));
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.SelectEventArgs<PlaylistInfo> args) {
        int index = Lists.indexFunc(_listView.getItems(), playlistListViewEntry -> args.Item.equals(playlistListViewEntry.getPlaylistInfo()));
        if (index == -1)
            return;

        _listView.getSelectionModel().clearAndSelect(index);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_SelectionModel(final Object sender, final Event args) {
        _listView.getSelectionModel().clearSelection();
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
    private void onDelete_ListViewEntry(final PlaylistListViewEntry.ContextMenuEvent e) {
        _playlistInfoModel.PlaylistInfoList.remove(e.ListViewEntry.getPlaylistInfo());
    }
}
