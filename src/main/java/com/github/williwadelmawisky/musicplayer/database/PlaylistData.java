package com.github.williwadelmawisky.musicplayer.database;

import java.util.*;

/**
 *
 */
public class PlaylistData implements Serializable, Iterable<UUID> {

    private final Set<UUID> _songIDSet;
    private UUID _id;
    private String _name;


    /**
     *
     */
    PlaylistData() {
        this(null, null);
    }

    /**
     * @param id
     * @param name
     */
    public PlaylistData(final UUID id, final String name) {
        _id = id;
        _name = name;
        _songIDSet = new HashSet<>();
    }


    /**
     * @param s
     * @return
     */
    public static PlaylistData parse(final String s) {
        final PlaylistData playlistData = new PlaylistData();
        playlistData.deserialize(s);
        return playlistData;
    }


    /**
     * @return
     */
    @Override
    public UUID getID() { return _id; }

    /**
     * @return
     */
    public String getName() { return _name; }

    /**
     * @return
     */
    public int getSize() {
        return _songIDSet.size();
    }


    /**
     * @param id
     */
    private void setID(final UUID id) { _id = id; }

    /**
     * @param name
     */
    public void setName(final String name) { _name = name; }

    /**
     * @param songID
     */
    public void add(final UUID songID) {
        _songIDSet.add(songID);
    }


    /**
     * @return
     */
    @Override
    public Iterator<UUID> iterator() {
        return _songIDSet.iterator();
    }


    /**
     * @return
     */
    @Override
    public String serialize() {
        final List<String> ids = _songIDSet.stream().map(UUID::toString).toList();
        return _id.toString() + "|" + _name + "|" + String.join("|", ids);
    }

    /**
     * @param s
     */
    @Override
    public void deserialize(final String s) {
        final String[] values = s.split("\\|");
        setID(UUID.fromString(values[0]));
        setName(values[1]);
        _songIDSet.clear();
        for (int i = 2; i < values.length; i++) {
            add(UUID.fromString(values[i]));
        }
    }
}
