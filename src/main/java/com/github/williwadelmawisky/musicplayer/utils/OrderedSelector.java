package com.github.williwadelmawisky.musicplayer.utils;

/**
 * @param <T>
 */
public class OrderedSelector<T> implements Selector<T> {

    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean previous(final SelectionModel<T> selectionModel) {
        if (selectionModel == null || selectionModel.isEmpty())
            return false;

        final int index = (selectionModel.getSelectedIndex() <= 0) ? selectionModel.size() - 1 : selectionModel.getSelectedIndex() - 1;
        return selectionModel.select(index);
    }

    /**
     * @param selectionModel
     * @return
     */
    @Override
    public boolean next(final SelectionModel<T> selectionModel) {
        if (selectionModel == null || selectionModel.isEmpty())
            return false;

        final int index = (selectionModel.getSelectedIndex() + 1) % selectionModel.size();
        return selectionModel.select(index);
    }
}
