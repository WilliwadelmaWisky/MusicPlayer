package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.audio.Genre;
import com.github.williwadelmawisky.musicplayer.audio.Language;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 *
 */
public class AudioClipEditor extends VBox {

    private final ListView<HBox> _listView;
    private final TextField _nameTextField;
    private final TextField _artistTextField;
    private final SearchableComboBox<Genre> _genreComboBox;
    private final SearchableComboBox<Language> _languageComboBox;

    private String _title;
    private AudioClip _audioClip;

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
     *
     */
    public AudioClipEditor() {
        super();

        _listView = new ListView<>();

        _nameTextField = new TextField();

        _artistTextField = new TextField();

        _genreComboBox = new SearchableComboBox<>();
        _genreComboBox.getItems().addAll(Genre.values());

        _languageComboBox = new SearchableComboBox<>();
        _languageComboBox.getItems().addAll(Language.values());
    }

    /**
     * @param title
     */
    public AudioClipEditor(final String title) {
        this();
        setTitle(title);
    }

    /**
     * @param e
     */
    private void onApply(ActionEvent e) {
        final String name = _nameTextField.getText().trim();
        final String artist = _artistTextField.getText().trim();
        final Genre genre = _genreComboBox.getValue();
        final Language language = _languageComboBox.getValue();

        if (name.isEmpty() || artist.isEmpty() || genre == null || language == null)
            return;

        _audioClip.getName().setValue(name);
        _audioClip.getArtist().setValue(artist);
        _audioClip.getGenre().setValue(genre);
        _audioClip.getLanguage().setValue(language);
    }


    /**
     * @param title
     */
    public void setTitle(final String title) { _title = title; }


    /**
     *
     */
    public void showDialog() {
        final ModalWindow modalWindow = new ModalWindow(new Stage(), _title, this);
        modalWindow.show();
    }
}
