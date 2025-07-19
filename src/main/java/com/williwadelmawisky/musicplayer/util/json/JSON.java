package com.williwadelmawisky.musicplayer.util.json;

/**
 * <code>JSON</code> class contains static methods for creating and parsing a JSON string very easily.
 * This class is not meant to be instantiated or inherited from.
 * <p>
 * For finer control of creating and parsing JSON string check out <code>Parser</code> and <code>Writer</code>.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public abstract class JSON {

    /**
     * Writes a serializable object to a JSON string.
     * <p>
     * For object to be serializable it must have a parameterless contructor and to not be an array or a string or a primitive type.
     * The object is however allowed to have arrays, strings and primitives as a field.
     * @param obj An object to write.
     * @return A JSON string.
     * @throws NotSerializableException Thrown if the object is not serializable (<i>primitive or array etc</i>).
     */
    public static String toJSON(final Object obj) throws NotSerializableException {
        final Writer writer = new Writer();
        return writer.write(obj);
    }

    /**
     * Parses an object values from a JSON string.
     * @param s A JSON string to parse from.
     * @param obj An object which the JSON string represents. The data is written to this object.
     * @throws InvalidSyntaxException Thrown if there are errors in the JSON string.
     */
    public static void fromJSON(final String s, final Object obj) throws InvalidSyntaxException {
        final Parser parser = new Parser();
        parser.parse(s, obj);
    }
}
