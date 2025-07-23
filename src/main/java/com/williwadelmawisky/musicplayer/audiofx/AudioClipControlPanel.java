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
        hbox.setPadding(new Insets(0, 50, 0, 50));
        this.getChildren().add(hbox);

        _playbackControlPanel = new PlaybackControlPanel();
        hbox.getChildren().add(_playbackControlPanel);

        final Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMaxWidth(Double.POSITIVE_INFINITY);
        hbox.getChildren().add(region);

        _volumeControlPanel = new VolumeControlPanel();
        _volumeControlPanel.setAlignment(Pos.CENTER_LEFT);
        _volumeControlPanel.setMinWidth(250);
        _volumeControlPanel.setMaxWidth(250);
        hbox.getChildren().add(_volumeControlPanel);

        setSpacing(15);
        setPadding(new Insets(10));
        getStyleClass().add("content-box");
        _playbackControlPanel.setOnNext(this::onNext_PlaybackControlPanel);
        _playbackControlPanel.setOnPrevious(this::onPrevious_PlaybackControlPanel);
    }


    /**
     * @param audioClip
     */
    public void setAudioClip(final AudioClip audioClip) {
        _audioClip = audioClip;

        _progressControlPanel.setAudioClip(audioClip);
        _volumeControlPanel.setAudioClip(audioClip);
        _playbackControlPanel.setAudioClip(audioClip);
    }

    /**
     *
     */
    public void clear() {
        _progressControlPanel.clear();
        _volumeControlPanel.clear();
        _playbackControlPanel.clear();

        if (_audioClip == null)
            return;
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


    /**
     * @param e
     */
    private void onPrevious_PlaybackControlPanel(final ActionEvent e) {
        _onPrevious.handle(e);
    }

    /**
     * @param e
     */
    private void onNext_PlaybackControlPanel(final ActionEvent e) {
        _onNext.handle(e);
    }
}
