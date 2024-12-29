package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.util.Action;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 *
 */
public class SongNode extends HBox {

    @FXML private Label _nameLabel;
    @FXML private Label _artistLabel;
    @FXML private Label _durationLabel;

    private EventHandler<MouseEvent> _onClick;
    private boolean _isSelected, _isMouseOver;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongNode() {}

    /**
     * @param songName
     * @param artistName
     * @param onClick
     */
    public SongNode(final String songName, final String artistName, final EventHandler<MouseEvent> onClick) {
        this();

        ResourceLoader.loadFxml("fxml/controls/SongNode.fxml", this);

        _onClick = onClick;
        _nameLabel.setText(songName);
        _artistLabel.setText(artistName);
        _durationLabel.setText("0:00");

        _isMouseOver = false;
        deselect();
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
        _onClick.handle(e);
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
