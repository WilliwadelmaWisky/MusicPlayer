package com.williwadelmawisky.musicplayer.util;

import java.util.Collection;

/**
 * @param <T>
 */
public class UniqueObservableList<T> extends ObservableList<T> {

    /**
     *
     */
    public UniqueObservableList() {
        super();
    }

    /**
     * @param collection
     */
    public UniqueObservableList(final Collection<T> collection) {
        super(collection);
    }


    /**
     * @param item
     * @return
     */
    @Override
    protected boolean canItemBeAdded(final T item) {
        return !contains(item);
    }
}
