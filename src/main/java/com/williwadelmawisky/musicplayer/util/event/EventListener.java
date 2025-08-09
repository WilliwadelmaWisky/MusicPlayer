package com.williwadelmawisky.musicplayer.util.event;

/**
 * @param <T>
 */
@FunctionalInterface
public interface EventListener<T extends Event> {

    /**
     * @param sender
     * @param event
     */
    void invoke(final Object sender, final T event);
}
