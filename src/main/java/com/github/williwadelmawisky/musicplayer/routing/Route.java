package com.github.williwadelmawisky.musicplayer.routing;

/**
 *
 */
public class Route {

    private final String _path;
    private final Page _page;


    /**
     * @param path
     * @param page
     */
    public Route(final String path, final Page page) {
        _path= path;
        _page = page;
    }


    /**
     * @return
     */
    public String getPath() {
        return _path;
    }

    /**
     * @return
     */
    public Page getPage() {
        return _page;
    }
}
