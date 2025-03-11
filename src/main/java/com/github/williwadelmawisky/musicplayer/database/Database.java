package com.github.williwadelmawisky.musicplayer.database;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Database {

    /**
     *
     */
    public enum TableType {
        SONG,
        ARTIST,
        PLAYLIST,
        FILE
    }

    private final Map<TableType, Table> _tableMap;


    /**
     * @param path
     */
    public Database(final String path) {
        _tableMap = new HashMap<>();
        _tableMap.put(TableType.SONG, new Table(Paths.get(path, TableType.SONG.name()).toString(), SongData::new));
        _tableMap.put(TableType.ARTIST, new Table(Paths.get(path, TableType.ARTIST.name()).toString(), ArtistData::new));
        _tableMap.put(TableType.PLAYLIST, new Table(Paths.get(path, TableType.PLAYLIST.name()).toString(), PlaylistData::new));
        _tableMap.put(TableType.FILE, new Table(Paths.get(path, TableType.FILE.name()).toString(), FileData::new));
    }


    /**
     * @param tableType
     * @return
     */
    public Table getTable(final TableType tableType) {
        return _tableMap.get(tableType);
    }


    /**
     *
     */
    public void save() {
        _tableMap.values().forEach(Table::save);
    }

    /**
     *
     */
    public void load() {
        _tableMap.values().forEach(Table::load);
    }
}
