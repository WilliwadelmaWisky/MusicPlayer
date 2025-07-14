package com.williwadelmawisky.musicplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 */
public abstract class ResourceLoader {

    /**
     * @param relativePath
     * @return
     */
    public static Parent loadFxml(final String relativePath) {
        return loadFxml(relativePath, controller -> {});
    }

    /**
     * @param relativePath
     * @param action
     * @param <T>
     * @return
     */
    public static <T> Parent loadFxml(final String relativePath, final Consumer<? super T> action) {
        try {
            final FXMLLoader loader = new FXMLLoader(ResourceLoader.class.getResource(relativePath));
            final Parent root = loader.load();
            action.accept(loader.getController());
            return root;
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
            final FXMLLoader loader = new FXMLLoader(ResourceLoader.class.getResource(relativePath));
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
        return loadMedia(file);
    }

    /**
     * @param file
     * @return
     */
    public static Media loadMedia(final File file) {
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
