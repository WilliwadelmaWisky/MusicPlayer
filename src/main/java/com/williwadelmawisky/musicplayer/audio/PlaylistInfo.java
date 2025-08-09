package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.json.JSON;
import com.williwadelmawisky.musicplayer.util.json.PrettyModifier;
import com.williwadelmawisky.musicplayer.util.json.SerializeField;

import java.util.function.Consumer;

/**
 *
 */
public class PlaylistInfo implements Saveable {

    @SerializeField private String[] FilePaths;


    /**
     *
     */
    public PlaylistInfo() {
        FilePaths = new String[0];
    }

    /**
     * @param audioClipList
     */
    public PlaylistInfo(final ObservableList<AudioClip> audioClipList) {
        FilePaths = new String[audioClipList.length()];
        for (int i = 0; i < audioClipList.length(); i++) {
            FilePaths[i] = audioClipList.get(i).getAbsoluteFilePath();
        }
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
     * @param index
     * @return
     */
    public String get(final int index) { return FilePaths[index]; }

    /**
     * @param action
     */
    public void forEach(final Consumer<String> action) {
        for (String filePath : FilePaths)
            action.accept(filePath);
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
    public boolean deserialize(String s) {
        JSON.fromJSON(s, this);
        return true;
    }
}
