package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.event.EventArgs;
import com.williwadelmawisky.musicplayer.util.event.EventHandler;

import java.util.Random;

/**
 * @param <T>
 */
public class SelectionModel<T> {

    public final EventHandler<SelectEventArgs<T>> OnSelected;
    public final EventHandler<ClearEventArgs<T>> OnCleared;

    public final ObservableValue<SelectionMode> SelectionModeProperty;
    private final ObservableList<T> _observableList;
    private final Random _random;
    private int _selectedIndex;
    private T _selectedItem;


    /**
     * @param observableList
     * @param selectionMode
     */
    public SelectionModel(final ObservableList<T> observableList, final SelectionMode selectionMode) {
        OnSelected = new EventHandler<>();
        OnCleared = new EventHandler<>();

        SelectionModeProperty = new ObservableValue<>(selectionMode);
        _observableList = observableList;
        _selectedIndex = -1;
        _selectedItem = null;
        _random = new Random();

        _observableList.OnItemRemoved.addListener(this::onRemoved_ObservableList);
        _observableList.OnCleared.addListener(this::onCleared_ObservableList);
        _observableList.OnSorted.addListener(this::onSorted_ObservableList);
    }


    /**
     * @return
     */
    public boolean hasValue() { return _selectedItem != null; }

    /**
     * @return
     */
    public T getValue() { return _selectedItem; }


    /**
     * @param index
     * @return
     */
    public boolean selectIndex(final int index) {
        if (index < 0 || index >= _observableList.length())
            return false;

        clearSelection();

        _selectedIndex = index;
        _selectedItem = _observableList.get(_selectedIndex);
        OnSelected.invoke(this, new SelectEventArgs<>(_selectedItem));
        return true;
    }

    /**
     * @return
     */
    public boolean selectNext() {
        if (_observableList.isEmpty() || !hasValue())
            return false;

        int index = 0;
        switch (SelectionModeProperty.getValue()) {
            case ORDERED -> index = (_selectedIndex + 1) % _observableList.length();
            case RANDOMIZED -> {
                index = _random.nextInt(_observableList.length());
                while (index == _selectedIndex && _observableList.length() > 1)
                    index = _random.nextInt(_observableList.length());
            }
            case REPEATED -> index = _selectedIndex;
        }

        return selectIndex(index);
    }

    /**
     * @return
     */
    public boolean selectPrevious() {
        if (_observableList.isEmpty() || !hasValue())
            return false;

        int index = 0;
        switch (SelectionModeProperty.getValue()) {
            case ORDERED -> index = (_selectedIndex <= 0) ? _observableList.length() - 1 : _selectedIndex - 1;
            case RANDOMIZED -> {
                index = _random.nextInt(_observableList.length());
                while (index == _selectedIndex && _observableList.length() > 1)
                    index = _random.nextInt(_observableList.length());
            }
            case REPEATED -> index = _selectedIndex;
        }

        return selectIndex(index);
    }


    /**
     *
     */
    public void clearSelection() {
        if (_selectedIndex == -1)
            return;

        final T item = _selectedItem;

        _selectedIndex = -1;
        _selectedItem = null;
        OnCleared.invoke(this, new ClearEventArgs<>(item));
    }


    /**
     * @param sender
     * @param args
     */
    private void onRemoved_ObservableList(final Object sender, final ObservableList.RemoveEventArgs<T> args) {
        if (args.Item.equals(_selectedItem))
            selectNext();
    }

    /**
     * @param sender
     * @param args
     */
    private void onCleared_ObservableList(final Object sender, final ObservableList.ClearEventArgs<T> args) {
        clearSelection();
    }

    /**
     * @param sender
     * @param args
     */
    private void onSorted_ObservableList(final Object sender, final EventArgs args) {
        if (_selectedIndex ==  -1)
            return;

        final int index = _observableList.indexOf(item -> item.equals(_selectedItem));
        if (index != -1) {
            _selectedIndex = index;
            return;
        }

        selectIndex(0);
    }


    /**
     * @param <T>
     */
    public static class SelectEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public SelectEventArgs(final T item) {
            Item = item;
        }
    }

    /**
     * @param <T>
     */
    public static class ClearEventArgs<T> extends EventArgs {

        public final T Item;

        /**
         * @param item
         */
        public ClearEventArgs(final T item) {
            Item = item;
        }
    }
}
