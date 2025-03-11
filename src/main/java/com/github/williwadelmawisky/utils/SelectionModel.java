package com.github.williwadelmawisky.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class SelectionModel<T> {

    /**
     *
     */
    public enum SelectionMode {
        SINGLE,
        MULTIPLE
    }

    public final EventHandler<OnSelectedEventArgs<T>> OnSelected;
    public final EventHandler<OnClearedEventArgs> OnCleared;

    private final ObservableList<T> _observableList;
    private final List<Integer> _selectedIndexList;
    private SelectionMode _selectionMode;


    /**
     * @param observableList
     */
    public SelectionModel(final ObservableList<T> observableList) {
        this(observableList, SelectionMode.SINGLE);
    }

    /**
     * @param observableList
     * @param selectionMode
     */
    public SelectionModel(final ObservableList<T> observableList, final SelectionMode selectionMode) {
        _observableList = observableList;
        _selectionMode = selectionMode;
        _selectedIndexList = new ArrayList<>();
        OnSelected = new EventHandler<>();
        OnCleared = new EventHandler<>();
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
    public int getSelectedIndex() { return _selectedIndexList.getLast(); }


    /**
     * @param selectionMode
     */
    public void setSelectionMode(final SelectionMode selectionMode) { _selectionMode = selectionMode; }


    /**
     * @param index
     */
    public boolean select(final int index) {
        if (index < 0 || index >= _observableList.length())
            return false;

        if (_selectionMode == SelectionMode.SINGLE)
            clear();

        _selectedIndexList.add(index);
        final T selectedItem = _observableList.get(index);
        OnSelected.invoke(this, new OnSelectedEventArgs<>(selectedItem));
        return true;
    }

    /**
     *
     */
    public void clear() {
        _selectedIndexList.clear();
        OnCleared.invoke(this, new OnClearedEventArgs());
    }


    /**
     * @param <T>
     */
    public static class OnSelectedEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public OnSelectedEventArgs(T item) {
            super();

            Item = item;
        }
    }

    public static class OnClearedEventArgs extends EventArgs {

    }
}
