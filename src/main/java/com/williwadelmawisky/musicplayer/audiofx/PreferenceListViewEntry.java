package com.williwadelmawisky.musicplayer.audiofx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 */
public class PreferenceListViewEntry extends HBox {

    private final Label _label;

    /**
     *
     */
    public PreferenceListViewEntry() {
        super();

        _label = new Label();

        getChildren().addAll(_label);
    }
}
