package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylist;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class AudioPlaylistListViewEntry extends HBox {

    private final Label _nameLabel;
    private AudioPlaylist _audioPlaylist;


    /**
     *
     */
    public AudioPlaylistListViewEntry() {
        super();

        final ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(15);
        imageView.setImage(ResourceLoader.loadImage("img/logo.png"));
        this.getChildren().add(imageView);

        _nameLabel = new Label();
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);

        setSpacing(5);
        setAlignment(Pos.CENTER_LEFT);
        setOnContextMenuRequested(this::onContextMenuRequested);
    }

    /**
     * @param audioPlaylist
     */
    public AudioPlaylistListViewEntry(final AudioPlaylist audioPlaylist) {
        this();
        setAudioPlaylist(audioPlaylist);
    }


    /**
     * @return
     */
    public AudioPlaylist getAudioPlaylist() { return _audioPlaylist; }


    /**
     * @param audioPlaylist
     */
    public void setAudioPlaylist(final AudioPlaylist audioPlaylist) {
        _audioPlaylist = audioPlaylist;
        _nameLabel.setText(audioPlaylist.getName());
    }

    /**
     *
     */
    public void destroy() {

    }


    /**
     * @param e
     */
    private void onContextMenuRequested(final ContextMenuEvent e) {
        final MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(actionEvent -> System.out.println("Edit"));

        final MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(actionEvent -> System.out.println("Delete"));

        final ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        contextMenu.show(this, 0, 0);
    }
}
