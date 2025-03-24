package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.audio.AudioClipListPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.utils.ObservableValue;
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
    private final AudioPlaybackProgressView _audioPlaybackProgressView;
    private final AudioPlaybackControlView _audioPlaybackControlView;
    private final VolumeSliderView _volumeSliderView;

    private AudioClipListPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioControlPanel() {
        super();

        _audioPlaybackProgressView = new AudioPlaybackProgressView();
        _audioPlaybackProgressView.setAlignment(Pos.BOTTOM_LEFT);
        _audioPlaybackProgressView.setSpacing(5);
        this.getChildren().add(_audioPlaybackProgressView);

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

        _audioPlaybackControlView = new AudioPlaybackControlView();
        _audioPlaybackControlView.setAlignment(Pos.CENTER);
        HBox.setHgrow(_audioPlaybackControlView, Priority.ALWAYS);
        _audioPlaybackControlView.setMaxWidth(Double.POSITIVE_INFINITY);
        hbox.getChildren().add(_audioPlaybackControlView);

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
        _audioClipPlayer = audioClipPlayer;
        audioClipPlayer.OnAudioClipStarted.addListener(this::onAudioClipStarted);

        _audioPlaybackProgressView.bindTo(audioClipPlayer);
        _volumeSliderView.bindTo(audioClipPlayer);
        _audioPlaybackControlView.bindTo(audioClipPlayer);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipStarted(final Object sender, final AudioClipPlayer.OnAudioClipStartedEventArgs args) {
        _titleLabel.setText(args.AudioClip.getName().getValue());
        _artistLabel.setText(args.AudioClip.getArtist().getValue());

        args.AudioClip.getName().OnChanged.addListener(this::onAudioClipNameChanged);
        args.AudioClip.getArtist().OnChanged.addListener(this::onAudioClipArtistChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAudioClipNameChanged(final Object sender, final ObservableValue.OnChangedEventArgs<String> args) {
        _titleLabel.setText(args.Value);
    }

    /**
     * @param sender
     * @param args
     */
    private void onAudioClipArtistChanged(final Object sender, final ObservableValue.OnChangedEventArgs<String> args) {
        _artistLabel.setText(args.Value);
    }
}
