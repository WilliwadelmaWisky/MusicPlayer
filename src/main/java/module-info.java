module com.github.williwadelmawisky.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.github.williwadelmawisky.musicplayer to javafx.fxml;
    exports com.github.williwadelmawisky.musicplayer;
}