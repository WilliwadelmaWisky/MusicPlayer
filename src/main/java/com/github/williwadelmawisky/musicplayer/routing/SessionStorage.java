package com.github.williwadelmawisky.musicplayer.routing;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SessionStorage {

    private final Map<String, String> _valueMap;

    /**
     *
     */
    public SessionStorage() {
        _valueMap = new HashMap<>();
    }

    /**
     * @param key
     * @return
     */
    public boolean hasKey(final String key) {
        return _valueMap.containsKey(key);
    }

    /**
     * @param key
     * @return
     */
    public String get(final String key) {
        return _valueMap.get(key);
    }

    /**
     * @param key
     * @param value
     */
    public void set(final String key, final String value) {
        _valueMap.put(key, value);
    }

    /**
     * @param key
     */
    public void delete(final String key) {
        _valueMap.remove(key);
    }
}
