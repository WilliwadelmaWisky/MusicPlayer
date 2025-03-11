package com.github.williwadelmawisky.musicplayer.database;

import java.util.UUID;

/**
 *
 */
public interface FetchDeleteHandler {

    /**
     *
     * @param tableType
     * @param id
     */
    void fetchDELETE(final Database.TableType tableType, final UUID id);
}
