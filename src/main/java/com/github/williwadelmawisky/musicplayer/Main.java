package com.github.williwadelmawisky.musicplayer;

import com.github.williwadelmawisky.musicplayer.audiofx.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
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

        final List<String> args = this.getParameters().getRaw();
        final File file = args.isEmpty() ? null : Path.of(args.getFirst()).toAbsolutePath().toFile();
        final MainWindow mainWindow = new MainWindow(stage, file);
        mainWindow.show();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
