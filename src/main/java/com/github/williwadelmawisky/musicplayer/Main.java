package com.github.williwadelmawisky.musicplayer;

import com.github.williwadelmawisky.musicplayer.core.control.audio.OrderSequencer;
import com.github.williwadelmawisky.musicplayer.core.db.Database;
import com.github.williwadelmawisky.musicplayer.core.db.FetchHandler;
import com.github.williwadelmawisky.musicplayer.routing.SessionStorage;
import com.github.williwadelmawisky.musicplayer.scene.pages.DashboardPage;
import com.github.williwadelmawisky.musicplayer.routing.Router;
import com.github.williwadelmawisky.musicplayer.scene.pages.EditPlaylistPage;
import com.github.williwadelmawisky.musicplayer.scene.pages.LibraryPage;
import com.github.williwadelmawisky.musicplayer.scene.pages.NotFoundPage;
import com.github.williwadelmawisky.musicplayer.stage.Window;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 */
public class Main extends Application {

    /**
     * @param stage
     */
    @Override
    public void start(final Stage stage) {
        stage.setTitle("Music Player");
        stage.setWidth(720);
        stage.setMinWidth(720);
        stage.setHeight(500);
        stage.setMinHeight(300);

        final Database database = new Database();
        final FetchHandler fetchHandler = new FetchHandler(database);

        final SessionStorage sessionStorage = new SessionStorage();
        final Router router = new Router(new NotFoundPage());
        router.addRoute("/", new DashboardPage(fetchHandler, router, sessionStorage));
        router.addRoute("/library", new LibraryPage(fetchHandler, router, sessionStorage));
        router.addRoute("/edit", new EditPlaylistPage(fetchHandler, router, sessionStorage));

        final Window window = new Window(stage, router);
        window.show();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
