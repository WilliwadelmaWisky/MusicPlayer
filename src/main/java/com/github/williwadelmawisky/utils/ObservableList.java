package com.github.williwadelmawisky.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @param <T>
 */
public class ObservableList<T> implements Iterable<T> {

    public final EventHandler<OnItemAddedEventArgs<T>> OnItemAdded;
    public final EventHandler<OnItemRemoveEventArgs<T>> OnItemRemoved;
    public final EventHandler<OnListClearedEventArgs> OnCleared;

    private final List<T> _list;


    /**
     *
     */
    public ObservableList() {
        _list = new ArrayList<>();
        OnItemAdded = new EventHandler<>();
        OnItemRemoved = new EventHandler<>();
        OnCleared = new EventHandler<>();
    }


    /**
     * @return
     */
    @Override
    public Iterator<T> iterator() { return _list.iterator(); }


    /**
     * @param index
     * @return
     */
    public T get(final int index) { return _list.get(index); }

    /**
     * @return
     */
    public boolean isEmpty() { return _list.isEmpty(); }

    /**
     * @return
     */
    public int length() { return _list.size(); }


    /**
     * @param item
     * @return
     */
    public boolean add(final T item) {
        if (item == null)
            return false;

        _list.add(item);
        final OnItemAddedEventArgs<T> args = new OnItemAddedEventArgs<>(item);
        OnItemAdded.invoke(this, args);
        return true;
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(final T item) {
        if (!_list.remove(item))
            return false;

        final OnItemRemoveEventArgs<T> args = new OnItemRemoveEventArgs<>(item);
        OnItemRemoved.invoke(this, args);
        return true;
    }

    /**
     *
     */
    public void clear() {
        _list.clear();
        final OnListClearedEventArgs args = new OnListClearedEventArgs();
        OnCleared.invoke(this, args);
    }


    public void shuffle() {

    }

    public void search() {

    }


    /**
     * @param <T>
     */
    public static class OnItemAddedEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public OnItemAddedEventArgs(final T item) {
            super();

            Item = item;
        }
    }

    /**
     * @param <T>
     */
    public static class OnItemRemoveEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public OnItemRemoveEventArgs(final T item) {
            super();

            Item = item;
        }
    }

    public static class OnListClearedEventArgs extends EventArgs {

    }
}
