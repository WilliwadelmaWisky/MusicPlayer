package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.database.*;
import com.github.williwadelmawisky.musicplayer.routing.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class AudioClipEditor extends VBox {


    @FXML private TextField _nameTextField;
    @FXML private ComboBox<String> _genreComboBox;
    @FXML private ArtistSelector _artistSelector;

    private SongData _songData;
    private Action _onApply;
    private FetchGetHandler _fetchHandler;

    /*
    <fx:root type="SongEditPage"
         xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
         spacing="10"
>
    <padding>
        <Insets topRightBottomLeft="10"/>
    </padding>
    <ScrollPane fitToWidth="true" VBox.vgrow="ALWAYS" maxHeight="1000">
        <VBox>
            <padding>
                <Insets topRightBottomLeft="5"/>
            </padding>
            <VBox spacing="5">
                <HBox alignment="CENTER_LEFT">
                    <Label text="Name" minWidth="80" maxWidth="80"/>
                    <TextField promptText="Enter the songData name..." HBox.hgrow="ALWAYS" maxWidth="Infinity" fx:id="_nameTextField"/>
                </HBox>
                <HBox alignment="CENTER_LEFT">
                    <Label text="Genre" minWidth="80" maxWidth="80"/>
                    <SearchableComboBox HBox.hgrow="ALWAYS" maxWidth="Infinity" fx:id="_genreComboBox"/>
                </HBox>
                <HBox alignment="CENTER_LEFT">
                    <Label text="Artist" minWidth="80" maxWidth="80"/>
                    <ArtistSelector HBox.hgrow="ALWAYS" maxWidth="Infinity" fx:id="_artistSelector"/>
                </HBox>
            </VBox>
        </VBox>
    </ScrollPane>
    <HBox spacing="5">
        <Button text="Apply" onAction="#onApply"/>
        <Button text="Cancel" onAction="#onCancel"/>
    </HBox>
</fx:root>
     */


    /**
     * NEVER USE, EXISTS ONLY TO KEEP FXML HAPPY
     */
    public AudioClipEditor() {}

    /**
     * @param songData
     * @param fetchHandler
     * @param onApply
     */
    public AudioClipEditor(final SongData songData, final FetchGetHandler fetchHandler, final Action onApply) {
        super();

        ResourceLoader.loadFxml("fxml/pages/SongEditPage.fxml", this);

        _songData = songData;
        _onApply = onApply;
        _fetchHandler = fetchHandler;

        _nameTextField.setText(_songData.getName());

        Iterable<ArtistData> artists = fetchHandler.fetchGET(Database.TableType.ARTIST);
        _artistSelector.setItems(artists);
        _artistSelector.setValue(_songData.getArtistID());

        List<String> values = Arrays.stream(Genre.values()).map(Enum::name).toList();
        ObservableList<String> itemList = FXCollections.observableList(values);
        _genreComboBox.setItems(itemList);
        _genreComboBox.setValue(songData.getGenre().name());
    }

    /**
     * @param e
     */
    @FXML
    private void onApply(ActionEvent e) {
        final String songName = _nameTextField.getText().trim();
        if (songName.isEmpty())
            return;

        final UUID artistID = _artistSelector.getValue();
        if (artistID == null)
            return;

        final Genre genre = Genre.valueOf(_genreComboBox.getValue());

        _songData.setName(songName);
        _songData.setArtistID(artistID);
        _songData.setGenre(genre);

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

    @Override
    public void onLoad() {

    }

    @Override
    public void onUnload() {

    }
}
