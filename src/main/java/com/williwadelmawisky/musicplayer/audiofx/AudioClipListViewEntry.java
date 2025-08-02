package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.utilfx.Durations;
import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

/**
 *
 */
public class AudioClipListViewEntry extends HBox {

    private final Label _nameLabel;
    private final Label _durationLabel;
    private final ContextMenu _contextMenu;

    private AudioClip _audioClip;
    private EventHandler<ContextMenuEvent> _onDelete;


    /**
     *
     */
    public AudioClipListViewEntry() {
        super();

        final ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(15);
        imageView.setImage(ResourceLoader.loadImage("img/logo.png"));
        this.getChildren().add(imageView);

        _nameLabel = new Label();
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        getChildren().add(_nameLabel);

        _durationLabel = new Label();
        _durationLabel.setAlignment(Pos.CENTER_RIGHT);
        _durationLabel.setMinWidth(50);
        getChildren().add(_durationLabel);

        _contextMenu = new ContextMenu();
        final MenuItem deleteMenuItem = new MenuItem("Delete");
        _contextMenu.getItems().addAll(deleteMenuItem);

        setSpacing(5);
        setAlignment(Pos.CENTER_LEFT);
        setOnContextMenuRequested(this::onContextMenuRequested);
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
    public EventHandler<ContextMenuEvent> getOnDelete() { return _onDelete; }

    /**
     * @param onDelete
     */
    public void setOnDelete(final EventHandler<ContextMenuEvent> onDelete) { _onDelete = onDelete; }


    /**
     * @param audioClip
     */
    private void updateView(final AudioClip audioClip) {
        _nameLabel.setText(AudioClip.nameof(audioClip));

        if (_audioClip.isReady()) {
            final String durationString = Durations.durationToString(_audioClip.getTotalDuration());
            _durationLabel.setText(durationString);
        }
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
    private void onReady_AudioClip(final Object sender, final EventArgs args) {
        final String durationString = Durations.durationToString(_audioClip.getTotalDuration());
        _durationLabel.setText(durationString);
    }


    /**
     *
     */
    public static final class ContextMenuEvent extends Event {

        public final AudioClipListViewEntry ListViewEntry;

        /**
         * @param eventType
         * @param listViewEntry
         */
        public ContextMenuEvent(final EventType<? extends Event> eventType, final AudioClipListViewEntry listViewEntry) {
            super(eventType);
            ListViewEntry = listViewEntry;
        }
    }
}
