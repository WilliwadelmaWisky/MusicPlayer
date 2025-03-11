package com.github.williwadelmawisky.musicplayer.database;

import com.github.williwadelmawisky.musicplayer.util.Files;
import com.github.williwadelmawisky.musicplayer.util.Func;

import java.util.*;

/**
 *
 */
public class Table implements Iterable<UUID> {

    private final Map<UUID, Serializable> _valueMap;
    private final Func<Serializable> _createFunc;
    private final String _savePath;


    /**
     * @param path
     * @param createFunc
     */
    public Table(final String path, final Func<Serializable> createFunc) {
        _savePath = path;
        _createFunc = createFunc;
        _valueMap = new HashMap<>();
    }


    /**
     * @param id
     * @return
     */
    public Serializable get(final UUID id) {
        return _valueMap.get(id);
    }

    /**
     * @param id
     * @param value
     */
    public void set(final UUID id, final Serializable value) {
        _valueMap.put(id, value);
    }

    /**
     * @param id
     */
    public void delete(final UUID id) {
        _valueMap.remove(id);
    }


    /**
     *
     */
    public void save() {
        final List<String> values = _valueMap.values().stream().map(Serializable::serialize).toList();
        final String content = String.join("\n", values);
        Files.write(_savePath, content);
    }

    /**
     *
     */
    public void load() {
        final String content = Files.read(_savePath);
        if (content == null)
            return;

        final String[] values = content.split("\n");
        for (String value : values) {
            final Serializable serializable = _createFunc.invoke();
            serializable.deserialize(value);
            _valueMap.put(serializable.getID(), serializable);
        }
    }


    /**
     * @return
     */
    @Override
    public Iterator<UUID> iterator() {
        return _valueMap.keySet().iterator();
    }
}
