package com.github.williwadelmawisky.musicplayer.database;

import java.util.UUID;

/**
 *
 */
public interface FetchGetHandler {

    /**
     *
     * @param tableType
     * @param id
     * @return
     * @param <T>
     */
    <T> T fetchGET(final Database.TableType tableType, final UUID id);

    /**
     *
     * @param tableType
     * @return
     * @param <T>
     */
    <T> Iterable<T> fetchGET(final Database.TableType tableType);
}
