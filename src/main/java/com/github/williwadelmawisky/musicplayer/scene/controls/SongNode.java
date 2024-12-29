package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.control.Timer;
import com.github.williwadelmawisky.musicplayer.util.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.UUID;

/**
 *
 */
public class SongNode extends HBox {

    /**
     *
     */
    public static class ClickEvent {

        private final SongNode _target;
        private final MouseButton _mouseButton;
        private final boolean _isShortcutDown, _isShiftDown;

        /**
         * @param target
         * @param mouseButton
         */
        public ClickEvent(final SongNode target, final MouseButton mouseButton, final boolean isShortcutDown, final boolean isShiftDown) {
            _target = target;
            _mouseButton = mouseButton;
            _isShortcutDown = isShortcutDown;
            _isShiftDown = isShiftDown;
        }

        /**
         * @return
         */
        public SongNode getTarget() { return _target; }

        /**
         * @return
         */
        public MouseButton getMouseButton() { return _mouseButton; }

        /**
         * @return
         */
        public boolean isShortcutDown() { return _isShortcutDown; }

        /**
         * @return
         */
        public boolean isShiftDown() { return _isShiftDown; }
    }


    @FXML private Label _nameLabel;
    @FXML private Label _artistLabel;
    @FXML private Label _durationLabel;

    private UUID _songID;
    private EventHandler<ClickEvent> _onClick;
    private boolean _isSelected, _isMouseOver;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongNode() {}

    /**
     * @param id
     * @param songName
     * @param artistName
     * @param duration
     * @param onClick
     */
    public SongNode(final UUID id, final String songName, final String artistName, final Duration duration, final EventHandler<ClickEvent> onClick) {
        this();

        ResourceLoader.loadFxml("fxml/controls/SongNode.fxml", this);

        _songID = id;
        _onClick = onClick;
        _nameLabel.setText(songName);
        _artistLabel.setText(artistName);

        final String durationString = (duration != null) ? Timer.durationToString(duration) : "";
        _durationLabel.setText(durationString);

        _isMouseOver = false;
        deselect();
    }


    /**
     * @return
     */
    public UUID getSongID() { return _songID; }

    /**
     * @return
     */
    public boolean isSelected() { return _isSelected; }


    /**
     * @param duration
     */
    public void setDuration(final Duration duration) {
        _durationLabel.setText(Timer.durationToString(duration));
    }


    /**
     *
     */
    public void select() {
        _isSelected = true;
        final String hex = "94baf7";
        final BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf(hex), CornerRadii.EMPTY, Insets.EMPTY);
        final Background background = new Background(backgroundFill);
        setBackground(background);
    }

    /**
     *
     */
    public void deselect() {
        _isSelected = false;
        setBackground(null);
    }


    /**
     * @param e
     */
    @FXML
    private void onClicked(MouseEvent e) {
        final ClickEvent event = new ClickEvent(this, e.getButton(), e.isShortcutDown(), e.isShiftDown());
        _onClick.invoke(event);
        select();
    }

    /**
     * @param e
     */
    @FXML
    private void onMouseEntered(MouseEvent e) {
        _isMouseOver = true;
        if (_isSelected)
            return;

        final String hex = "b8d3ff";
        final BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf(hex), CornerRadii.EMPTY, Insets.EMPTY);
        final Background background = new Background(backgroundFill);
        setBackground(background);
    }

    /**
     * @param e
     */
    @FXML
    private void onMouseExited(MouseEvent e) {
        _isMouseOver = false;
        if (_isSelected)
            return;

        setBackground(null);
    }
}
