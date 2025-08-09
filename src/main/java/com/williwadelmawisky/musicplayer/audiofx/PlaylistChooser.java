package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfoModel;
import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 *
 */
public class PlaylistChooser extends VBox {

    private final SearchField _searchField;
    private final PlaylistListView _playlistListView;

    private PlaylistInfoModel _playlistInfoModel;
    private String _windowTitle;


    /**
     *
     */
    public PlaylistChooser() {
        super();

        _searchField = new SearchField();
        _playlistListView = new PlaylistListView();
        VBox.setVgrow(_playlistListView, Priority.ALWAYS);
        _playlistListView.setMaxHeight(Double.POSITIVE_INFINITY);

        final HBox buttonBox = new HBox();
        final Button cancelButton = new Button("Cancel");
        final Button okButton = new Button("Select");
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(cancelButton, okButton);

        setSpacing(10);
        setPadding(new Insets(10));
        getChildren().addAll(_searchField, _playlistListView, buttonBox);

        _searchField.setOnSearch(this::onSearch_SearchField);
        okButton.setOnAction(this::onClick_OkButton);
        cancelButton.setOnAction(this::onClick_CancelButton);
    }


    /**
     * @param title
     */
    public void setTitle(final String title) { _windowTitle = title; }

    /**
     * @param playlistInfoModel
     */
    public void setPlaylistModel(final PlaylistInfoModel playlistInfoModel) {
        _playlistInfoModel = playlistInfoModel;

        _playlistListView.setPlaylistModel(_playlistInfoModel);
    }


    /**
     * @return
     */
    public PlaylistInfo showOpenDialog() {
        final ModalWindow modalWindow = new ModalWindow(new Stage(), _windowTitle, this);
        modalWindow.showAndWait();

        if (!_playlistInfoModel.SelectionModel.hasValue())
            return null;

        return _playlistInfoModel.SelectionModel.getValue();
    }


    /**
     * @param e
     */
    @FXML
    private void onClick_OkButton(final ActionEvent e) {
        this.getScene().getWindow().hide();
    }

    /**
     * @param e
     */
    @FXML
    private void onClick_CancelButton(final ActionEvent e) {
        _playlistInfoModel.SelectionModel.clearSelection();
        this.getScene().getWindow().hide();
    }


    /**
     * @param e
     */
    private void onSearch_SearchField(final SearchField.SearchEvent e) {
        final List<PlaylistInfo> searchList = _playlistInfoModel.PlaylistInfoList.filter(playlistInfo -> playlistInfo.name().toLowerCase().contains(e.getSearchString().toLowerCase()));
        _playlistListView.generateEntries(searchList);
    }
}
