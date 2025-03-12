module com.github.williwadelmawisky.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports com.github.williwadelmawisky.musicplayer;
    opens com.github.williwadelmawisky.musicplayer to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.audio;
    opens com.github.williwadelmawisky.musicplayer.audio to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.database;
    exports com.github.williwadelmawisky.musicplayer.routing;
    opens com.github.williwadelmawisky.musicplayer.routing to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.scene.pages;
    opens com.github.williwadelmawisky.musicplayer.scene.pages to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.scene.controls;
    opens com.github.williwadelmawisky.musicplayer.scene.controls to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.stage;
    opens com.github.williwadelmawisky.musicplayer.stage to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.utils;
    exports com.github.williwadelmawisky.musicplayer.fxutils;
    opens com.github.williwadelmawisky.musicplayer.fxutils to javafx.fxml;
}