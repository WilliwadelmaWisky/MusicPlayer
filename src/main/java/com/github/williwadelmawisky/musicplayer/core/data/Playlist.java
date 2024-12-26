package com.github.williwadelmawisky.musicplayer.core.data;

import java.util.*;

/**
 *
 */
public class Playlist implements Iterable<UUID> {

    private final UUID _id;
    private final Set<UUID> _songIDSet;
    private String _name;


    /**
     * @param id
     * @param name
     */
    public Playlist(final UUID id, final String name) {
        _id = id;
        _name = name;
        _songIDSet = new HashSet<>();
    }

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
}
