package com.github.williwadelmawisky.musicplayer.stage;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.routing.Page;
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
     * @param page
     */
    public ModalWindow(final String title, final Page page) {
        _stage = new Stage();
        _stage.setTitle(title);

        Scene scene = new Scene(page.getRoot());
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.setScene(scene);

        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));
    }


    /**
     *
     */
    public void show() {
        _stage.showAndWait();
    }
}
