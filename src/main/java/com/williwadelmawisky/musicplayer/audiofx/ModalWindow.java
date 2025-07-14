package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 */
public class ModalWindow {

    private final Stage _stage;


    /**
     * @param title
     * @param root
     */
    public ModalWindow(final String title, final Parent root) {
        this(new Stage(), title, root);
    }

    /**
     * @param stage
     * @param title
     * @param root
     */
    public ModalWindow(final Stage stage, final String title, final Parent root) {
        _stage = stage;
        _stage.setTitle(title);

        final Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));
        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.setScene(scene);
    }


    /**
     *
     */
    public void showAndWait() {
        _stage.showAndWait();
    }
}
