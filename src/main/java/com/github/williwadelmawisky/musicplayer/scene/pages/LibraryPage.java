package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 *
 */
public class LibraryPage extends VBox implements Page {


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public LibraryPage() {}

    /**
     * @param library
     */
    public LibraryPage(final String library) {
        super();

        ResourceLoader.loadFxml("fxml/pages/LibraryPage.fxml", this);
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
