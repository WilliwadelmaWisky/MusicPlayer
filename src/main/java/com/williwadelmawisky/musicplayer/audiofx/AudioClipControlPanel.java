package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.util.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 *
 */
public class AudioClipControlPanel extends VBox {

    private final Label _titleLabel, _artistLabel;
    private final ProgressControlPanel _progressControlPanel;
    private final PlaybackControlPanel _playbackControlPanel;
    private final VolumeControlPanel _volumeControlPanel;

    private EventHandler<ActionEvent> _onPrevious;
    private EventHandler<ActionEvent> _onNext;
    private AudioClip _audioClip;


    /**
     *
     */
    public AudioClipControlPanel() {
        super();

        _progressControlPanel = new ProgressControlPanel();
        _progressControlPanel.setAlignment(Pos.BOTTOM_LEFT);
        _progressControlPanel.setSpacing(5);
        this.getChildren().add(_progressControlPanel);

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

        _playbackControlPanel = new PlaybackControlPanel();
        _playbackControlPanel.setAlignment(Pos.CENTER);
        HBox.setHgrow(_playbackControlPanel, Priority.ALWAYS);
        _playbackControlPanel.setMaxWidth(Double.POSITIVE_INFINITY);
        _playbackControlPanel.setOnPrevious(_onPrevious);
        _playbackControlPanel.setOnNext(_onNext);
        hbox.getChildren().add(_playbackControlPanel);

        _volumeControlPanel = new VolumeControlPanel();
        _volumeControlPanel.setAlignment(Pos.CENTER_LEFT);
        _volumeControlPanel.setMinWidth(200);
        _volumeControlPanel.setMaxWidth(200);
        hbox.getChildren().add(_volumeControlPanel);

        final Region rightRegion = new Region();
        rightRegion.setMinWidth(40);
        rightRegion.setMaxHeight(40);
        hbox.getChildren().add(rightRegion);

        getStyleClass().add("content-box");
        setPadding(new Insets(10));
        setSpacing(10);
    }


    /**
     *
     */
    public void unbind() {

    }

    /**
     * @param audioClip
     */
    public void bindTo(final AudioClip audioClip) {
        _audioClip = audioClip;

        File file = new File(audioClip.getAbsoluteFilePath());
        _titleLabel.setText(file.getName());
        //_artistLabel.setText(_audioClip.Artist.getValue());
        //_audioClip.Name.OnChanged.addListener(this::onAudioClipNameChanged);
        //_audioClip.Artist.OnChanged.addListener(this::onAudioClipArtistChanged);

        _progressControlPanel.bindTo(audioClip);
        _volumeControlPanel.bindTo(audioClip);
        _playbackControlPanel.bindTo(audioClip);
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


    /**
     * @return
     */
    public EventHandler<ActionEvent> getOnPrevious() { return _onPrevious; }

    /**
     * @return
     */
    public EventHandler<ActionEvent> getOnNext() { return _onNext; }


    /**
     * @param onPrevious
     */
    public void setOnPrevious(final EventHandler<ActionEvent> onPrevious) { _onPrevious = onPrevious; }

    /**
     * @param onNext
     */
    public void setOnNext(final EventHandler<ActionEvent> onNext) { _onNext = onNext; }
}
