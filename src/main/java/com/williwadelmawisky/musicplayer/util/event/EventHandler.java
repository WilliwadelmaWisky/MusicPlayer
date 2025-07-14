package com.williwadelmawisky.musicplayer.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class EventHandler<T extends EventArgs> {

    private final List<Event<T>> _listenerList;

    /**
     *
     */
    public EventHandler() {
        _listenerList = new ArrayList<>();
    }

    /**
     * @param sender
     * @param args
     */
    public void invoke(final Object sender, final T args) { _listenerList.forEach(listener -> listener.invoke(sender, args)); }

    /**
     * @param listener
     */
    public void addListener(final Event<T> listener) { _listenerList.add(listener); }

    /**
     * @param listener
     */
    public void removeListener(final Event<T> listener) { _listenerList.remove(listener); }
}
