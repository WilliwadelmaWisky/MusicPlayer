package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 */
public class AudioControlPanel extends VBox {

    private final Label _titleLabel, _artistLabel;
    private final AudioProgressView _audioProgressView;
    private final AudioButtonView _audioButtonView;
    private final VolumeSliderView _volumeSliderView;


    /**
     *
     */
    public AudioControlPanel() {
        super();

        _audioProgressView = new AudioProgressView();
        _audioProgressView.setAlignment(Pos.BOTTOM_LEFT);
        _audioProgressView.setSpacing(5);
        this.getChildren().add(_audioProgressView);

        final HBox hbox = new HBox();
        hbox.setSpacing(10);
        this.getChildren().add(hbox);

        final Region leftRegion = new Region();
        leftRegion.setMinWidth(40);
        leftRegion.setMaxHeight(40);
        hbox.getChildren().add(leftRegion);

        final VBox vbox = new VBox();
        vbox.setSpacing(2);
        vbox.setMinWidth(200);
        vbox.setMinWidth(200);
        hbox.getChildren().add(vbox);

        _titleLabel = new Label();
        vbox.getChildren().add(_titleLabel);

        _artistLabel = new Label();
        vbox.getChildren().add(_artistLabel);

        _audioButtonView = new AudioButtonView();
        _audioButtonView.setAlignment(Pos.CENTER);
        HBox.setHgrow(_audioButtonView, Priority.ALWAYS);
        _audioButtonView.setMaxWidth(Double.POSITIVE_INFINITY);
        hbox.getChildren().add(_audioButtonView);

        _volumeSliderView = new VolumeSliderView();
        _volumeSliderView.setAlignment(Pos.CENTER_LEFT);
        _volumeSliderView.setMinWidth(200);
        _volumeSliderView.setMaxWidth(200);
        hbox.getChildren().add(_volumeSliderView);

        final Region rightRegion = new Region();
        rightRegion.setMinWidth(40);
        rightRegion.setMaxHeight(40);
        hbox.getChildren().add(rightRegion);

        getStyleClass().add("content-box");
        setPadding(new Insets(10));
        setSpacing(10);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipListPlayer audioClipPlayer) {
        _audioProgressView.bindTo(audioClipPlayer);
        _volumeSliderView.bindTo(audioClipPlayer);
        _audioButtonView.bindTo(audioClipPlayer);

        audioClipPlayer.OnAudioClipStarted.addListener(this::onAudioClipStarted);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipStarted(final Object sender, final AudioClipPlayer.OnAudioClipStartedEventArgs args) {
        _titleLabel.setText(args.AudioClip.getName());
        _artistLabel.setText(args.AudioClip.getArtist());
    }
}
