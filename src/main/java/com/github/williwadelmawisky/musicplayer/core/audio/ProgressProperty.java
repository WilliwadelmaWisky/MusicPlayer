package com.github.williwadelmawisky.musicplayer.core.audio;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public final class ProgressProperty {


    /**
     *
     */
    public static final class UpdateEvent {

        private final List<OnUpdate> _listenerList;

        /**
         *
         */
        public UpdateEvent() {
            _listenerList = new LinkedList<>();
        }

        /**
         *
         */
        private void invoke() {
            _listenerList.forEach(e -> e.invoke());
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
    @FunctionalInterface
    public interface OnUpdate {

        /**
         *
         */
        void invoke();
    }
}
