package com.github.williwadelmawisky.musicplayer.routing;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Router {

    /**
     *
     */
    @FunctionalInterface
    public interface OnRouteSelected {

        /**
         * @param page
         */
        void invoke(final Page page);
    }

    private String _path;
    private OnRouteSelected _onRouteSelected;
    private final Map<String, Route> _routeMap;
    private final Page _notFoundPage;


    /**
     * @param notFound
     */
    public Router(final Page notFound) {
        _routeMap = new HashMap<>();
        _notFoundPage = notFound;
        _path = "/";
    }


    /**
     * @param path
     * @return
     */
    public Route getRoute(final String path) {
        return _routeMap.get(path);
    }

    /**
     * @return
     */
    public Route getRoute() {
        return getRoute(_path);
    }

    /**
     * @param path
     */
    public void setRoute(final String path) {
        _path = path;
        if (_routeMap.containsKey(_path)) {
            final Route route = _routeMap.get(_path);
            _onRouteSelected.invoke(route.getPage());
            return;
        }

        _onRouteSelected.invoke(_notFoundPage);
    }

    /**
     * @param onRouteSelected
     */
    public void setOnRouteSelected(OnRouteSelected onRouteSelected) {
        _onRouteSelected = onRouteSelected;
    }


    /**
     * @param route
     */
    public void addRoute(final Route route) {
        _routeMap.put(route.getPath(), route);
    }

    /**
     * @param path
     * @param page
     */
    public void addRoute(final String path, Page page) {
        addRoute(new Route(path, page));
    }


    /**
     * @param page
     * @return
     */
    public static Router singlePageRouter(final Page page) {
        final Router router = new Router(null);
        router.addRoute("/", page);
        return router;
    }
}
