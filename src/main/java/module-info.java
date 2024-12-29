module com.github.williwadelmawisky.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports com.github.williwadelmawisky.musicplayer;
    opens com.github.williwadelmawisky.musicplayer to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.core.control;
    opens com.github.williwadelmawisky.musicplayer.core.control to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.core.data;
    exports com.github.williwadelmawisky.musicplayer.core.db;
    exports com.github.williwadelmawisky.musicplayer.routing;
    opens com.github.williwadelmawisky.musicplayer.routing to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.scene.pages;
    opens com.github.williwadelmawisky.musicplayer.scene.pages to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.scene.controls;
    opens com.github.williwadelmawisky.musicplayer.scene.controls to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.stage;
    opens com.github.williwadelmawisky.musicplayer.stage to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.util;
    opens com.github.williwadelmawisky.musicplayer.util to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.core.control.audio;
    opens com.github.williwadelmawisky.musicplayer.core.control.audio to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer.util.event;
    opens com.github.williwadelmawisky.musicplayer.util.event to javafx.fxml;
}