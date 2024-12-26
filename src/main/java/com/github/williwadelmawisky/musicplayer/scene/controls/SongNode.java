package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.util.Action;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 */
public class SongNode extends VBox {

    @FXML private Label _nameLabel;
    @FXML private Label _artistLabel;

    private Action _onClick;
    private boolean _isHighlighted;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public SongNode() {}

    /**
     * @param songName
     * @param artistName
     * @param onClick
     */
    public SongNode(final String songName, final String artistName, final Action onClick) {
        this();

        ResourceLoader.loadFxml("fxml/controls/PlaylistNode.fxml", this);

        _onClick = onClick;
        DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.color(0, 0, 0, 0.8), 5, 0, 2, 2);
        setEffect(dropShadow);

        _isHighlighted = true;
        highlight(false);

        _nameLabel.setText(songName);
        _artistLabel.setText(artistName);
    }


    /**
     * @param e
     */
    @FXML
    private void onClicked(MouseEvent e) {
        if (e.getButton() != MouseButton.PRIMARY)
            return;

        _onClick.invoke();
        highlight(true);
    }


    /**
     * @param highlight
     */
    public void highlight(boolean highlight) {
        if (_isHighlighted == highlight)
            return;

        _isHighlighted = highlight;
        String hex = highlight ? "94baf7" : "dcdcdc";
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf(hex), new CornerRadii(10), Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
    }
}
