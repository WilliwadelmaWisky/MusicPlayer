package com.github.williwadelmawisky.musicplayer.scene.controls;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 */
public class SearchPrefixPanel extends HBox {

    /**
     *
     */
    public SearchPrefixPanel() {
        super();
        setSpacing(2);

        for (int i = 0; i < 26; i++) {
            final int asciiCode = 65 + i;
            final String text = Character.toString((char)asciiCode);
            final Button button = new Button(text);
            button.setOnAction(this::onButtonClicked);
            getChildren().add(button);
        }
    }


    /**
     * @param e
     */
    private void onButtonClicked(ActionEvent e) {
        final String searchPrefix = ((Button)e.getTarget()).getText();
        System.out.println(searchPrefix);
    }
}
