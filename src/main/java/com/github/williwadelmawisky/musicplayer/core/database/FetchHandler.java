package com.github.williwadelmawisky.musicplayer.core.database;

import com.github.williwadelmawisky.musicplayer.util.Util;

import java.util.UUID;

/**
 *
 */
public class FetchHandler implements FetchGetHandler, FetchPostHandler, FetchDeleteHandler {

    private final Database _database;


    /**
     *
     * @param database
     */
    public FetchHandler(final Database database) {
        _database = database;
    }


    /**
     *
     * @param type
     * @param id
     * @return
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fetchGET(final URL type, final UUID id) {
        final Table table = _database.getTable(type);
        return (T)table.read(id);
    }

    /**
     *
     * @param url
     * @return
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Iterable<T> fetchGET(URL url) {
        final Table table = _database.getTable(url);
        return Util.map(table.readAll(), obj -> (T)obj);
    }

    /**
     *
     * @param type
     * @param id
     * @param value
     */
    @Override
    public void fetchPOST(final URL type, final UUID id, final Object value) {
        final Table table = _database.getTable(type);
        table.write(id, value);
    }

    /**
     *
     * @param type
     * @param id
     */
    @Override
    public void fetchDELETE(final URL type, final UUID id) {
        final Table table = _database.getTable(type);
        table.delete(id);
    }
}
