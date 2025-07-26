package com.williwadelmawisky.musicplayer.util;

import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;

/**
 * @param <T>
 */
public class SelectionModel<T> {

    public final EventHandler<OnSelectedEventArgs<T>> OnSelected;
    public final EventHandler<EventArgs> OnCleared;

    private final ObservableList<T> _observableList;
    private int _selectedIndex;
    private T _selectedItem;


    /**
     * @param observableList
     */
    public SelectionModel(final ObservableList<T> observableList) {
        OnSelected = new EventHandler<>();
        OnCleared = new EventHandler<>();
        _observableList = observableList;
        _selectedIndex = -1;
        _selectedItem = null;

        _observableList.OnSorted.addListener(this::onListSorted);
    }


    /**
     * @return
     */
    public boolean isEmpty() { return _observableList.isEmpty(); }

    /**
     * @return
     */
    public int size() { return _observableList.length(); }

    /**
     * @return
     */
    public int getSelectedIndex() { return _selectedIndex; }

    /**
     * @return
     */
    public T getSelectedItem() { return _selectedItem; }


    /**
     * @param index
     */
    public void select(final int index) {
        if (index < 0 || index == _selectedIndex || index >= _observableList.length())
            return;

        _selectedIndex = index;
        _selectedItem = _observableList.get(_selectedIndex);
        OnSelected.invoke(this, new OnSelectedEventArgs<>(_selectedItem));
    }

    /**
     * @param index
     */
    public void clearAndSelect(final int index) {
        clear();

        if (index < 0 || index >= _observableList.length())
            return;

        _selectedIndex = index;
        _selectedItem = _observableList.get(_selectedIndex);
        OnSelected.invoke(this, new OnSelectedEventArgs<>(_selectedItem));
    }

    /**
     *
     */
    public void clear() {
        _selectedIndex = -1;
        _selectedItem = null;
        OnCleared.invoke(this, EventArgs.EMPTY);
    }


    /**
     * @param sender
     * @param args
     */
    private void onListSorted(final Object sender, final EventArgs args) {
        final int index = _observableList.indexOf(item -> item.equals(_selectedItem));
        if (index != -1) {
            _selectedIndex = index;
            return;
        }

        clearAndSelect(0);
    }


    /**
     * @param <T>
     */
    public static class OnSelectedEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public OnSelectedEventArgs(final T item) {
            Item = item;
        }
    }
}
