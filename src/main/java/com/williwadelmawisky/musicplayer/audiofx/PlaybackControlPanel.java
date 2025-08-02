package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.AudioClip;
import com.williwadelmawisky.musicplayer.audio.AudioClipPlayer;
import com.williwadelmawisky.musicplayer.audio.ObservableValue;
import com.williwadelmawisky.musicplayer.audio.SelectionMode;
import com.williwadelmawisky.musicplayer.util.Arrays;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.util.Map;

/**
 *
 */
public class PlaybackControlPanel extends HBox {

    private final Map<SelectionMode, String> SELECTION_MODE_SYMBOL_MAP = Map.ofEntries(
            Map.entry(SelectionMode.ORDERED, "↦"),
            Map.entry(SelectionMode.RANDOMIZED, "⇄"),
            Map.entry(SelectionMode.REPEATED, "↻")
    );

    private final Button _playButton;
    private final Button _previousButton;
    private final Button _nextButton;
    private final Button _stopButton;
    private final Button _modeButton;

    private AudioClipPlayer _audioClipPlayer;


    /**
     *
     */
    public PlaybackControlPanel() {
        super();

        _playButton = new Button("⏵");
        _playButton.setMinWidth(35);
        _playButton.setMaxWidth(35);
        _stopButton = new Button("⏹");
        _previousButton = new Button("⏮");
        _nextButton = new Button("⏭");
        _modeButton = new Button("→");
        _modeButton.setMinWidth(35);
        _modeButton.setMaxWidth(35);

        final Region region = new Region();
        region.setMinWidth(10);
        region.setMaxWidth(10);

        final Region region2 = new Region();
        region2.setMinWidth(10);
        region2.setMaxWidth(10);

        setSpacing(5);
        getChildren().addAll(_playButton, region, _previousButton, _stopButton, _nextButton, region2, _modeButton);

        _playButton.setOnAction(this::onClicked_PlayButton);
        _stopButton.setOnAction(this::onClicked_StopButton);
        _previousButton.setOnAction(this::onClicked_PreviousButton);
        _nextButton.setOnAction(this::onClicked_NextButton);
        _modeButton.setOnAction(this::onClicked_ModeButton);
    }


    /**
     * @param audioClipPlayer
     */
    public void setAudioClipPlayer(final AudioClipPlayer audioClipPlayer) {
        _audioClipPlayer = audioClipPlayer;

        updatePlaystate(_audioClipPlayer.SelectionModel.hasValue() && _audioClipPlayer.SelectionModel.getValue().isPlaying());
        updateSelectionMode(_audioClipPlayer.SelectionModel.SelectionModeProperty.getValue());

        _audioClipPlayer.OnPlay.addListener(this::onPlay_AudioClipPlayer);
        _audioClipPlayer.OnPause.addListener(this::onPause_AudioClipPlayer);
        _audioClipPlayer.OnStop.addListener(this::onStop_AudioClipPlayer);
        _audioClipPlayer.SelectionModel.SelectionModeProperty.OnChanged.addListener(this::onChanged_SelectionModeProperty);
    }

    /**
     *
     */
    public void clear() {
        updatePlaystate(false);

        if (_audioClipPlayer == null)
            return;

        _audioClipPlayer.OnPlay.removeListener(this::onPlay_AudioClipPlayer);
        _audioClipPlayer.OnPause.removeListener(this::onPause_AudioClipPlayer);
        _audioClipPlayer.OnStop.removeListener(this::onStop_AudioClipPlayer);
        _audioClipPlayer.SelectionModel.SelectionModeProperty.OnChanged.removeListener(this::onChanged_SelectionModeProperty);
    }


    /**
     * @param isPlaying
     */
    private void updatePlaystate(final boolean isPlaying) {
        final String textString = isPlaying ? "⏸" : "⏵";
        _playButton.setText(textString);
    }

    /**
     * @param selectionMode
     */
    private void updateSelectionMode(final SelectionMode selectionMode) {
        if (!SELECTION_MODE_SYMBOL_MAP.containsKey(selectionMode))
            return;

        final String textString = SELECTION_MODE_SYMBOL_MAP.get(selectionMode);
        _modeButton.setText(textString);
    }


    /**
     * @param e
     */
    private void onClicked_PlayButton(final ActionEvent e) {
        final AudioClip audioClip = _audioClipPlayer.SelectionModel.getValue();
        switch (audioClip.getStatus()) {
            case PLAYING -> _audioClipPlayer.pause();
            case PAUSED -> _audioClipPlayer.play();
        }
    }

    /**
     * @param e
     */
    private void onClicked_StopButton(final ActionEvent e) {
        _audioClipPlayer.stop();
    }

    /**
     * @param e
     */
    private void onClicked_PreviousButton(final ActionEvent e) {
        _audioClipPlayer.SelectionModel.selectPrevious();
    }

    /**
     * @param e
     */
    private void onClicked_NextButton(final ActionEvent e) {
        _audioClipPlayer.SelectionModel.selectNext();
    }

    /**
     * @param e
     */
    private void onClicked_ModeButton(final ActionEvent e) {
        final SelectionMode[] selectionModes = SelectionMode.values();
        final int index = Arrays.indexof(selectionModes, _audioClipPlayer.SelectionModel.SelectionModeProperty.getValue());
        final SelectionMode selectionMode = selectionModes[(index + 1) % selectionModes.length];
        _audioClipPlayer.SelectionModel.SelectionModeProperty.setValue(selectionMode);
    }


    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updatePlaystate(true);
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updatePlaystate(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClipPlayer(final Object sender, final EventArgs_SingleValue<AudioClip> args) {
        updatePlaystate(false);
    }

    /**
     * @param sender
     * @param args
     */
    private void onChanged_SelectionModeProperty(final Object sender, final ObservableValue.ChangeEventArgs<SelectionMode> args) {
        updateSelectionMode(args.Value);
    }
}
