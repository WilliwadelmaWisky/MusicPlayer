package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 *
 */
public class TestFields {

    /**
     *
     */
    @Test
    public void testGetField() throws NoSuchFieldException {
        final Object_InheritanceTest.InheritedClass obj = new Object_InheritanceTest.InheritedClass();

        // DECLARED FIELD
        assertNotNull(Fields.getField(obj, "pDouble"));

        // INHERITED FIELD
        assertNotNull(Fields.getField(obj, "pInt"));

        // NO FIELD
        assertThrows(NoSuchFieldException.class, () -> Fields.getField(obj, ""));
    }

    /**
     *
     */
    @Test
    public void testGetFields() {
        final Object_InheritanceTest.InheritedClass obj = new Object_InheritanceTest.InheritedClass();

        // ALL FIELDS
        final Field[] allFields = Fields.getFields(obj);
        assertEquals(2, allFields.length);
        assertEquals("pDouble", allFields[0].getName());
        assertEquals("pInt", allFields[1].getName());

        // DECLARED FIELDS WITH PREDICATE
        final Field[] declaredFields = Fields.getFields(obj, field -> field.getDeclaringClass() == obj.getClass());
        assertEquals(1, declaredFields.length);
        assertEquals("pDouble", declaredFields[0].getName());
    }
}
