package com.github.williwadelmawisky.musicplayer.core.db;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class Table {

    private final Map<UUID, Object> _valueMap;


    public Table() {
        _valueMap = new HashMap<>();
    }


    public Object read(final UUID id) {
        return _valueMap.get(id);
    }

    public Iterable<Object> readAll() {
        return _valueMap.values();
    }

    public void write(final UUID id, final Object value) {
        _valueMap.put(id, value);
    }

    public void delete(final UUID id) {
        _valueMap.remove(id);
    }
}
