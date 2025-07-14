package com.williwadelmawisky.musicplayer.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * <code>Fields</code> contain a couple of useful static methods for working with fields (reflection).
 * This class is not meant to be instantiated or inherited from.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public abstract class Fields {

    /**
     * Gets a declared field of an object or from its superclasses.
     * @param obj The object whose field is being search of.
     * @param name The name of the field.
     * @return The found field.
     * @throws NoSuchFieldException Thrown if a field with the specified name is not found.
     */
    public static Field getField(final Object obj, final String name) throws NoSuchFieldException {
        Class<?> cls = obj.getClass();
        while (cls != null) {
            try {
                final Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }

        throw new NoSuchFieldException("field does not exist");
    }

    /**
     * Gets all the declared fields from an object and its superclasses.
     * @param obj The object whose fields are being requested.
     * @return An array of fields found.
     */
    public static Field[] getFields(final Object obj) {
        return getFields(obj, field -> true);
    }

    /**
     * Gets all the declared fields from an object and its superclasses that match the specified predicate.
     * @param obj The object whose fields are being requested.
     * @param predicate The predicate to define if the field should be included in the listing.
     * @return An array of fields found.
     */
    public static Field[] getFields(final Object obj, final Predicate<Field> predicate) {
        final List<Field> fieldList = new ArrayList<>();
        Class<?> cls = obj.getClass();
        while (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                if (!predicate.test(field))
                    continue;

                field.setAccessible(true);
                fieldList.add(field);
            }

            cls = cls.getSuperclass();
        }

        return fieldList.toArray(new Field[0]);
    }


    /**
     * Checks if a field is serializable.
     * @param field The field to check.
     * @return True if the field is serializable.
     */
    public static boolean isSerializable(final Field field) {
        return field.isAnnotationPresent(SerializeField.class);
    }
}
