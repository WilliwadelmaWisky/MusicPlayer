package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestWriter {

    /**
     *
     */
    @Test
    public void testWrite() {
        final Writer writer = new Writer();

        // PRIMITIVE TEST
        final Object_PrimitiveTest objectPrimitive = new Object_PrimitiveTest(true, true, (byte)5, (byte)5, (short)5, (short)5, 5, 5, (long)5, (long)5, 2.3f, 2.3f, 2.3, 2.3, 'b', 'b', "Hello");
        final String expectedPrimitive = "{\"pBoolean\":true,\"cBoolean\":true,\"pByte\":5,\"cByte\":5,\"pShort\":5,\"cShort\":5,\"pInt\":5,\"cInt\":5,\"pLong\":5,\"cLong\":5,\"pFloat\":2.3,\"cFloat\":2.3,\"pDouble\":2.3,\"cDouble\":2.3,\"pChar\":\"b\",\"cChar\":\"b\",\"cString\":\"Hello\"}";
        final String actualPrimitive = writer.write(objectPrimitive);
        assertEquals(expectedPrimitive, actualPrimitive);

        // ARRAY TEST
        final Object_ArrayTest objectArray = new Object_ArrayTest(new int[] { 1, 2 }, new Integer[] { 1, 2 }, new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }, new int[][] { new int[] { 1, 2 }, new int[] { 3, 4 }}, new Integer[][] { new Integer[] { 1, 2 }, new Integer[] { 3, 4 }}, new Object_ArrayTest.ObjectClass[][] { new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }, new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }});
        final String expectedArray = "{\"pIntArray\":[1,2],\"cIntArray\":[1,2],\"cObjectArray\":[{\"pInt\":2,\"cInt\":2}],\"pIntArray2D\":[[1,2],[3,4]],\"cIntArray2D\":[[1,2],[3,4]],\"cObjectArray2D\":[[{\"pInt\":2,\"cInt\":2}],[{\"pInt\":2,\"cInt\":2}]]}";
        final String actualArray = writer.write(objectArray);
        assertEquals(expectedArray, actualArray);

        // INHERITANCE TEST
        final Object_InheritanceTest objectInheritance = new Object_InheritanceTest(new Object_InheritanceTest.SuperClass(2), new Object_InheritanceTest.InheritedClass(2, 2.3));
        final String expectedInheritance = "{\"cSuperClass\":{\"pInt\":2},\"cInheritedClass\":{\"pDouble\":2.3,\"pInt\":2}}";
        final String actualInheritance = writer.write(objectInheritance);
        assertEquals(expectedInheritance, actualInheritance);
    }
}
