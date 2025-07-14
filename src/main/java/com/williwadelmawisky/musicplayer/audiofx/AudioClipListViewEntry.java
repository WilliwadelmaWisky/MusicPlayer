package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.ResourceLoader;
import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.util.Durations;
import com.williwadelmawisky.musicplayer.util.Files;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;

import java.io.File;

/**
 *
 */
public class AudioClipListViewEntry extends HBox {

    private final Label _nameLabel, _durationLabel;
    private AudioClip _audioClip;


    /**
     *
     */
    public AudioClipListViewEntry() {
        super();

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        setOnContextMenuRequested(this::onContextMenuRequested);

        final ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(15);
        imageView.setImage(ResourceLoader.loadImage("img/logo.png"));
        this.getChildren().add(imageView);

        _nameLabel = new Label();
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);
        getChildren().add(_nameLabel);

        _durationLabel = new Label();
        _durationLabel.setAlignment(Pos.CENTER_RIGHT);
        _durationLabel.setMinWidth(50);
        getChildren().add(_durationLabel);
    }

    /**
     * @param audioClip
     */
    public AudioClipListViewEntry(final AudioClip audioClip) {
        this();
        onCreated(audioClip);
    }


    /**
     * @return
     */
    public AudioClip getAudioClip() { return _audioClip; }


    /**
     * @param audioClip
     */
    void onCreated(final AudioClip audioClip) {
        _audioClip = audioClip;

        _audioClip.OnReady.addListener(this::onReady_AudioClip);

        updateView(audioClip);
    }

    /**
     *
     */
    void onDestroy() {
        if (_audioClip == null)
            return;

        _audioClip.OnReady.removeListener(this::onReady_AudioClip);
    }


    /**
     * @param audioClip
     */
    private void updateView(final AudioClip audioClip) {
        final File file = new File(audioClip.getAbsoluteFilePath());
        final String name = Files.getNameWithoutExtension(file);
        _nameLabel.setText(name);

        if (_audioClip.isReady()) {
            final String durationString = Durations.durationToString(_audioClip.getTotalDuration());
            _durationLabel.setText(durationString);
        }
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


    /**
     * @param sender
     * @param args
     */
    private void onReady_AudioClip(final Object sender, final EventArgs args) {
        final String durationString = Durations.durationToString(_audioClip.getTotalDuration());
        _durationLabel.setText(durationString);
    }
}
