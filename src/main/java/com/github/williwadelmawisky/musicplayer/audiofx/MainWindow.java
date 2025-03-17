package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 *
 */
public class MainWindow {

    private final Stage _stage;


    /**
     * @param stage
     * @param file
     */
    public MainWindow(final Stage stage, final File file) {
        _stage = stage;

        final Parent root = ResourceLoader.loadFxml("fxml/main_window.fxml", (MainWindowController controller) -> {
            if (file != null && file.exists())
                controller.openUnknownFile(file);
        });

        final Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceLoader.loadCss("css/style.css"));
        _stage.getIcons().add(ResourceLoader.loadImage("img/logo.png"));
        _stage.setScene(scene);
    }


    /**
     *
     */
    public void show() {
        _stage.show();
    }
}
