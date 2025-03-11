package com.github.williwadelmawisky.utils;

import java.util.Random;

/**
 * @param <T>
 */
public class RandomizedSelector<T> implements Selector<T> {

    private final Random _random;


    /**
     *
     */
    public RandomizedSelector() {
        _random = new Random();
    }


    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean next(final SelectionModel<T> selectionModel) {
        if (selectionModel == null || selectionModel.isEmpty())
            return false;

        int index = _random.nextInt(selectionModel.size());
        while (index == selectionModel.getSelectedIndex() && selectionModel.size() > 1)
            index = _random.nextInt(selectionModel.size());

        return selectionModel.select(index);
    }

    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean previous(final SelectionModel<T> selectionModel) {
        int index = _random.nextInt(selectionModel.size());
        while (index == selectionModel.getSelectedIndex() && selectionModel.size() > 1)
            index = _random.nextInt(selectionModel.size());

        return selectionModel.select(index);
    }
}
