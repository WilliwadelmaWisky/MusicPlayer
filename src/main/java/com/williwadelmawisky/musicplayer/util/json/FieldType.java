package com.williwadelmawisky.musicplayer.util.json;

import java.lang.reflect.Field;

/**
 * The <code>FieldType</code> represents a type of a field or and object.
 * <ul>
 *     <li>ARRAY: Represents an array.</li>
 *     <li>PRIMITIVE: Represents any of the primitive types and their wrapper classes, and also a string.</li>
 *     <li>OBJECT: Represents any object that is not an array or a primitive.</li>
 * </ul>
 */
public enum FieldType {
    ARRAY,
    PRIMITIVE,
    OBJECT;

    /**
     * Checks if the specified type is equal to this field type.
     * @param type The type to check.
     * @return True if the type is same.
     */
    public boolean isEqual(final Class<?> type) {
        return switch (this) {
            case PRIMITIVE -> type == boolean.class || type == Boolean.class
                    || type == byte.class || type == Byte.class
                    || type == short.class || type == Short.class
                    || type == int.class || type  == Integer.class
                    || type == long.class || type == Long.class
                    || type == float.class || type == Float.class
                    || type == double.class || type == Double.class
                    || type == char.class || type == Character.class
                    || type == String.class;
            case ARRAY -> type.isArray();
            case OBJECT -> true;
        };
    }


    /**
     * Finds a field type for an object field.
     * @param field The field of an object.
     * @return The field type of an object, never null.
     */
    public static FieldType get(final Field field) {
        return get(field.getType());
    }

    /**
     * Finds a field type for a specified type.
     * @param type The type.
     * @return The field type of the given type, never null.
     */
    public static FieldType get(final Class<?> type) {
        for (FieldType fieldType : FieldType.values()) {
            if (fieldType.isEqual(type))
                return fieldType;
        }

        return FieldType.OBJECT;
    }
}
