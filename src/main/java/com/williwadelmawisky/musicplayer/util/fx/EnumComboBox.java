package com.williwadelmawisky.musicplayer.util.fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.EnumSet;

/**
 * @param <T>
 */
public abstract class EnumComboBox<T extends Enum<T>> extends ComboBox<T> {

    /**
     * @param enumType
     * @param defaultValue
     */
    public EnumComboBox(final Class<T> enumType, final T defaultValue) {
        super();

        final ObservableList<T> items = FXCollections.observableList(EnumSet.allOf(enumType).stream().toList());
        this.setItems(items);
        this.setValue(defaultValue);
    }
}
