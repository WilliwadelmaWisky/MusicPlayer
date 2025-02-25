package com.github.williwadelmawisky.musicplayer.core.database;

import java.util.ArrayList;
import java.util.List;
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
     * @param tableType
     * @param id
     * @return
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fetchGET(final Database.TableType tableType, final UUID id) {
        final Table table = _database.getTable(tableType);
        return (T)table.get(id);
    }

    /**
     *
     * @param tableType
     * @return
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Iterable<T> fetchGET(final Database.TableType tableType) {
        final Table table = _database.getTable(tableType);
        final List<T> valueList = new ArrayList<>();
        for (UUID id : table) {
            valueList.add((T)table.get(id));
        }

        return valueList;
    }

    /**
     *
     * @param type
     * @param id
     * @param value
     */
    @Override
    public void fetchPOST(final Database.TableType type, final UUID id, final Serializable value) {
        final Table table = _database.getTable(type);
        table.set(id, value);
    }

    /**
     *
     * @param type
     * @param id
     */
    @Override
    public void fetchDELETE(final Database.TableType type, final UUID id) {
        final Table table = _database.getTable(type);
        table.delete(id);
    }
}
