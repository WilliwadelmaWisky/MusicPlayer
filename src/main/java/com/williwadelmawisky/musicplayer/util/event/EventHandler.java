package com.williwadelmawisky.musicplayer.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class EventHandler<T extends Event> {

    private final List<EventListener<T>> _listenerList;


    /**
     *
     */
    public EventHandler() {
        _listenerList = new ArrayList<>();
    }


    /**
     * @param sender
     * @param args
     * @throws NullEventListenerException
     */
    public void invoke(final Object sender, final T args) throws NullEventListenerException {
        _listenerList.forEach(listener -> {
            if (listener == null)
                throw new NullEventListenerException("event-handler contains null listener");

            listener.invoke(sender, args);
        });
    }

    /**
     *
     */
    public void clear() {
        _listenerList.clear();
    }

    /**
     * @param eventListener
     */
    public void addListener(final EventListener<T> eventListener) { _listenerList.add(eventListener); }

    /**
     * @param eventListener
     */
    public void removeListener(final EventListener<T> eventListener) { _listenerList.remove(eventListener); }
}
