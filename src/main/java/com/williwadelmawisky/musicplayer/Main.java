package com.williwadelmawisky.musicplayer;

import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.audiofx.MainWindow;
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

    private static final String MUSIC_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), "Music").toString();
    private static final String CONFIG_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), ".config", "WilliwadelmaWisky", "MusicPlayer").toString();
    private static final double DEFAULT_VOLUME = 1.0;

    private AudioClipPlayer _audioClipPlayer;

    /**
     * @param stage
     */
    @Override
    public void start(final Stage stage) {
        _audioClipPlayer = new AudioClipPlayer(DEFAULT_VOLUME);

        final File CONFIG_DIRECTORY = new File(CONFIG_DIRECTORY_PATH);
        if (!CONFIG_DIRECTORY.exists())
            CONFIG_DIRECTORY.mkdirs();

        stage.setTitle("Music Player");
        stage.setWidth(640);
        stage.setMinWidth(640);
        stage.setHeight(400);
        stage.setMinHeight(300);

        final MainWindow mainWindow = new MainWindow(stage, _audioClipPlayer);
        final List<String> args = this.getParameters().getRaw();
        if (!args.isEmpty()) {
            final File file = Path.of(args.getFirst()).toAbsolutePath().toFile();
            mainWindow.load(file);
        }

        mainWindow.show();
    }


    /**
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(Main.class, args);
    }
}
