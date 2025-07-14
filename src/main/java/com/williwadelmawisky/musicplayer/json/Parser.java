package com.williwadelmawisky.musicplayer.json;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * The <code>Parser</code> provides a method to create an object from JSON string.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class Parser {

    /**
     * Constructs a parser with a default serialization table.
     */
    public Parser() {}


    /**
     * Reads the data of an object from a JSON string.
     * @param s The JSON string to read.
     * @param obj The object to write the data into.
     * @throws InvalidSyntaxException Thrown if there is a syntax problem in the given JSON string.
     */
    public void parse(final String s, final Object obj) throws InvalidSyntaxException {
        final Tokenizer tokenizer = new Tokenizer(s);
        final Token token = tokenizer.next();

        if (token.TokenType == Token.Type.OBJECT_START) {
            parseObject(obj, tokenizer);
            return;
        }

        throw new InvalidSyntaxException("invalid object start token");
    }


    /**
     * Reads the data of an object from the tokenizer.
     * @param obj The object to write the data into.
     * @param tokenizer The tokenizer to read the JSON string.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private void parseObject(final Object obj, final Tokenizer tokenizer) throws InvalidSyntaxException {
        while (true) {
            final Token fieldNameToken = tokenizer.next();
            if (fieldNameToken.TokenType == Token.Type.OBJECT_END) // End of object
                break;

            final Token colonToken = tokenizer.next();
            if (colonToken == null || colonToken.TokenType != Token.Type.COLON)
                throw new InvalidSyntaxException("missing colon");

            final Token valueToken = tokenizer.next();
            if (valueToken == null)
                throw new InvalidSyntaxException("missing value");

            try {
                final Field field = Fields.getField(obj, fieldNameToken.Value);
                parseObjectField(obj, field, tokenizer, valueToken);
            } catch (NoSuchFieldException e) {
                // No field found, skip
                switch (valueToken.TokenType) {
                    case OBJECT_START -> skip(FieldType.OBJECT, tokenizer);
                    case ARRAY_START -> skip(FieldType.ARRAY, tokenizer);
                }
            }

            final Token commaToken = tokenizer.next();
            if (commaToken.TokenType == Token.Type.OBJECT_END) // End of object
                break;
        }
    }

    /**
     * Read the data of an array from the tokenizer.
     * @param array The array to write the data into.
     * @param tokenizer The tokenizer to read the JSON string.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private void parseArray(final Object array, final Tokenizer tokenizer) throws InvalidSyntaxException {
        int i = 0;
        while (true) {
            final Token valueToken = tokenizer.next();
            if (valueToken.TokenType == Token.Type.ARRAY_END) // End of array
                break;

            parseArrayElement(array, i, tokenizer, valueToken);
            final Token commaToken = tokenizer.next();
            if (commaToken.TokenType == Token.Type.ARRAY_END) // End of array
                break;

            i++;
        }
    }


    /**
     * Read the data of the field of an object from the tokenizer.
     * @param obj The object whose field is modified.
     * @param field The field to write the data into.
     * @param tokenizer The tokenizer to read the JSON string.
     * @param valueToken The value token field.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private void parseObjectField(final Object obj, final Field field, final Tokenizer tokenizer, final Token valueToken) throws InvalidSyntaxException {
        if (!Fields.isSerializable(field)) {
            skip(FieldType.get(field), tokenizer);
            return;
        }

        try {
            if (field.getType().isArray()) {
                if (valueToken.TokenType != Token.Type.ARRAY_START)
                    throw new InvalidSyntaxException("invalid array start token");

                final int length = getArrayLength(tokenizer);
                final Object array = Array.newInstance(field.getType().getComponentType(), length);
                parseArray(array, tokenizer);
                field.set(obj, array);
            }
            else if (field.getType() == boolean.class) field.setBoolean(obj, Boolean.parseBoolean(valueToken.Value));
            else if (field.getType() == Boolean.class) field.set(obj, Boolean.valueOf(valueToken.Value));
            else if (field.getType() == byte.class) field.setByte(obj, Byte.parseByte(valueToken.Value));
            else if (field.getType() == Byte.class) field.set(obj, Byte.valueOf(valueToken.Value));
            else if (field.getType() == short.class) field.setShort(obj, Short.parseShort(valueToken.Value));
            else if (field.getType() == Short.class) field.set(obj, Short.valueOf(valueToken.Value));
            else if (field.getType() == int.class) field.setInt(obj, Integer.parseInt(valueToken.Value));
            else if (field.getType() == Integer.class) field.set(obj, Integer.valueOf(valueToken.Value));
            else if (field.getType() == long.class) field.setLong(obj, Long.parseLong(valueToken.Value));
            else if (field.getType() == Long.class) field.set(obj, Long.valueOf(valueToken.Value));
            else if (field.getType() == float.class) field.setFloat(obj, Float.parseFloat(valueToken.Value));
            else if (field.getType() == Float.class) field.set(obj, Float.valueOf(valueToken.Value));
            else if (field.getType() == double.class) field.setDouble(obj, Double.parseDouble(valueToken.Value));
            else if (field.getType() == Double.class) field.set(obj, Double.valueOf(valueToken.Value));
            else if (field.getType() == String.class) field.set(obj, valueToken.Value);
            else if (field.getType() == char.class) {
                if (valueToken.Value.length() > 1)
                    throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                field.setChar(obj, valueToken.Value.charAt(0));
            }
            else if (field.getType() == Character.class) {
                if (valueToken.Value.length() > 1)
                    throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                field.set(obj, Character.valueOf(valueToken.Value.charAt(0)));
            }
            else {
                final Constructor<?> constructor = field.getType().getConstructor();
                final Object innerObject = constructor.newInstance();

                if (innerObject.getClass().isArray()) {
                    if (valueToken.TokenType != Token.Type.ARRAY_START)
                        throw new InvalidSyntaxException("invalid array start token");

                    final int length = getArrayLength(tokenizer);
                    final Object array = Array.newInstance(innerObject.getClass().getComponentType(), length);
                    parseArray(array, tokenizer);
                    field.set(obj, array);
                }
                else if (innerObject.getClass() == Boolean.class) field.set(obj, Boolean.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Byte.class) field.set(obj, Byte.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Short.class) field.set(obj, Short.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Integer.class) field.set(obj, Integer.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Long.class) field.set(obj, Long.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Float.class) field.set(obj, Float.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Double.class) field.set(obj, Double.valueOf(valueToken.Value));
                else if (innerObject.getClass() == String.class) field.set(obj, valueToken.Value);
                else if (innerObject.getClass() == Character.class) {
                    if (valueToken.Value.length() > 1)
                        throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                    field.set(obj, Character.valueOf(valueToken.Value.charAt(0)));
                }
                else {
                    if (valueToken.TokenType != Token.Type.OBJECT_START)
                        throw new InvalidSyntaxException("invalid object start token");

                    parseObject(innerObject, tokenizer);
                    field.set(obj, innerObject);
                }
            }
        } catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new InvalidSyntaxException(String.format("value cannot be converted to a number: %s", valueToken.Value));
        }
    }


    /**
     * Read the data of an array from the tokenizer.
     * @param array The array to modify.
     * @param index The index of the array element.
     * @param tokenizer The tokenizer to read the JSON string.
     * @param valueToken The value token of the array element.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private void parseArrayElement(final Object array, final int index, final Tokenizer tokenizer, final Token valueToken) throws InvalidSyntaxException {
        try {
            if (array.getClass().getComponentType().isArray()) {
                if (valueToken.TokenType != Token.Type.ARRAY_START)
                    throw new InvalidSyntaxException(String.format("invalid array start token: %s", valueToken.Value));

                final int length = getArrayLength(tokenizer);
                final Object innerArray = Array.newInstance(array.getClass().getComponentType().getComponentType(), length);
                parseArray(innerArray, tokenizer);
                Array.set(array, index, innerArray);
            }
            else if (array.getClass().getComponentType() == boolean.class) Array.setBoolean(array, index, Boolean.parseBoolean(valueToken.Value));
            else if (array.getClass().getComponentType() == Boolean.class) Array.set(array, index, Boolean.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == byte.class) Array.setByte(array, index, Byte.parseByte(valueToken.Value));
            else if (array.getClass().getComponentType() == Byte.class) Array.set(array, index, Byte.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == short.class) Array.setShort(array, index, Short.parseShort(valueToken.Value));
            else if (array.getClass().getComponentType() == Short.class) Array.set(array, index, Short.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == int.class) Array.setInt(array, index, Integer.parseInt(valueToken.Value));
            else if (array.getClass().getComponentType() == Integer.class) Array.set(array, index, Integer.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == long.class) Array.setLong(array, index, Long.parseLong(valueToken.Value));
            else if (array.getClass().getComponentType() == Long.class) Array.set(array, index, Long.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == float.class) Array.setFloat(array, index, Float.parseFloat(valueToken.Value));
            else if (array.getClass().getComponentType() == Float.class) Array.set(array, index, Float.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == double.class) Array.setDouble(array, index, Double.parseDouble(valueToken.Value));
            else if (array.getClass().getComponentType() == Double.class) Array.set(array, index, Double.valueOf(valueToken.Value));
            else if (array.getClass().getComponentType() == String.class) Array.set(array, index, valueToken.Value);
            else if (array.getClass().getComponentType() == char.class) {
                if (valueToken.Value.length() > 1)
                    throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                Array.setChar(array, index, valueToken.Value.charAt(0));
            }
            else if (array.getClass().getComponentType() == Character.class) {
                if (valueToken.Value.length() > 1)
                    throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                Array.set(array, index, Character.valueOf(valueToken.Value.charAt(0)));
            }
            else {
                final Constructor<?> constructor = array.getClass().getComponentType().getConstructor();
                final Object innerObject = constructor.newInstance();

                if (innerObject.getClass().isArray()) {
                    if (valueToken.TokenType != Token.Type.ARRAY_START)
                        throw new InvalidSyntaxException("invalid array start token");

                    final int length = getArrayLength(tokenizer);
                    final Object arrayObject = Array.newInstance(innerObject.getClass().getComponentType(), length);
                    parseArray(arrayObject, tokenizer);
                    Array.set(array, index, arrayObject);
                }
                else if (innerObject.getClass() == Boolean.class) Array.set(array, index, Boolean.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Byte.class) Array.set(array, index, Byte.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Short.class) Array.set(array, index, Short.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Integer.class) Array.set(array, index, Integer.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Long.class) Array.set(array, index, Long.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Float.class) Array.set(array, index, Float.valueOf(valueToken.Value));
                else if (innerObject.getClass() == Double.class) Array.set(array, index, Double.valueOf(valueToken.Value));
                else if (innerObject.getClass() == String.class) Array.set(array, index, valueToken.Value);
                else if (innerObject.getClass() == Character.class) {
                    if (valueToken.Value.length() > 1)
                        throw new InvalidSyntaxException(String.format("string cannot be converted to character: %s", valueToken.Value));

                    Array.set(array, index, Character.valueOf(valueToken.Value.charAt(0)));
                }
                else {
                    if (valueToken.TokenType != Token.Type.OBJECT_START)
                        throw new InvalidSyntaxException("invalid object start token");

                    parseObject(innerObject, tokenizer);
                    Array.set(array, index, innerObject);
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new InvalidSyntaxException(String.format("value cannot be converted to a number: %s", valueToken.Value));
        }
    }


    /**
     * Reads the length of an array. Creates a secondary tokenizer to peek the future tokens.
     * @param tokenizer The tokenizer to read from.
     * @return The length of the array.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private int getArrayLength(final Tokenizer tokenizer) throws InvalidSyntaxException {
        final Tokenizer seeker = new Tokenizer(tokenizer.TargetString, tokenizer.currentIndex());
        return skip(FieldType.ARRAY, seeker);
    }


    /**
     * Skips a field on a tokenizer. Only objects and array can be skipped.
     * @param fieldType The type of the field.
     * @param tokenizer The tokenizer to read from.
     * @return The amount of fields or array elements skipped. Does not include inner objects/array.
     * @throws InvalidSyntaxException Thrown if a syntax error is encountered.
     */
    private int skip(final FieldType fieldType, final Tokenizer tokenizer) throws InvalidSyntaxException {
        switch (fieldType) {
            case OBJECT -> {
                int count = 0;
                while (true) {
                    final Token fieldNameToken = tokenizer.next();
                    if (fieldNameToken.TokenType == Token.Type.OBJECT_END) // End of object
                        break;

                    final Token colonToken = tokenizer.next();
                    if (colonToken.TokenType != Token.Type.COLON)
                        throw new InvalidSyntaxException("missing colon");

                    final Token valueToken = tokenizer.next();
                    switch (valueToken.TokenType) {
                        case Token.Type.OBJECT_START -> skip(FieldType.OBJECT, tokenizer);
                        case Token.Type.ARRAY_START -> skip(FieldType.ARRAY, tokenizer);
                    }

                    count++;
                    final Token commaToken = tokenizer.next();
                    if (commaToken.TokenType == Token.Type.OBJECT_END) // End of object
                        break;
                }

                return count;
            }
            case ARRAY -> {
                int count = 0;
                while (true) {
                    final Token valueToken = tokenizer.next();
                    if (valueToken.TokenType == Token.Type.ARRAY_END) // End of array
                        break;

                    switch (valueToken.TokenType) {
                        case Token.Type.OBJECT_START -> skip(FieldType.OBJECT, tokenizer);
                        case Token.Type.ARRAY_START -> skip(FieldType.ARRAY, tokenizer);
                    }

                    count++;
                    final Token commaToken = tokenizer.next();
                    if (commaToken.TokenType == Token.Type.ARRAY_END) // End of array
                        break;
                }

                return count;
            }
        }

        return 0;
    }
}
