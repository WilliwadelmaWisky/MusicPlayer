package com.github.williwadelmawisky.musicplayer.core.database;

import java.util.UUID;

/**
 *
 */
public interface FetchPostHandler {

    /**
     *
     * @param tableType
     * @param id
     * @param value
     */
    void fetchPOST(final Database.TableType tableType, final UUID id, final Serializable value);
}
