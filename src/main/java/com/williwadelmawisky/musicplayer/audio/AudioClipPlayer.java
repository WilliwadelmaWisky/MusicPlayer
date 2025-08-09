package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.event.Event;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;
import com.williwadelmawisky.musicplayer.util.event.ChangeEvent;
import com.williwadelmawisky.musicplayer.utilfx.Durations;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

/**
 *
 */
public class AudioClipPlayer {

    public final EventHandler<ChangeEvent<AudioClip>> OnPlay;
    public final EventHandler<ChangeEvent<AudioClip>> OnPause;
    public final EventHandler<ChangeEvent<AudioClip>> OnStop;

    public final ObservableList<AudioClip> AudioClipList;
    public final SelectionModel<AudioClip> SelectionModel;
    public final ObservableValue<Duration> TotalDurationProperty;
    public final ObservableValue<Double> VolumeProperty;


    /**
     * @param volume
     */
    public AudioClipPlayer(final double volume) {
        AudioClipList = new UniqueObservableList<>();
        SelectionModel = new SelectionModel<>(AudioClipList, SelectionMode.ORDERED);
        VolumeProperty = new ObservableValue<>(Math.clamp(volume, 0, 1));
        TotalDurationProperty = new ObservableValue<>(Duration.ZERO);

        OnPlay = new EventHandler<>();
        OnPause = new EventHandler<>();
        OnStop = new EventHandler<>();

        AudioClipList.OnItemAdded.addListener(this::onAdded_AudioClipList);
        AudioClipList.OnItemRemoved.addListener(this::onRemoved_AudioClipList);
        AudioClipList.OnCleared.addListener(this::onCleared_AudioClipList);
        SelectionModel.OnSelected.addListener(this::onSelected_SelectionModel);
        SelectionModel.OnCleared.addListener(this::onCleared_SelectionModel);
    }


    /**
     * @param volume
     */
    public void setVolume(final double volume) {
        final double clampedVolume = Math.clamp(volume, 0, 1);;
        if (Math.abs(VolumeProperty.getValue() - clampedVolume) <= 1e-6)
            return;

        AudioClipList.forEach(audioClip -> audioClip.setVolume(clampedVolume));
        VolumeProperty.setValue(clampedVolume);
    }

    /**
     * @param amount
     */
    public void increaseVolume(final double amount) {
        final double volume = Math.floor((VolumeProperty.getValue() * 100.0 / amount) + 1) * amount / 100.0;
        setVolume(volume);
    }

    /**
     * @param amount
     */
    public void decreaseVolume(final double amount) {
        final double volume = Math.ceil((VolumeProperty.getValue() * 100.0 / amount) - 1) * amount / 100.0;
        setVolume(volume);
    }

    /**
     *
     */
    public void muteVolume() {
        final double volume = 0;
        setVolume(volume);
    }


    /**
     * @param playlist
     */
    public boolean load(final PlaylistInfo playlist) {
        AudioClipList.clear();

        for (int i = 0; i < playlist.length(); i++) {
            final String filePath = playlist.get(i);
            final AudioClip audioClip = new AudioClip(new File(filePath));
            AudioClipList.add(audioClip);
        }

        return !AudioClipList.isEmpty();
    }

    /**
     * @param file
     */
    public boolean load(final File file) {
        AudioClipList.clear();

        final FileReader fileReader = new FileReader(file);
        fileReader.read(AudioClipList::add);

        return !AudioClipList.isEmpty();
    }


    /**
     * @return
     */
    public boolean play() {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        return play(audioClip);
    }

    /**
     * @param audioClip
     * @return
     */
    private boolean play(final AudioClip audioClip) {
        if (!audioClip.play())
            return false;

        OnPlay.invoke(this, new ChangeEvent<>(audioClip));
        return true;
    }

    /**
     * @return
     */
    public boolean pause() {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        return pause(audioClip);
    }

    /**
     * @param audioClip
     * @return
     */
    private boolean pause(final AudioClip audioClip) {
        if (!audioClip.pause())
            return false;

        OnPause.invoke(this, new ChangeEvent<>(audioClip));
        return true;
    }

    /**
     * @return
     */
    public boolean stop() {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        if (!stop(audioClip))
            return false;

        SelectionModel.clearSelection();
        return true;
    }

    /**
     * @param audioClip
     * @return
     */
    private boolean stop(final AudioClip audioClip) {
        if (!audioClip.stop())
            return false;

        OnStop.invoke(this, new ChangeEvent<>(audioClip));
        return true;
    }


    /**
     * @param amount
     * @return
     */
    public boolean jumpForward(final Duration amount) {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        return audioClip.jumpForward(amount);
    }

    /**
     * @param amount
     * @return
     */
    public boolean jumpBackward(final Duration amount) {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        return audioClip.jumpBackward(amount);
    }

    /**
     * @return
     */
    public boolean jumpToStart() {
        if (!SelectionModel.hasValue())
            return false;

        final AudioClip audioClip = SelectionModel.getValue();
        return audioClip.jumpToStart();
    }


    /**
     * @return
     */
    private Duration calculateTotalDuration() {
        final List<AudioClip> audioClipList = AudioClipList.filter(AudioClip::isReady);
        final List<Duration> durationList = audioClipList.stream().map(AudioClip::getTotalDuration).toList();
        return Durations.sum(durationList);
    }


    /**
     * @param sender
     * @param args
     */
    private void onReady_AudioClip(final Object sender, final Event args) {
        final Duration totalDuration = calculateTotalDuration();
        TotalDurationProperty.setValue(totalDuration);
    }

    /**
     * @param sender
     * @param args
     */
    private void onFinished_AudioClip(final Object sender, final Event args) {
        SelectionModel.selectNext();
    }


    /**
     * @param sender
     * @param args
     */
    private void onAdded_AudioClipList(final Object sender, final ObservableList.AddEventArgs<AudioClip> args) {
        final AudioClip audioClip = args.Item;

        audioClip.setVolume(VolumeProperty.getValue());
        audioClip.OnReady.addListener(this::onReady_AudioClip);
        audioClip.OnFinish.addListener(this::onFinished_AudioClip);
    }

    /**
     * @param sender
     * @param args
     */
    private void onRemoved_AudioClipList(final Object sender, final ObservableList.RemoveEventArgs<AudioClip> args) {
        final AudioClip audioClip = args.Item;

        TotalDurationProperty.setValue(calculateTotalDuration());
        audioClip.OnReady.removeListener(this::onReady_AudioClip);
        audioClip.OnFinish.removeListener(this::onFinished_AudioClip);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_AudioClipList(final Object sender, final ObservableList.ClearEventArgs<AudioClip> args) {
        TotalDurationProperty.setValue(Duration.ZERO);
        args.Items.forEach(audioClip -> {
            audioClip.OnReady.removeListener(this::onReady_AudioClip);
            audioClip.OnFinish.removeListener(this::onFinished_AudioClip);
        });
    }


    /**
     * @param sender
     * @param args
     */
    private void onSelected_SelectionModel(final Object sender, final SelectionModel.SelectEventArgs<AudioClip> args) {
        play(args.Item);
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_SelectionModel(final Object sender, final SelectionModel.ClearEventArgs<AudioClip> args) {
        stop(args.Item);
    }
}
