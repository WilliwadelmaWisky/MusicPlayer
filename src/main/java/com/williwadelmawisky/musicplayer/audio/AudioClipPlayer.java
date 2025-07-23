package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.*;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;
import com.williwadelmawisky.musicplayer.util.event.EventArgs_SingleValue;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

/**
 *
 */
public class AudioClipPlayer {

    public final EventHandler<EventArgs_SingleValue<AudioClip>> OnPlay;
    public final EventHandler<EventArgs_SingleValue<AudioClip>> OnPause;
    public final EventHandler<EventArgs_SingleValue<AudioClip>> OnStop;
    public final EventHandler<EventArgs_SingleValue<Double>> OnVolumeChanged;
    public final EventHandler<EventArgs_SingleValue<Duration>> OnTotalDurationChanged;
    public final EventHandler<EventArgs_SingleValue<Progress>> OnProgressChanged;

    public final ObservableList<AudioClip> AudioClipList;
    public final com.williwadelmawisky.musicplayer.util.SelectionModel<AudioClip> SelectionModel;
    public final AudioClipSelector AudioClipSelector;
    private AudioClip _activeAudioClip;
    private Duration _totalDuration;
    private double _volume;


    /**
     * @param volume
     */
    public AudioClipPlayer(final double volume) {
        AudioClipList = new UniqueObservableList<>();
        SelectionModel = new SelectionModel<>(AudioClipList);
        AudioClipSelector = new AudioClipSelector(SelectionModel);

        OnPlay = new EventHandler<>();
        OnPause = new EventHandler<>();
        OnStop = new EventHandler<>();
        OnVolumeChanged = new EventHandler<>();
        OnTotalDurationChanged = new EventHandler<>();
        OnProgressChanged = new EventHandler<>();

        _volume = Math.clamp(volume, 0, 1);
        _totalDuration = Duration.ZERO;
        _activeAudioClip = null;

        AudioClipList.OnItemAdded.addListener(this::onAdded_AudioClipList);
        AudioClipList.OnItemRemoved.addListener(this::onRemoved_AudioClipList);
        AudioClipList.OnCleared.addListener(this::onCleared_AudioClipList);
        SelectionModel.OnSelected.addListener(this::onSelected_SelectionModel);
        SelectionModel.OnCleared.addListener(this::onCleared_SelectionModel);
    }


    /**
     * @return
     */
    public double getVolume() { return _volume; }


    /**
     * @param volume
     */
    public void setVolume(final double volume) {
        if (Math.abs(_volume - volume) <= 1e-6)
            return;

        _volume = Math.clamp(volume, 0, 1);
        OnVolumeChanged.invoke(this, new EventArgs_SingleValue<>(_volume));

        if (_activeAudioClip != null)
            _activeAudioClip.setVolume(_volume);
    }


    /**
     * @param playlist
     */
    public void load(final PlaylistInfo playlist) {
        clear();

        for (int i = 0; i < playlist.length(); i++) {
            final String filePath = playlist.get(i);
            final AudioClip audioClip = new AudioClip(new File(filePath));
            AudioClipList.add(audioClip);
        }
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return AudioClipList.isEmpty();
    }

    /**
     * @param audioClip
     */
    public void add(final AudioClip audioClip) {
        AudioClipList.add(audioClip);
    }

    /**
     * @param audioClip
     */
    public void remove(final AudioClip audioClip) {
        AudioClipList.remove(audioClip);
    }

    /**
     *
     */
    public void clear() {
        AudioClipList.clear();
    }


    /**
     *
     */
    public void next() { AudioClipSelector.next(); }

    /**
     *
     */
    public void previous() { AudioClipSelector.previous(); }


    /**
     * @return
     */
    public boolean play() {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.play();
    }

    /**
     * @return
     */
    public boolean pause() {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.pause();
    }

    /**
     * @return
     */
    public boolean stop() {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.stop();
    }


    /**
     * @param progress
     * @return
     */
    public boolean seek(final double progress) {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.seek(progress);
    }

    /**
     * @param playbackPosition
     * @return
     */
    public boolean seek(final Duration playbackPosition) {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.seek(playbackPosition);
    }

    /**
     * @param amount
     * @return
     */
    public boolean skipForward(final Duration amount) {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.skipForward(amount);
    }

    /**
     * @param amount
     * @return
     */
    public boolean skipBackward(final Duration amount) {
        if (_activeAudioClip == null)
            return false;

        return _activeAudioClip.skipBackward(amount);
    }


    /**
     * @return
     */
    private Duration calculateTotalDuration() {
        final List<AudioClip> audioClipList = AudioClipList.filter(audioClip -> !audioClip.getTotalDuration().equals(Duration.UNKNOWN));
        final List<Duration> durationList = audioClipList.stream().map(AudioClip::getTotalDuration).toList();
        return Durations.sum(durationList);
    }

    /**
     * @param totalDuration
     */
    private void updateTotalDuration(final Duration totalDuration) {
        _totalDuration = totalDuration;
        OnTotalDurationChanged.invoke(this, new EventArgs_SingleValue<>(_totalDuration));
    }


    /**
     * @param audioClip
     */
    private void subscribeAudioClipEvents(final AudioClip audioClip) {
        audioClip.OnReady.addListener(this::onReady_AudioClip);
        audioClip.OnFinish.addListener(this::onFinished_AudioClip);
        audioClip.OnPlay.addListener(this::onPlay_AudioClip);
        audioClip.OnPause.addListener(this::onPause_AudioClip);
        audioClip.OnStop.addListener(this::onStop_AudioClip);
        audioClip.OnProgressChanged.addListener(this::onProgressChanged_AudioClip);
        audioClip.OnVolumeChanged.addListener(this::onVolumeChanged_AudioClip);
    }

    /**
     * @param audioClip
     */
    private void unsubscribeAudioClipEvents(final AudioClip audioClip) {
        audioClip.OnReady.removeListener(this::onReady_AudioClip);
        audioClip.OnFinish.removeListener(this::onFinished_AudioClip);
        audioClip.OnPlay.removeListener(this::onPlay_AudioClip);
        audioClip.OnPause.removeListener(this::onPause_AudioClip);
        audioClip.OnStop.removeListener(this::onStop_AudioClip);
        audioClip.OnProgressChanged.removeListener(this::onProgressChanged_AudioClip);
        audioClip.OnVolumeChanged.removeListener(this::onVolumeChanged_AudioClip);
    }


    /**
     * @param sender
     * @param args
     */
    private void onReady_AudioClip(final Object sender, final EventArgs args) {
        updateTotalDuration(calculateTotalDuration());
    }

    /**
     * @param sender
     * @param args
     */
    private void onFinished_AudioClip(final Object sender, final EventArgs args) {
        AudioClipSelector.next();
    }

    /**
     * @param sender
     * @param args
     */
    private void onPlay_AudioClip(final Object sender, final EventArgs args) {
        OnPlay.invoke(this, new EventArgs_SingleValue<>(_activeAudioClip));
    }

    /**
     * @param sender
     * @param args
     */
    private void onPause_AudioClip(final Object sender, final EventArgs args) {
        OnPause.invoke(this, new EventArgs_SingleValue<>(_activeAudioClip));
    }

    /**
     * @param sender
     * @param args
     */
    private void onStop_AudioClip(final Object sender, final EventArgs args) {
        OnStop.invoke(this, new EventArgs_SingleValue<>(_activeAudioClip));
        if (_activeAudioClip == null)
            return;

        if (!_activeAudioClip.equals(SelectionModel.getSelectedItem()))
            return;

        _activeAudioClip = null;
        SelectionModel.clear();
    }

    /**
     * @param sender
     * @param args
     */
    private void onVolumeChanged_AudioClip(final Object sender, final EventArgs_SingleValue<Double> args) {
        if (Math.abs(_volume - args.Value) <= 1e-6)
            return;

        _volume = args.Value;
        OnVolumeChanged.invoke(this, args);
    }

    /**
     * @param sender
     * @param args
     */
    private void onProgressChanged_AudioClip(final Object sender, final EventArgs_SingleValue<Progress> args) {
        OnProgressChanged.invoke(this, args);
    }


    /**
     * @param sender
     * @param args
     */
    private void onAdded_AudioClipList(final Object sender, final ObservableList.OnItemAddedEventArgs<AudioClip> args) {
        subscribeAudioClipEvents(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onRemoved_AudioClipList(final Object sender, final ObservableList.OnItemRemovedEventArgs<AudioClip> args) {
        unsubscribeAudioClipEvents(args.Item);
        updateTotalDuration(calculateTotalDuration());

        if (args.Item.equals(SelectionModel.getSelectedItem()))
            AudioClipSelector.next();
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_AudioClipList(final Object sender, final ObservableList.OnClearedEventArgs<AudioClip> args) {
        args.Items.forEach(this::unsubscribeAudioClipEvents);
        updateTotalDuration(Duration.ZERO);
        SelectionModel.clear();
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.OnSelectedEventArgs<AudioClip> args) {
        if (args.Item.equals(_activeAudioClip))
            return;

        if (_activeAudioClip != null)
            _activeAudioClip.stop();

        _activeAudioClip = args.Item;
        _activeAudioClip.setVolume(_volume);
        _activeAudioClip.play();
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_SelectionModel(final Object sender, final EventArgs args) {
        if (_activeAudioClip != null)
            _activeAudioClip.stop();
    }
}
