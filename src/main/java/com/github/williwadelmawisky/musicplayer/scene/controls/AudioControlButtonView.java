package com.github.williwadelmawisky.musicplayer.scene.controls;

import com.github.williwadelmawisky.musicplayer.ResourceLoader;
import com.github.williwadelmawisky.musicplayer.core.audio.StatusProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 */
public class AudioControlButtonView extends HBox {

    private final PlayButton _playButton;
    private EventHandler<ActionEvent> _onPrevious, _onNext;


    /**
     *
     */
    public AudioControlButtonView() {
        super();
        this.setSpacing(5);

        final Button previousButton = new Button();
        final ImageView previousButtonImageView = new ImageView(ResourceLoader.loadImage("img/previous_icon.png"));
        previousButtonImageView.setFitWidth(15);
        previousButtonImageView.setPickOnBounds(true);
        previousButtonImageView.setPreserveRatio(true);
        previousButton.setGraphic(previousButtonImageView);
        previousButton.setOnAction(_onPrevious);
        this.getChildren().add(previousButton);

        _playButton = new PlayButton();
        this.getChildren().add(_playButton);

        final Button nextButton = new Button();
        final ImageView nextButtonImageView = new ImageView(ResourceLoader.loadImage("img/next_icon.png"));
        nextButtonImageView.setFitWidth(15);
        nextButtonImageView.setPickOnBounds(true);
        nextButtonImageView.setPreserveRatio(true);
        nextButton.setGraphic(nextButtonImageView);
        nextButton.setOnAction(_onNext);
        this.getChildren().add(nextButton);
    }

    /**
     * @param statusProperty
     */
    public AudioControlButtonView(final StatusProperty statusProperty) {
        this();
        setStatusProperty(statusProperty);
    }


    /**
     * @param statusProperty
     */
    public void setStatusProperty(final StatusProperty statusProperty) {
        _playButton.setStatusProperty(statusProperty);
    }


    /**
     * @return
     */
    public EventHandler<ActionEvent> getOnPrevious() { return _onPrevious; }

    /**
     * @return
     */
    public EventHandler<ActionEvent> getOnNext() { return _onNext; }

    /**
     * @param onPrevious
     */
    public void setOnPrevious(EventHandler<ActionEvent> onPrevious) { _onPrevious = onPrevious; }

    /**
     * @param onNext
     */
    public void setOnNext(EventHandler<ActionEvent> onNext) { _onNext = onNext; }
}
