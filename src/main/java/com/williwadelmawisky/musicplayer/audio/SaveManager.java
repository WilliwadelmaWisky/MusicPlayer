package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.Files;

import java.io.File;

/**
 *
 */
public class SaveManager {

    /**
     * @param saveable
     * @param file
     * @return
     */
    public boolean save(final Saveable saveable, final File file) {
        if (saveable == null)
            return false;

        final String data = saveable.serialize();
        return Files.write(file, data);
    }

    /**
     * @param saveable
     * @param filePath
     * @return
     */
    public boolean save(final Saveable saveable, final String filePath) {
        final File file = new File(filePath);
        return save(saveable, file);
    }


    /**
     * @param saveable
     * @param file
     * @return
     */
    public boolean load(final Saveable saveable, final File file) {
        if (saveable == null)
            return false;

        final String data = Files.read(file);
        return saveable.deserialize(data);
    }

    /**
     * @param saveable
     * @param filePath
     * @return
     */
    public boolean load(final Saveable saveable, final String filePath) {
        final File file = new File(filePath);
        return load(saveable, file);
    }
}
