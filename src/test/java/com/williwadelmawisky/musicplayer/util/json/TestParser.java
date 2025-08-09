package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestParser {

    /**
     *
     */
    @Test
    public void testParse() {
        final Parser parser = new Parser();

        // PRIMITIVE TEST
        final String sPrimitive = "{ \"pBoolean\": true, \"cBoolean\": true, \"pByte\": 5, \"cByte\": 5, \"pShort\": 5, \"cShort\": 5, \"pInt\": 5, \"cInt\": 5, \"pLong\": 5, \"cLong\": 5, \"pFloat\": 2.3, \"cFloat\": 2.3, \"pDouble\": 2.3, \"cDouble\": 2.3, \"pChar\": b, \"cChar\": b, \"cString\": \"Hello\" }";
        final Object_PrimitiveTest expectedPrimitive = new Object_PrimitiveTest(true, true, (byte)5, (byte)5, (short)5, (short)5, 5, 5, (long)5, (long)5, 2.3f, 2.3f, 2.3, 2.3, 'b', 'b', "Hello");
        final Object_PrimitiveTest actualPrimitive = new Object_PrimitiveTest();
        parser.parse(sPrimitive, actualPrimitive);
        assertEquals(expectedPrimitive, actualPrimitive);

        // ARRAY TEST
        final String sArray = "{ \"pIntArray\": [ 1, 2 ], \"cIntArray\": [ 1, 2 ], \"cObjectArray\": [{ \"pInt\": 2, \"cInt\": 2 }], \"pIntArray2D\": [[ 1, 2 ], [ 3, 4 ]], \"cIntArray2D\": [[ 1, 2 ], [ 3, 4 ]], \"cObjectArray2D\": [[{ \"pInt\": 2, \"cInt\": 2 }], [{ \"pInt\": 2, \"cInt\": 2 }]]}";
        final Object_ArrayTest expectedArray = new Object_ArrayTest(new int[] { 1, 2 }, new Integer[] { 1, 2 }, new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }, new int[][] { new int[] { 1, 2 }, new int[] { 3, 4 }}, new Integer[][] { new Integer[] { 1, 2 }, new Integer[] { 3, 4 }}, new Object_ArrayTest.ObjectClass[][] { new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }, new Object_ArrayTest.ObjectClass[] { new Object_ArrayTest.ObjectClass(2, 2) }});
        final Object_ArrayTest actualArray = new Object_ArrayTest();
        parser.parse(sArray, actualArray);
        assertEquals(expectedArray, actualArray);

        // INHERITANCE TEST
        final String sInheritance = "{ \"cSuperClass\": { \"pInt\": 2 }, \"cInheritedClass\": { \"pInt\": 2, \"pDouble\": 2.3 }}";
        final Object_InheritanceTest expectedInheritance = new Object_InheritanceTest(new Object_InheritanceTest.SuperClass(2), new Object_InheritanceTest.InheritedClass(2, 2.3));
        final Object_InheritanceTest actualInheritance = new Object_InheritanceTest();
        parser.parse(sInheritance, actualInheritance);
        assertEquals(expectedInheritance, actualInheritance);
    }
}
