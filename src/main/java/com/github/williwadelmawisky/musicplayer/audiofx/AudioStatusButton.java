package com.github.williwadelmawisky.musicplayer.audiofx;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.github.williwadelmawisky.musicplayer.audio.AudioStatus;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 */
public class AudioStatusButton extends Button {

    private static final String PLAY_ICON = "img/play_icon.png";
    private static final String PAUSE_ICON = "img/pause_icon.png";

    private final ImageView _imageView;
    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public AudioStatusButton() {
        super();

        _imageView = new ImageView();
        _imageView.setFitWidth(15);
        _imageView.setPickOnBounds(true);
        _imageView.setPreserveRatio(true);
        setGraphic(_imageView);

        setOnAction(this::onClick);
        updateView(AudioStatus.STOPPED);
    }


    /**
     * @param audioClipPlayer
     */
    public void bindTo(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;
        updateView(audioClipPlayer.getAudioStatus());
        audioClipPlayer.OnStatusChanged.addListener(this::onStatusChanged);
    }


    /**
     * @param sender
     * @param args
     */
    private void onStatusChanged(final Object sender, final AudioClipPlayer.OnStatusChangedEventArgs args) {
        updateView(args.AudioStatus);
    }

    /**
     * @param e
     */
    private void onClick(final ActionEvent e) {
        if (_audioClipPlayer == null)
            return;

        final AudioStatus audioStatus = _audioClipPlayer.getAudioStatus() == AudioStatus.PLAYING ? AudioStatus.PAUSED : AudioStatus.PLAYING;
        _audioClipPlayer.setAudioStatus(audioStatus);
    }

    /**
     * @param audioStatus
     */
    private void updateView(final AudioStatus audioStatus) {
        final String iconPath = audioStatus == AudioStatus.PLAYING ? PAUSE_ICON : PLAY_ICON;
        _imageView.setImage(ResourceLoader.loadImage(iconPath));
    }
}
