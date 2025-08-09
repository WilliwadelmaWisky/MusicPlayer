package com.williwadelmawisky.musicplayer.audio;

import com.williwadelmawisky.musicplayer.util.Files;

import java.io.File;
import java.nio.file.Paths;

/**
 *
 */
public class PlaylistInfoModel {

    private static final String BASE_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), ".config", "WilliwadelmaWisky", "MusicPlayer").toString();
    private static final String[] ALLOWED_EXTENSIONS = new String[] { ".playlist.json" };

    public final ObservableList<PlaylistInfo> PlaylistInfoList;
    public final SelectionModel<PlaylistInfo> SelectionModel;


    /**
     *
     */
    public PlaylistInfoModel() {
        PlaylistInfoList = new UniqueObservableList<>();
        SelectionModel = new SelectionModel<>(PlaylistInfoList, SelectionMode.ORDERED);

        final SaveManager saveManager = new SaveManager();
        final File baseDirectory = new File(BASE_DIRECTORY_PATH);
        Files.listFiles(baseDirectory, ALLOWED_EXTENSIONS, false, file -> {
            final PlaylistInfo playlistInfo = new PlaylistInfo();
            if (saveManager.load(playlistInfo, file))
                PlaylistInfoList.add(playlistInfo);
        });
    }
}
