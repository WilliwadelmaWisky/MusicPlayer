package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class TestFieldType {

    /**
     *
     */
    @Test
    public void testIsEqual() {
        // PRIMITIVE
        assertTrue(FieldType.PRIMITIVE.isEqual(boolean.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Boolean.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(byte.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Byte.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(short.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Short.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(int.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Integer.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(long.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Long.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(float.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Float.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(double.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Double.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(char.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(Character.class));
        assertTrue(FieldType.PRIMITIVE.isEqual(String.class));
        assertFalse(FieldType.PRIMITIVE.isEqual(Object[].class));
        assertFalse(FieldType.PRIMITIVE.isEqual(Object.class));

        // ARRAY
        assertTrue(FieldType.ARRAY.isEqual(Object[].class));
        assertFalse(FieldType.ARRAY.isEqual(Object.class));

        // OBJECT
        assertTrue(FieldType.OBJECT.isEqual(Object.class));
    }


    /**
     *
     */
    @Test
    public void testGet() {
        // PRIMITIVE
        assertEquals(FieldType.PRIMITIVE, FieldType.get(boolean.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Boolean.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(byte.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Byte.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(short.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Short.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(int.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Integer.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(long.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Long.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(float.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Float.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(double.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Double.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(char.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(Character.class));
        assertEquals(FieldType.PRIMITIVE, FieldType.get(String.class));

        // ARRAY
        assertEquals(FieldType.ARRAY, FieldType.get(Object[].class));

        // OBJECT
        assertEquals(FieldType.OBJECT, FieldType.get(Object.class));
    }
}
