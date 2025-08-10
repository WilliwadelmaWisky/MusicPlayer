package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.json.JSON;
import com.williwadelmawisky.musicplayer.util.json.PrettyModifier;
import com.williwadelmawisky.musicplayer.util.json.SerializeField;
import com.williwadelmawisky.musicplayer.utilfx.Durations;

import javafx.util.Duration;

import java.util.function.Consumer;

/**
 *
 */
public class PlaylistInfo implements Saveable {

    @SerializeField private String Name;
    @SerializeField private String TotalDurationString;
    @SerializeField private String[] FilePaths;


    /**
     *
     */
    public PlaylistInfo() {
        Name = "";
        TotalDurationString = "";
        FilePaths = new String[0];
    }

    /**
     *
     * @param name
     * @param audioClipList
     */
    public PlaylistInfo(final String name, final ObservableList<AudioClip> audioClipList) {
        Name = name;
        FilePaths = new String[audioClipList.length()];
        Duration totalDuration = new Duration(0);

        for (int i = 0; i < audioClipList.length(); i++) {
            FilePaths[i] = audioClipList.get(i).getAbsoluteFilePath();
            totalDuration = totalDuration.add(audioClipList.get(i).getTotalDuration());
        }

        TotalDurationString = Durations.durationToString(totalDuration);
    }


    /**
     * @return
     */
    public boolean isEmpty() { return FilePaths.length == 0; }

    /**
     * @return
     */
    public int length() { return FilePaths.length; }

    /**
     * @return
     */
    public String name() { return Name; }

    /**
     * @return
     */
    public String totalDurationString() { return TotalDurationString; }

    /**
     * @return
     */
    private String getID() { return Name; }

    /**
     * @param index
     * @return
     */
    public String get(final int index) { return FilePaths[index]; }


    /**
     * @param name
     */
    void setName(final String name) { Name = name; }


    /**
     * @param action
     */
    public void forEach(final Consumer<String> action) {
        for (String filePath : FilePaths)
            action.accept(filePath);
    }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof PlaylistInfo playlistInfo))
            return false;

        return getID().equals(playlistInfo.getID());
    }


    /**
     * @return
     */
    @Override
    public String serialize() {
        final PrettyModifier prettyModifier = new PrettyModifier();
        return prettyModifier.modify(JSON.toJSON(this));
    }

    /**
     * @param s
     */
    @Override
    public boolean deserialize(final String s) {
        JSON.fromJSON(s, this);
        return true;
    }
}
