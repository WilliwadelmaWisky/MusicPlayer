package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.PlaylistInfo;
import com.williwadelmawisky.musicplayer.audio.PlaylistInfoModel;
import com.williwadelmawisky.musicplayer.utilfx.SearchField;

import javafx.event.ActionEvent;
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

    private final PlaylistInfoModel _playlistInfoModel;
    private String _windowTitle;


    /**
     *
     */
    public PlaylistChooser() {
        super();

        _playlistInfoModel = new PlaylistInfoModel();

        _searchField = new SearchField();
        _searchField.setAlignment(Pos.CENTER_RIGHT);
        _playlistListView = new PlaylistListView();
        VBox.setVgrow(_playlistListView, Priority.ALWAYS);
        _playlistListView.setMaxHeight(Double.POSITIVE_INFINITY);
        _playlistListView.onCreated(_playlistInfoModel);

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
     * @return
     */
    public PlaylistInfo showOpenDialog() {
        final Stage stage = new Stage();
        stage.setTitle(_windowTitle);
        stage.setWidth(400);
        stage.setHeight(500);
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> _playlistInfoModel.SelectionModel.clearSelection());

        final ModalWindow modalWindow = new ModalWindow(stage, this);
        modalWindow.showAndWait();

        if (!_playlistInfoModel.SelectionModel.hasValue())
            return null;

        return _playlistInfoModel.SelectionModel.getValue();
    }


    /**
     * @param e
     */
    private void onClick_OkButton(final ActionEvent e) {
        if (_playlistInfoModel.SelectionModel.hasValue())
            this.getScene().getWindow().hide();
    }

    /**
     * @param e
     */
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
