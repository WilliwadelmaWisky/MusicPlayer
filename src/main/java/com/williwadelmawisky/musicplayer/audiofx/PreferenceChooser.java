package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 */
public class PreferenceChooser extends VBox {

    @FXML private PreferenceListView _listView;

    private String _dialogTitle;

    /**
     *
     */
    public PreferenceChooser() {
        super();
        ResourceLoader.loadFxml("fxml/preference_chooser.fxml");
    }


    /**
     * @param title
     */
    public void setTitle(final String title) { _dialogTitle = title; }


    /**
     *
     */
    public void showDialog() {

        final ModalWindow modalWindow = new ModalWindow(new Stage(), this);
        modalWindow.showAndWait();


    }


    /**
     * @param e
     */
    @FXML
    private void onSearch(final SearchField.SearchEvent e) {

    }
}
