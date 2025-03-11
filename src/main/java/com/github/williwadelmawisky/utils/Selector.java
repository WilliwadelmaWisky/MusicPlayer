package com.github.williwadelmawisky.utils;

/**
 * @param <T>
 */
public interface Selector<T> {

    /**
     * @param selectionModel
     * @return
     */
    boolean next(final SelectionModel<T> selectionModel);

    /**
     * @param selectionModel
     * @return
     */
    boolean previous(final SelectionModel<T> selectionModel);
}
