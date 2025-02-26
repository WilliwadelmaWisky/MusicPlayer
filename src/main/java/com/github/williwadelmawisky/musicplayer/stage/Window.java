package com.github.williwadelmawisky.musicplayer.stage;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.RouteProperty;
import com.github.williwadelmawisky.musicplayer.routing.Router;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */
public class Window {

    protected final Stage _stage;
    protected final Router _router;
    private Page _currentPage;


    /**
     * @param stage
     * @param router
     */
    public Window(final Stage stage, final Router router) {
        _stage = stage;
        _router = router;

        final String route = _router.getRouteProperty().getValue();
        _currentPage = _router.getPage(route);
        final Scene scene = new Scene(_currentPage.getRoot());
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.setScene(scene);

        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));

        _router.getRouteProperty().getChangeDelegate().addListener(this::onRouteChanged);
    }


    /**
     * @param changeEvent
     */
    private void onRouteChanged(final RouteProperty.ChangeEvent changeEvent) {
        if (_currentPage != null) _currentPage.onUnload();

        _currentPage = _router.getPage(changeEvent.Route);
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
