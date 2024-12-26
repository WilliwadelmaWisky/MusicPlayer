package com.github.williwadelmawisky.musicplayer.scene.pages;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.data.Playlist;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import com.github.williwadelmawisky.musicplayer.util.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 */
public class PlaylistEditPage extends VBox implements Page {

    @FXML private TextField _nameTextField;

    private Playlist _playlist;
    private Action _onApply;


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public PlaylistEditPage() {}

    /**
     * @param playlist
     * @param onApply
     */
    public PlaylistEditPage(Playlist playlist, final Action onApply) {
        super();

        ResourceLoader.loadFxml("fxml/pages/PlaylistEditPage.fxml", this);

        _playlist = playlist;
        _onApply = onApply;
    }


    /**
     * @param e
     */
    @FXML
    private void onApply(ActionEvent e) {
        final String name = _nameTextField.getText().trim();
        if (name.isEmpty())
            return;

        _playlist.setName(name);
        _onApply.invoke();
    }

    /**
     * @param e
     */
    @FXML
    private void onCancel(ActionEvent e) {

    }


    /**
     * @return
     */
    @Override
    public Parent getRoot() {
        return this;
    }
}
