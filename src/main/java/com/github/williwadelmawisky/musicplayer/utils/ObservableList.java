package com.github.williwadelmawisky.musicplayer.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @param <T>
 */
public class ObservableList<T> implements Iterable<T> {

    public final EventHandler<OnItemAddedEventArgs<T>> OnItemAdded;
    public final EventHandler<OnItemRemovedEventArgs<T>> OnItemRemoved;
    public final EventHandler<OnClearedEventArgs> OnCleared;
    public final EventHandler<OnSortedEventArgs> OnSorted;

    private final List<T> _list;


    /**
     *
     */
    public ObservableList() {
        _list = new ArrayList<>();
        OnItemAdded = new EventHandler<>();
        OnItemRemoved = new EventHandler<>();
        OnCleared = new EventHandler<>();
        OnSorted = new EventHandler<>();
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
        OnItemAdded.invoke(this, new OnItemAddedEventArgs<>(item));
        return true;
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(final T item) {
        if (!_list.remove(item))
            return false;

        OnItemRemoved.invoke(this, new OnItemRemovedEventArgs<>(item));
        return true;
    }

    /**
     *
     */
    public void clear() {
        _list.clear();
        OnCleared.invoke(this, new OnClearedEventArgs());
    }


    /**
     * @param comparator
     */
    public void sort(final Comparator<? super T> comparator) {
        _list.sort(comparator);
        OnSorted.invoke(this, new OnSortedEventArgs());
    }

    /**
     *
     */
    public void shuffle() {
        Collections.shuffle(_list);
        OnSorted.invoke(this, new OnSortedEventArgs());
    }


    /**
     * @param item
     * @return
     */
    public int indexOf(final T item) { return _list.indexOf(item); }

    /**
     * @param predicate
     * @return
     */
    public int indexOf(final Predicate<? super T> predicate) { return Lists.indexFunc(_list, predicate); }


    /**
     * @param action The action to be performed for each element
     */
    public void forEach(final Consumer<? super T> action) { _list.forEach(action); }

    /**
     * @param mapper
     * @param <TOut>
     * @return
     */
    public <TOut> List<? extends TOut> map(final Function<? super T, ? extends TOut> mapper) { return _list.stream().map(mapper).toList(); }

    /**
     * @param predicate
     * @return
     */
    public List<T> filter(final Predicate<? super T> predicate) { return _list.stream().filter(predicate).toList(); }


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
    public static class OnItemRemovedEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public OnItemRemovedEventArgs(final T item) {
            super();

            Item = item;
        }
    }

    public static class OnClearedEventArgs extends EventArgs {

    }

    public static class OnSortedEventArgs extends EventArgs {

    }
}
