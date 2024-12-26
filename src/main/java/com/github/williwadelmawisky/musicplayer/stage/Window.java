package com.github.williwadelmawisky.musicplayer.stage;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.Router;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class Window {

    protected final Stage _stage;
    private final Router _router;


    /**
     * @param stage
     * @param router
     */
    public Window(Stage stage, Router router) {
        _stage = stage;
        _router = router;

        Scene scene = new Scene(_router.getRoute().getPage().getRoot());
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.setScene(scene);

        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));

        _router.setOnRouteSelected(this::onRouteSelected);
    }


    /**
     * @param page
     */
    private void onRouteSelected(final Page page) {
        setRoot(page.getRoot());
    }

    /**
     * @param root
     */
    public void setRoot(final Parent root) {
        _stage.getScene().setRoot(root);
    }


    /**
     *
     */
    public void show() {
        _stage.show();
    }
}
