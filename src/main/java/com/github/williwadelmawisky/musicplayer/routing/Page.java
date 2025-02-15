package com.github.williwadelmawisky.musicplayer.routing;

import javafx.scene.Parent;

/**
 *
 */
public interface Page {

    /**
     * @return
     */
    Parent getRoot();

    /**
     *
     */
    void onLoad();

    /**
     *
     */
    void onUnload();
}
