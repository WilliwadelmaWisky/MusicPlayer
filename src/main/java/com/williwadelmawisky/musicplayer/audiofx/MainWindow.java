package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.util.function.Predicate;

/**
 *
 */
public class MainWindow {

    private final Stage _stage;
    private Predicate<File> _loadEvent;


    /**
     * @param stage
     * @param audioClipPlayer
     */
    public MainWindow(final Stage stage, final AudioClipPlayer audioClipPlayer) {
        _stage = stage;

        final Parent root = ResourceLoader.loadFxml("fxml/main_window.fxml", (MainWindowController controller) -> {
            controller.onCreated(audioClipPlayer);
            _loadEvent = controller::load;
        });

        final Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));
        _stage.setScene(scene);
    }


    /**
     * @param file
     * @return
     */
    public boolean load(final File file) {
        if (file == null || !file.exists()) {
            final String message = "Incorrect file path!";
            final Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
            return false;
        }

        boolean sucess = _loadEvent.test(file);
        if (sucess)
            return true;

        final String message = file.isDirectory() ? "The directory contains no audio files!" : "The file is not an audio file!";
        final Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
        return false;
    }


    /**
     *
     */
    public void show() {
        _stage.show();
    }
}
