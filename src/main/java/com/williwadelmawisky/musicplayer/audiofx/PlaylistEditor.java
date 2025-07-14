package com.williwadelmawisky.musicplayer.audiofx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 *
 */
public class PlaylistEditor extends VBox {

    private String _title;
    private File _directory;


    /**
     *
     */
    public PlaylistEditor() {
        super();

        setPadding(new Insets(10));
        setSpacing(5);

        final Label label = new Label("Playlist");
        this.getChildren().add(label);

        final TextField textField = new TextField();
        textField.setPromptText("Name for the playlist...");
        this.getChildren().add(textField);

        final HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setSpacing(5);
        this.getChildren().add(hbox);

        final Button cancelButton = new Button("Cancel");
        hbox.getChildren().add(cancelButton);

        final Button saveButton = new Button("Save");
        hbox.getChildren().add(saveButton);
    }

    /**
     * @param title
     */
    public PlaylistEditor(final String title) {
        this();
        setTitle(title);
    }

    /**
     * @param title
     * @param initialDirectory
     */
    public PlaylistEditor(final String title, final File initialDirectory) {
        this(title);
        setInitialDirectory(initialDirectory);
    }


    /**
     * @param title
     */
    public void setTitle(final String title) { _title = title; }

    /**
     * @param directory
     */
    public void setInitialDirectory(final File directory) { _directory = directory; }


    /**
     *
     */
    public void showDialog() {
        final ModalWindow modalWindow = new ModalWindow(new Stage(), _title, this);
        modalWindow.showAndWait();
    }
}
