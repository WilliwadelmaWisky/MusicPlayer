module com.github.williwadelmawisky.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    exports com.williwadelmawisky.musicplayer;
    opens com.williwadelmawisky.musicplayer to javafx.fxml;
    exports com.williwadelmawisky.musicplayer.audio;
    opens com.williwadelmawisky.musicplayer.audio to javafx.fxml;
    exports com.williwadelmawisky.musicplayer.audiofx;
    opens com.williwadelmawisky.musicplayer.audiofx to javafx.fxml;
    exports com.williwadelmawisky.musicplayer.util;
    opens com.williwadelmawisky.musicplayer.util to javafx.fxml;
    exports com.williwadelmawisky.musicplayer.util.event;
    opens com.williwadelmawisky.musicplayer.util.event to javafx.fxml;
    exports com.williwadelmawisky.musicplayer.utilfx;
    opens com.williwadelmawisky.musicplayer.utilfx to javafx.fxml;
}