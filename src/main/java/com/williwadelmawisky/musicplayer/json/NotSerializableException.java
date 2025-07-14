package com.williwadelmawisky.musicplayer.json;

/**
 * The <code>NotSerializableException</code> represents an error while trying to write a unserializable object, such as one of the primitive types or an array.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class NotSerializableException extends RuntimeException {

    /**
     * Constructs a new not serializable exception with the specified a message.
     * @param message A detail message of the exception.
     */
    public NotSerializableException(final String message) {
        super(message);
    }
}
