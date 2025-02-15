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
    private Page _currentPage;


    /**
     * @param stage
     * @param router
     */
    public Window(final Stage stage, final Router router) {
        _stage = stage;

        _currentPage = router.getRoute().getPage();
        Scene scene = new Scene(_currentPage.getRoot());
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.setScene(scene);

        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));

        router.setOnRouteSelected(this::onRouteSelected);
    }


    /**
     * @param page
     */
    private void onRouteSelected(final Page page) {
        if (_currentPage != null)
            _currentPage.onUnload();

        _currentPage = page;
        setRoot(_currentPage.getRoot());
        _currentPage.onLoad();
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
