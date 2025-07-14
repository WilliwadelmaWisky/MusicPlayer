package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.util.Files;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;

/**
 *
 */
public class PlaylistListViewEntry extends HBox {

    private final Label _nameLabel;
    private File _playlistFile;


    /**
     *
     */
    public PlaylistListViewEntry() {
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
     * @param playlistFile
     */
    public PlaylistListViewEntry(final File playlistFile) {
        this();
        setFile(playlistFile);
    }


    /**
     * @return
     */
    public File getFile() { return _playlistFile; }

    /**
     * @param file
     */
    public void setFile(final File file) {
        _playlistFile = file;
        _nameLabel.setText(Files.getNameWithoutExtension(file));
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
