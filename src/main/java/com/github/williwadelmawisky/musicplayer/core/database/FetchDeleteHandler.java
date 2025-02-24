package com.github.williwadelmawisky.musicplayer.core.database;

import java.util.UUID;

/**
 *
 */
public interface FetchDeleteHandler {

    /**
     *
     * @param url
     * @param id
     */
    void fetchDELETE(final URL url, final UUID id);
}
