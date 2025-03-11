package com.github.williwadelmawisky.musicplayer.database;

import java.util.UUID;

/**
 *
 */
public class FileData implements Serializable {

    private UUID _id;
    private String _absolutePath;


    /**
     *
     */
    FileData() {
        this(null, null);
    }

    /**
     * @param id
     * @param absolutePath
     */
    public FileData(final UUID id, final String absolutePath) {
        _id = id;
        _absolutePath = absolutePath;
    }


    /**
     * @param s
     * @return
     */
    public static FileData parse(final String s) {
        final FileData fileData = new FileData();
        fileData.deserialize(s);
        return fileData;
    }


    /**
     * @return
     */
    @Override
    public UUID getID() {
        return _id;
    }

    /**
     * @return
     */
    public String getAbsolutePath() {
        return _absolutePath;
    }


    /**
     * @param id
     */
    private void setID(final UUID id) { _id = id; }

    /**
     * @param absolutePath
     */
    public void setAbsolutePath(final String absolutePath) { _absolutePath = absolutePath; }


    /**
     * @return
     */
    @Override
    public String serialize() {
        final String[] values = new String[] {
                _id.toString(),
                _absolutePath
        };
        return String.join("|", values);
    }

    /**
     * @param s
     */
    @Override
    public void deserialize(String s) {
        final String[] values = s.split("\\|");
        setID(UUID.fromString(values[0]));
        setAbsolutePath(values[1]);
    }
}
