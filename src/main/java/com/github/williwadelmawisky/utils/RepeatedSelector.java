package com.github.williwadelmawisky.utils;

/**
 * @param <T>
 */
public class RepeatedSelector<T> implements Selector<T> {

    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean next(final SelectionModel<T> selectionModel) {
        if (selectionModel == null || selectionModel.isEmpty())
            return false;

        final int index = selectionModel.getSelectedIndex();
        return selectionModel.select(index);
    }

    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean previous(final SelectionModel<T> selectionModel) {
        if (selectionModel == null || selectionModel.isEmpty())
            return false;

        final int index = selectionModel.getSelectedIndex();
        return selectionModel.select(index);
    }
}
