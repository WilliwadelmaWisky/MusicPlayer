package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClip;
import com.github.williwadelmawisky.musicplayer.audio.AudioPlaylist;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 */
public class AudioPlaylistView extends HBox {

    private final Label _nameLabel;
    private AudioPlaylist _audioPlaylist;


    /**
     *
     */
    public AudioPlaylistView() {
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

        setAudioPlaylist(null);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
    }

    /**
     * @param audioPlaylist
     */
    public AudioPlaylistView(final AudioPlaylist audioPlaylist) {
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
        updateView(audioPlaylist);
    }


    /**
     * @param audioPlaylist
     */
    private void updateView(final AudioPlaylist audioPlaylist) {
        final String text = audioPlaylist != null ? audioPlaylist.getName() : "";
        _nameLabel.setText(text);
    }
}
