package com.github.williwadelmawisky.musicplayer.routing;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Router implements RedirectHandler {

    private final RouteProperty _routeProperty;
    private final Map<String, Page> _routeMap;


    /**
     *
     */
    public Router() {
        _routeProperty = new RouteProperty("/");
        _routeMap = new HashMap<>();
    }


    /**
     * @return
     */
    public RouteProperty getRouteProperty() { return _routeProperty; }

    /**
     * @param route
     * @return
     */
    public Page getPage(final String route) {
        return _routeMap.get(route);
    }


    /**
     * @param route
     */
    @Override
    public void setRoute(final String route) {
        _routeProperty.setValue(route);
    }


    /**
     * @param route
     * @param page
     */
    public void addRoute(final String route, Page page) {
        _routeMap.put(route, page);
    }
}
