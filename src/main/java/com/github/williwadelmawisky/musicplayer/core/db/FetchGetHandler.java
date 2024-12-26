package com.github.williwadelmawisky.musicplayer.core.db;

import java.util.UUID;

/**
 *
 */
public interface FetchGetHandler {

    /**
     *
     * @param url
     * @param id
     * @return
     * @param <T>
     */
    <T> T fetchGET(final URL url, final UUID id);

    /**
     *
     * @param url
     * @return
     * @param <T>
     */
    <T> Iterable<T> fetchGET(final URL url);
}
