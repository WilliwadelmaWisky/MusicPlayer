package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.database.FetchGetHandler;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.routing.RedirectHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.GridView;

import java.util.Arrays;

/**
 *
 */
public class LibraryPage extends VBox implements Page {

    @FXML private GridView<String> _gridView;

    private FetchGetHandler _fetchHandler;
    private RedirectHandler _redirectHandler;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public LibraryPage() {}

    /**
     * @param fetchHandler
     * @param redirectHandler
     */
    public LibraryPage(final FetchGetHandler fetchHandler, final RedirectHandler redirectHandler) {
        super();

        ResourceLoader.loadFxml("fxml/pages/LibraryPage.fxml", this);

        _fetchHandler = fetchHandler;
        _redirectHandler = redirectHandler;
        
        final ObservableList<String> items = FXCollections.observableList(Arrays.asList("Hello", "World"));
        _gridView.setItems(items);
    }


    /**
     * @param e
     */
    @FXML
    private void onDashboardPageButtonClicked(ActionEvent e) {
        _redirectHandler.setRoute("/");
    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }
}
