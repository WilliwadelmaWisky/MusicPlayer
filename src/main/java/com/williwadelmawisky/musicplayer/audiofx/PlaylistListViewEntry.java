package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class PlaylistListViewEntry extends HBox {

    private final Label _nameLabel;
    private final Label _durationLabel;
    private final ContextMenu _contextMenu;

    private PlaylistInfo _playlistInfo;
    private EventHandler<ContextMenuEvent> _onRename;
    private EventHandler<ContextMenuEvent> _onDelete;


    /**
     *
     */
    public PlaylistListViewEntry() {
        super();

        final Label typeLabel = new Label("\uD83D\uDDC0");
        _nameLabel = new Label();
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        _durationLabel = new Label();

        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(typeLabel, _nameLabel, _durationLabel);

        _contextMenu = new ContextMenu();
        final MenuItem renameMenuItem = new MenuItem("Rename");
        final MenuItem deleteMenuItem = new MenuItem("Delete");
        _contextMenu.getItems().addAll(renameMenuItem, deleteMenuItem);

        setOnContextMenuRequested(this::onContextMenuRequested);
        renameMenuItem.setOnAction(this::onRename_ContextMenuItem);
        deleteMenuItem.setOnAction(this::onDelete_ContextMenuItem);
    }

    /**
     * @param playlistInfo
     */
    public PlaylistListViewEntry(final PlaylistInfo playlistInfo) {
        this();
        onCreated(playlistInfo);
    }


    /**
     * @param playlistInfo
     */
    void onCreated(final PlaylistInfo playlistInfo) {
        _playlistInfo = playlistInfo;
        _nameLabel.setText(playlistInfo.name());
        _durationLabel.setText(playlistInfo.totalDurationString());
    }

    /**
     *
     */
    void onDestroy() {

    }


    /**
     * @return
     */
    public PlaylistInfo getPlaylistInfo() { return _playlistInfo; }


    /**
     * @return
     */
    public EventHandler<ContextMenuEvent> getOnRename() { return _onRename; }

    /**
     * @return
     */
    public EventHandler<ContextMenuEvent> getOnDelete() { return _onDelete; }


    /**
     * @param onRename
     */
    public void setOnRename(final EventHandler<ContextMenuEvent> onRename) { _onRename = onRename; }

    /**
     * @param onDelete
     */
    public void setOnDelete(final EventHandler<ContextMenuEvent> onDelete) { _onDelete = onDelete; }


    /**
     * @param e
     */
    private void onContextMenuRequested(final javafx.scene.input.ContextMenuEvent e) {
        if (_contextMenu.isShowing())
            _contextMenu.hide();

        final double x = e.getScreenX() - e.getX() + 20;
        final double y = e.getScreenY() - e.getY() + this.getHeight();
        _contextMenu.show(this, x, y);
    }


    /**
     * @param e
     */
    private void onRename_ContextMenuItem(final ActionEvent e) {
        if (_onRename == null)
            return;

        final ContextMenuEvent contextMenuEvent = new ContextMenuEvent(e.getEventType(), this);
        _onRename.handle(contextMenuEvent);
    }

    /**
     * @param e
     */
    private void onDelete_ContextMenuItem(final ActionEvent e) {
        if (_onDelete == null)
            return;

        final ContextMenuEvent contextMenuEvent = new ContextMenuEvent(e.getEventType(), this);
        _onDelete.handle(contextMenuEvent);
    }


    /**
     *
     */
    public static final class ContextMenuEvent extends javafx.event.Event {

        public final PlaylistListViewEntry ListViewEntry;

        /**
         * @param eventType
         * @param listViewEntry
         */
        public ContextMenuEvent(final EventType<? extends Event> eventType, final PlaylistListViewEntry listViewEntry) {
            super(eventType);
            ListViewEntry = listViewEntry;
        }
    }
}
