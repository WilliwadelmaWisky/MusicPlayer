package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.utils.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;

/**
 *
 */
public class AudioClipView extends HBox {

    private final Label _nameLabel;
    private AudioClip _audioClip;


    /**
     *
     */
    public AudioClipView() {
        super();

        final ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(15);
        imageView.setImage(ResourceLoader.loadImage("img/logo.png"));
        this.getChildren().add(imageView);

        _nameLabel = new Label("");
        HBox.setHgrow(_nameLabel, Priority.ALWAYS);
        _nameLabel.setMaxWidth(Double.POSITIVE_INFINITY);

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        setOnContextMenuRequested(this::onContextMenuRequested);
    }

    /**
     * @param audioClip
     */
    public AudioClipView(final AudioClip audioClip) {
        this();
        setAudioClip(audioClip);
    }


    /**
     * @return
     */
    public AudioClip getAudioClip() { return _audioClip; }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        _audioClip = audioClip;

        audioClip.getName().OnChanged.addListener(this::onAudioClipNameChanged);
        audioClip.getArtist().OnChanged.addListener(this::onAudioClipArtistChanged);

        updateView(audioClip);
    }


    /**
     * @param audioClip
     */
    private void updateView(final AudioClip audioClip) {
        final String name = audioClip.getName().getValue();
        final String artist = audioClip.getArtist().getValue();
        final String text = artist.isEmpty() ? name : name + " - " + artist;
        _nameLabel.setText(text);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipNameChanged(final Object sender, final ObservableValue.OnChangedEventArgs<String> args) {
        updateView(_audioClip);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipArtistChanged(final Object sender, final ObservableValue.OnChangedEventArgs<String> args) {
        updateView(_audioClip);
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
