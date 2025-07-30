package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.Lists;
import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;

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
    public final EventHandler<OnClearedEventArgs<T>> OnCleared;
    public final EventHandler<EventArgs> OnChanged;
    public final EventHandler<EventArgs> OnSorted;

    private final List<T> _list;


    /**
     *
     */
    public ObservableList() {
        _list = new ArrayList<>();
        OnItemAdded = new EventHandler<>();
        OnItemRemoved = new EventHandler<>();
        OnCleared = new EventHandler<>();
        OnChanged = new EventHandler<>();
        OnSorted = new EventHandler<>();
    }

    /**
     * @param collection
     */
    public ObservableList(final Collection<T> collection) {
        this();
        addAll(collection);
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
     * @param item
     * @return
     */
    public boolean contains(final T item) { return _list.contains(item); }

    /**
     * @return
     */
    public int length() { return _list.size(); }


    /**
     * @param item
     * @return
     */
    public boolean add(final T item) {
        if (item == null || !canItemBeAdded(item))
            return false;

        _list.add(item);
        OnItemAdded.invoke(this, new OnItemAddedEventArgs<>(item));
        OnChanged.invoke(this, EventArgs.EMPTY);
        return true;
    }

    /**
     * @param items
     */
    public void addAll(final Collection<T> items) {
        items.forEach(this::add);
    }

    /**
     * @param item
     * @return
     */
    protected boolean canItemBeAdded(final T item) {
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
        OnChanged.invoke(this, EventArgs.EMPTY);
        return true;
    }


    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        final List<T> items = new ArrayList<>(_list);
        _list.clear();
        OnCleared.invoke(this, new OnClearedEventArgs<>(items));
        OnChanged.invoke(this, EventArgs.EMPTY);
    }


    /**
     * @param comparator
     */
    public void sort(final Comparator<? super T> comparator) {
        _list.sort(comparator);
        OnSorted.invoke(this, EventArgs.EMPTY);
        OnChanged.invoke(this, EventArgs.EMPTY);
    }

    /**
     *
     */
    public void shuffle() {
        Collections.shuffle(_list);
        OnSorted.invoke(this, EventArgs.EMPTY);
        OnChanged.invoke(this, EventArgs.EMPTY);
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

    /**
     * @param <T>
     */
    public static class OnClearedEventArgs<T> extends EventArgs {

        public final List<T> Items;

        /**
         * @param items
         */
        public OnClearedEventArgs(final List<T> items) {
            Items = items;
        }
    }
}
