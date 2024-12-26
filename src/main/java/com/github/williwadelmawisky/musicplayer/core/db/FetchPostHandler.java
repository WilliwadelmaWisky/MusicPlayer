package com.github.williwadelmawisky.musicplayer.core.db;

import java.util.UUID;

/**
 *
 */
public interface FetchPostHandler {

    /**
     *
     * @param url
     * @param id
     * @param value
     */
    void fetchPOST(final URL url, final UUID id, final Object value);
}
