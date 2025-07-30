package com.williwadelmawisky.musicplayer.audiofx;

import com.williwadelmawisky.musicplayer.audio.ObservableValue;
import com.williwadelmawisky.musicplayer.audio.SelectionMode;
import com.williwadelmawisky.musicplayer.utilfx.EnumComboBox;

/**
 *
 */
public class SelectionModeComboBox extends EnumComboBox<SelectionMode> {

    private ObservableValue<SelectionMode> _selectionModeProperty;


    /**
     *
     */
    public SelectionModeComboBox() {
        super(SelectionMode.class, SelectionMode.ORDERED);
        valueProperty().addListener(this::onChanged_ComboBox);
    }


    /**
     * @param modeProperty
     */
    public void setSelectionModeProperty(final ObservableValue<SelectionMode> modeProperty) {
        _selectionModeProperty = modeProperty;
        _selectionModeProperty.OnChanged.addListener(this::onChanged_SelectionModeProperty);
    }

    /**
     *
     */
    public void unbind() {
        if (_selectionModeProperty == null)
            return;

        _selectionModeProperty.OnChanged.removeListener(this::onChanged_SelectionModeProperty);
    }


    /**
     * @param observable
     * @param oldVal
     * @param newVal
     */
    private void onChanged_ComboBox(final javafx.beans.value.ObservableValue<? extends SelectionMode> observable, final SelectionMode oldVal, final SelectionMode newVal) {
        _selectionModeProperty.setValue(newVal);
    }

    /**
     * @param sender
     * @param args
     */
    private void onChanged_SelectionModeProperty(final Object sender, final ObservableValue.ChangeEventArgs<SelectionMode> args) {
        if (getValue() != args.Value)
            setValue(args.Value);
    }
}
