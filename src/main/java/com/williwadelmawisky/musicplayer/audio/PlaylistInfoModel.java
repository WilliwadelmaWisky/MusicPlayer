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
    private final SaveManager _saveManager;


    /**
     *
     */
    public PlaylistInfoModel() {
        PlaylistInfoList = new UniqueObservableList<>();
        SelectionModel = new SelectionModel<>(PlaylistInfoList, SelectionMode.ORDERED);
        _saveManager = new SaveManager();

        final File baseDirectory = new File(BASE_DIRECTORY_PATH);
        Files.listFiles(baseDirectory, ALLOWED_EXTENSIONS, false, file -> {
            final PlaylistInfo playlistInfo = new PlaylistInfo();
            if (_saveManager.load(playlistInfo, file))
                PlaylistInfoList.add(playlistInfo);
        });
    }


    /**
     * @param playlistInfo
     * @param newName
     */
    public boolean rename(final PlaylistInfo playlistInfo, final String newName) {
        final File oldFile = Paths.get(BASE_DIRECTORY_PATH, playlistInfo.name() + ".playlist.json").toFile();
        if (!oldFile.delete())
            return false;

        playlistInfo.setName(newName);
        final File newFile = Paths.get(BASE_DIRECTORY_PATH, playlistInfo.name() + ".playlist.json").toFile();
        return _saveManager.save(playlistInfo, newFile);
    }

    /**
     * @param playlistInfo
     */
    public boolean delete(final PlaylistInfo playlistInfo) {
        if (!PlaylistInfoList.remove(playlistInfo))
            return false;

        final File file = Paths.get(BASE_DIRECTORY_PATH, playlistInfo.name() + ".playlist.json").toFile();
        return file.delete();
    }
}
