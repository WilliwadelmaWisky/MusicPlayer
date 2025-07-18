package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.json.JSON;
import com.williwadelmawisky.musicplayer.json.SerializeField;

/**
 *
 */
public class AudioClipInfo implements Saveable {

    @SerializeField private String FilePath;
    @SerializeField private String Name;
    @SerializeField private String Artist;


    /**
     * @return
     */
    @Override
    public String serialize() {
        return JSON.toJSON(this);
    }

    /**
     * @param s
     * @return
     */
    @Override
    public boolean deserialize(final String s) {
        JSON.fromJSON(s, this);
        return true;
    }
}
