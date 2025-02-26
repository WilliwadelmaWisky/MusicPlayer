package com.github.williwadelmawisky.musicplayer.routing;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class RouteProperty {

    private final ChangeDelegate _changeDelegate;
    private String _value;


    /**
     *
     */
    public RouteProperty() {
        this("/");
    }

    /**
     * @param defaultValue
     */
    public RouteProperty(final String defaultValue) {
        _value = defaultValue;
        _changeDelegate = new ChangeDelegate();
    }


    /**
     * @return
     */
    public ChangeDelegate getChangeDelegate() { return _changeDelegate; }

    /**
     * @return
     */
    public String getValue() { return _value; }


    /**
     * @param value
     */
    void setValue(final String value) {
        if (_value.equals(value))
            return;

        _value = value;
        final ChangeEvent changeEvent = new ChangeEvent(_value);
        _changeDelegate.invoke(changeEvent);
    }


    /**
     *
     */
    public static class ChangeDelegate {

        private final List<OnUpdate> _listenerList;

        /**
         *
         */
        public ChangeDelegate() {
            _listenerList = new LinkedList<>();
        }

        /**
         * @param changeEvent
         */
        private void invoke(final ChangeEvent changeEvent) {
            _listenerList.forEach(e -> e.invoke(changeEvent));
        }

        /**
         * @param e
         */
        public void addListener(final OnUpdate e) { _listenerList.add(e); }

        /**
         * @param e
         */
        public void removeListener(final OnUpdate e) { _listenerList.remove(e); }
    }


    /**
     *
     */
    public static class ChangeEvent {

        public final String Route;

        /**
         * @param route
         */
        public ChangeEvent(final String route) {
            Route = route;
        }
    }


    /**
     *
     */
    @FunctionalInterface
    public interface OnUpdate {

        /**
         * @param changeEvent
         */
        void invoke(final ChangeEvent changeEvent);
    }
}
