package com.github.williwadelmawisky.musicplayer;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import com.github.williwadelmawisky.musicplayer.database.Database;
import com.github.williwadelmawisky.musicplayer.database.FetchHandler;
import com.github.williwadelmawisky.musicplayer.scene.pages.DashboardPage;
import com.github.williwadelmawisky.musicplayer.routing.Router;
import com.github.williwadelmawisky.musicplayer.scene.pages.LibraryPage;
import com.github.williwadelmawisky.musicplayer.stage.Window;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

        final String path = Paths.get(System.getProperty("user.home"), ".WilliwadelmaWisky", "MusicPlayer").toString();
        final Database database = new Database(path);
        database.load();

        final FetchHandler fetchHandler = new FetchHandler(database);
        final AudioClipListPlayer listAudioClipPlayer = new AudioClipListPlayer();

        final Router router = new Router();
        final DashboardPage dashboardPage = new DashboardPage(fetchHandler, router, listAudioClipPlayer);
        final LibraryPage libraryPage = new LibraryPage(fetchHandler, router);

        router.addRoute("/", dashboardPage);
        router.addRoute("/library", libraryPage);

        final List<String> args = this.getParameters().getRaw();
        if (!args.isEmpty()) {
            final File file = Path.of(args.getFirst()).toAbsolutePath().toFile();
            if (file.exists()) dashboardPage.open(file);
        }

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
