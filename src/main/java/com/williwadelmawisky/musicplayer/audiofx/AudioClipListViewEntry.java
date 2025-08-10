package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.utilfx.Durations;
import com.williwadelmawisky.musicplayer.util.event.Event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;

/**
 *
 */
public class AudioClipListViewEntry extends HBox {

    private final Label _nameLabel;
    private final Label _durationLabel;
    private final ContextMenu _contextMenu;

    private AudioClip _audioClip;
    private EventHandler<ContextMenuEvent> _onEdit;
    private EventHandler<ContextMenuEvent> _onDelete;


    /**
     *
     */
    public AudioClipListViewEntry() {
        super();

        final Label typeLabel = new Label("♫");
        _nameLabel = new Label();
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        _durationLabel = new Label();

        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(typeLabel, _nameLabel, _durationLabel);

        _contextMenu = new ContextMenu();
        final MenuItem editMenuItem = new MenuItem("Edit");
        final MenuItem deleteMenuItem = new MenuItem("Delete");
        _contextMenu.getItems().addAll(deleteMenuItem);

        setOnContextMenuRequested(this::onContextMenuRequested);
        editMenuItem.setOnAction(this::onEdit_ContextMenuItem);
        deleteMenuItem.setOnAction(this::onDelete_ContextMenuItem);
    }

    /**
     * @param audioClip
     */
    public AudioClipListViewEntry(final AudioClip audioClip) {
        this();
        onCreated(audioClip);
    }


    /**
     * @param audioClip
     */
    void onCreated(final AudioClip audioClip) {
        _audioClip = audioClip;

        updateView(audioClip);

        _audioClip.OnReady.addListener(this::onReady_AudioClip);
    }

    /**
     *
     */
    void onDestroy() {
        if (_audioClip == null)
            return;

        _audioClip.OnReady.removeListener(this::onReady_AudioClip);
    }


    /**
     * @return
     */
    public AudioClip getAudioClip() { return _audioClip; }


    /**
     * @return
     */
    public EventHandler<ContextMenuEvent> getOnEdit() { return _onEdit; }

    /**
     * @return
     */
    public EventHandler<ContextMenuEvent> getOnDelete() { return _onDelete; }


    /**
     * @param onEdit
     */
    public void setOnEdit(final EventHandler<ContextMenuEvent> onEdit) { _onEdit = onEdit; }

    /**
     * @param onDelete
     */
    public void setOnDelete(final EventHandler<ContextMenuEvent> onDelete) { _onDelete = onDelete; }


    /**
     * @param audioClip
     */
    private void updateView(final AudioClip audioClip) {
        _nameLabel.setText(AudioClip.nameof(audioClip));
        updateDurationLabel();
    }

    /**
     *
     */
    private void updateDurationLabel() {
        final String durationString = _audioClip.isReady() ? Durations.durationToString(_audioClip.getTotalDuration()) : "";
        _durationLabel.setText(durationString);
    }


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
    private void onEdit_ContextMenuItem(final ActionEvent e) {
        if (_onEdit == null)
            return;

        final ContextMenuEvent contextMenuEvent = new ContextMenuEvent(e.getEventType(), this);
        _onEdit.handle(contextMenuEvent);
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
     * @param sender
     * @param args
     */
    private void onReady_AudioClip(final Object sender, final Event args) {
        updateDurationLabel();
    }


    /**
     *
     */
    public static final class ContextMenuEvent extends javafx.event.Event {

        public final AudioClipListViewEntry ListViewEntry;

        /**
         * @param eventType
         * @param listViewEntry
         */
        public ContextMenuEvent(final EventType<? extends javafx.event.Event> eventType, final AudioClipListViewEntry listViewEntry) {
            super(eventType);
            ListViewEntry = listViewEntry;
        }
    }
}
