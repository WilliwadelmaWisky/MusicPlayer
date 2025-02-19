package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.UUID;

/**
 *
 */
public class SongNode extends HBox {

    @FXML private Label _nameLabel;
    @FXML private Label _artistLabel;
    @FXML private Label _durationLabel;

    private UUID _songID;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongNode() {}

    /**
     * @param songID
     * @param songName
     * @param artistName
     * @param duration
     */
    public SongNode(final UUID songID, final String songName, final String artistName, final Duration duration) {
        this();

        ResourceLoader.loadFxml("fxml/controls/SongNode.fxml", this);

        _songID = songID;
        _nameLabel.setText(songName);
        _artistLabel.setText(artistName);

        final String durationString = (duration != null) ? Timer.durationToString(duration) : "";
        _durationLabel.setText(durationString);
    }


    /**
     * @return
     */
    public UUID getSongID() { return _songID; }


    /**
     * @param duration
     */
    public void setDuration(final Duration duration) {
        _durationLabel.setText(Timer.durationToString(duration));
    }
}
