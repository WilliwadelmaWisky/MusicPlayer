package com.williwadelmawisky.musicplayer.json;

/**
 * The <code>InvalidSyntaxException</code> represents an error while trying to read a JSON string.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class InvalidSyntaxException extends RuntimeException {

    /**
     * Constructs a new invalid syntax exception with a specified message.
     * @param message A details message.
     */
    public InvalidSyntaxException(final String message) {
        super(message);
    }
}
