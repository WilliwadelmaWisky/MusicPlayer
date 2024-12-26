package com.github.williwadelmawisky.musicplayer.stage;

import com.github.williwadelmawisky.musicplayer.routing.Page;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 *
 */
public class ModalWindow {

    private static final String STYLESHEET = "/css/style.css";
    private static final String LOGO= "/img/logo.png";

    private final Stage _stage;


    /**
     * @param title
     * @param page
     */
    public ModalWindow(final String title, final Page page) {
        _stage = new Stage();
        _stage.setTitle(title);

        Scene scene = new Scene(page.getRoot());
        scene.getStylesheets().add(STYLESHEET);
        _stage.setScene(scene);

        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO))));
    }


    /**
     *
     */
    public void show() {
        _stage.showAndWait();
    }
}
