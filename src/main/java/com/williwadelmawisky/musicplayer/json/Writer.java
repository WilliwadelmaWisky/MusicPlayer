package com.williwadelmawisky.musicplayer.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * The <code>Writer</code> provides a method to write a JSON string from an object.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class Writer {

    /**
     * Constructs a writer with a default serialization table.
     */
    public Writer() {}


    /**
     * Writes the data of an object to a JSON string.
     * @param obj The object to write the data of.
     * @return The generated JSON string.
     * @throws NotSerializableException Thrown if the object is not serializable (<i>primitive or array etc.</i>).
     */
    public String write(final Object obj) throws NotSerializableException {
        final StringBuilder stringBuilder = new StringBuilder();
        write(obj, stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Writes the data of an object to a JSON string. The JSON string is written at the end of the given string builder.
     * @param obj The object to write the data of.
     * @param stringBuilder A string builder to write the data into.
     * @throws NotSerializableException Thrown if the object is not serializable (<i>primitive or array etc.</i>).
     */
    public void write(final Object obj, final StringBuilder stringBuilder) throws NotSerializableException {
        final FieldType fieldType = FieldType.get(obj.getClass());
        if (fieldType != FieldType.OBJECT)
            throw new NotSerializableException("class not serializable, only custom objects allowed");

        writeObject(obj, stringBuilder);
    }


    /**
     * Writes a data of an object to a string builder.
     * @param obj The object to write the data of.
     * @param stringBuilder A string builder to write the data into.
     */
    private void writeObject(final Object obj, final StringBuilder stringBuilder) {
        stringBuilder.append('{');

        for (Field field : Fields.getFields(obj, Fields::isSerializable)) {
            stringBuilder.append('\"').append(field.getName()).append('\"');
            stringBuilder.append(':');
            writeObjectField(obj, field, stringBuilder);
            stringBuilder.append(',');
        }

        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append('}');
    }


    /**
     * Writes a data of an array to a string builder.
     * @param obj The array object to write the data of.
     * @param stringBuilder The string builder to write the data into.
     */
    private void writeArray(final Object obj, final StringBuilder stringBuilder) {
        stringBuilder.append('[');

        final int arrayLength = Array.getLength(obj);
        for (int i = 0; i < arrayLength; i++) {
            writeArrayElement(obj, i, stringBuilder);
            stringBuilder.append(',');
        }

        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',')
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');
    }


    /**
     * Writes a value of a object field to a string builder.
     * @param obj The object whose field is written.
     * @param field The field to write the value of.
     * @param stringBuilder The string builder to write the data into.
     */
    private void writeObjectField(final Object obj, final Field field, final StringBuilder stringBuilder) {
        try {
            if (field.getType().isArray()) writeArray(field.get(obj), stringBuilder);
            else if (field.getType() == boolean.class) stringBuilder.append(field.getBoolean(obj));
            else if (field.getType() == Boolean.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == byte.class) stringBuilder.append(field.getByte(obj));
            else if (field.getType() == Byte.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == short.class) stringBuilder.append(field.getShort(obj));
            else if (field.getType() == Short.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == int.class) stringBuilder.append(field.getInt(obj));
            else if (field.getType() == Integer.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == long.class) stringBuilder.append(field.getLong(obj));
            else if (field.getType() == Long.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == float.class) stringBuilder.append(field.getFloat(obj));
            else if (field.getType() == Float.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == double.class) stringBuilder.append(field.getDouble(obj));
            else if (field.getType() == Double.class) stringBuilder.append(field.get(obj));
            else if (field.getType() == char.class) stringBuilder.append('\"').append(field.getChar(obj)).append('\"');
            else if (field.getType() == Character.class) stringBuilder.append('\"').append(field.get(obj)).append('\"');
            else if (field.getType() == String.class) stringBuilder.append('\"').append(field.get(obj).toString()).append('\"');
            else {
                final Object innerObject = field.get(obj);
                if (innerObject.getClass().isArray()) writeArray(innerObject, stringBuilder);
                else if (innerObject.getClass() == Boolean.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Byte.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Short.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Integer.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Long.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Float.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Double.class) stringBuilder.append(innerObject);
                else if (innerObject.getClass() == Character.class) stringBuilder.append('\"').append(innerObject).append('\"');
                else if (innerObject.getClass() == String.class) stringBuilder.append('\"').append(innerObject.toString()).append('\"');
                else writeObject(innerObject, stringBuilder);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Writes an value of an array element to a string builder.
     * @param obj The array object whose element is written.
     * @param index The index of the array element.
     * @param stringBuilder The string builder to write the value into.
     */
    private void writeArrayElement(final Object obj, final int index, final StringBuilder stringBuilder) {
        if (obj.getClass().getComponentType().isArray()) writeArray(Array.get(obj, index), stringBuilder);
        else if (obj.getClass().getComponentType() == boolean.class) stringBuilder.append(Array.getBoolean(obj, index));
        else if (obj.getClass().getComponentType() == Boolean.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == byte.class) stringBuilder.append(Array.getByte(obj, index));
        else if (obj.getClass().getComponentType() == Byte.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == short.class) stringBuilder.append(Array.getShort(obj, index));
        else if (obj.getClass().getComponentType() == Short.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == int.class) stringBuilder.append(Array.getInt(obj, index));
        else if (obj.getClass().getComponentType() == Integer.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == long.class) stringBuilder.append(Array.getLong(obj, index));
        else if (obj.getClass().getComponentType() == Long.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == float.class) stringBuilder.append(Array.getFloat(obj, index));
        else if (obj.getClass().getComponentType() == Float.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == double.class) stringBuilder.append(Array.getDouble(obj, index));
        else if (obj.getClass().getComponentType() == Double.class) stringBuilder.append(Array.get(obj, index));
        else if (obj.getClass().getComponentType() == char.class) stringBuilder.append('\"').append(Array.getChar(obj, index)).append('\"');
        else if (obj.getClass().getComponentType() == Character.class) stringBuilder.append('\"').append(Array.get(obj, index)).append('\"');
        else if (obj.getClass().getComponentType() == String.class) stringBuilder.append('\"').append(Array.get(obj, index).toString()).append('\"');
        else {
            final Object innerObject = Array.get(obj, index);
            if (innerObject.getClass().isArray()) writeArray(innerObject, stringBuilder);
            else if (innerObject.getClass() == Boolean.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Byte.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Short.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Integer.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Long.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Float.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Double.class) stringBuilder.append(innerObject);
            else if (innerObject.getClass() == Character.class) stringBuilder.append('\"').append(innerObject).append('\"');
            else if (innerObject.getClass() == String.class) stringBuilder.append('\"').append(innerObject.toString()).append('\"');
            else writeObject(innerObject, stringBuilder);
        }
    }
}
