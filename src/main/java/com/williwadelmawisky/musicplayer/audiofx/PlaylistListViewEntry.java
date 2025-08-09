package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
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
        final MenuItem deleteMenuItem = new MenuItem("Delete");
        _contextMenu.getItems().addAll(deleteMenuItem);

        setOnContextMenuRequested(this::onContextMenuRequested);
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
