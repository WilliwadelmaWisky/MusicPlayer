package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 */
public class PlaylistNode extends HBox {

    @FXML private Label _nameLabel;

    private Action _onClick;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public PlaylistNode() {}

    /**
     * @param playlistName
     */
    public PlaylistNode(final String playlistName, final Action onClick) {
        this();

        ResourceLoader.loadFxml("fxml/controls/PlaylistNode.fxml", this);

        _onClick = onClick;
        _nameLabel.setText(playlistName);
    }


    /**
     * @param e
     */
    @FXML
    private void onClicked(ActionEvent e) {
        _onClick.invoke();
    }
}
