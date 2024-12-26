package com.github.williwadelmawisky.musicplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public abstract class ResourceLoader {

    /**
     * @param relativePath
     * @return
     */
    public static Parent loadFxml(final String relativePath) {
        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.class.getResource(relativePath));
            return loader.load();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * @param relativePath
     * @param root
     */
    public static void loadFxml(final String relativePath, final Object root) {
        try {
            FXMLLoader loader = new FXMLLoader(ResourceLoader.class.getResource(relativePath));
            loader.setController(root);
            loader.setRoot(root);
            loader.load();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }


    /**
     * @param path
     * @return
     */
    public static Media loadMedia(final String path) {
        final File file = new File(path);
        return new Media(file.toURI().toString());
    }

    /**
     * @param relativePath
     * @return
     */
    public static Image loadImage(final String relativePath) {
        return new Image(Objects.requireNonNull(ResourceLoader.class.getResourceAsStream(relativePath)));
    }

    /**
     * @param relativePath
     * @return
     */
    public static String loadCss(final String relativePath) {
        return Objects.requireNonNull(ResourceLoader.class.getResource(relativePath)).toString();
    }
}
