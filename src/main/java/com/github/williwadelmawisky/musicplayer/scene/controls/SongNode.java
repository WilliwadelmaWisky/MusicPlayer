package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.UUID;

/**
 *
 */
public class SongNode extends HBox {

    @FXML private Label _nameLabel;

    private UUID _songID;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongNode() {}

    /**
     * @param songID
     * @param songName
     * @param artistName
     */
    public SongNode(final UUID songID, final String songName, final String artistName) {
        this();

        ResourceLoader.loadFxml("fxml/controls/SongNode.fxml", this);

        _songID = songID;
        _nameLabel.setText(songName + " - " + artistName);
    }


    /**
     * @return
     */
    public UUID getSongID() { return _songID; }
}
