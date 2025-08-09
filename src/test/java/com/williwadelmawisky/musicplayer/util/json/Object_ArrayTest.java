package com.williwadelmawisky.musicplayer.util.json;

/**
 *
 */
public class Object_ArrayTest {

    @SerializeField private int[] pIntArray;
    @SerializeField private Integer[] cIntArray;
    @SerializeField private ObjectClass[] cObjectArray;
    @SerializeField private int[][] pIntArray2D;
    @SerializeField private Integer[][] cIntArray2D;
    @SerializeField private ObjectClass[][] cObjectArray2D;


    /**
     *
     */
    public Object_ArrayTest() {}

    /**
     * @param pIntArray
     * @param cIntArray
     * @param cObjectArray
     * @param pIntArray2D
     * @param cIntArray2D
     * @param cObjectArray2D
     */
    public Object_ArrayTest(final int[] pIntArray, final Integer[] cIntArray, final ObjectClass[] cObjectArray,
                                final int[][] pIntArray2D, final Integer[][] cIntArray2D, final ObjectClass[][] cObjectArray2D) {
        this.pIntArray = pIntArray;
        this.cIntArray = cIntArray;
        this.cObjectArray = cObjectArray;
        this.pIntArray2D = pIntArray2D;
        this.cIntArray2D = cIntArray2D;
        this.cObjectArray2D = cObjectArray2D;
    }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Object_ArrayTest o))
            return false;

        if (pIntArray.length != o.pIntArray.length) return false;
        for (int i = 0; i < pIntArray.length; i++) {
            if (pIntArray[i] != o.pIntArray[i])
                return false;
        }

        if (cIntArray.length != o.cIntArray.length) return false;
        for (int i = 0; i < cIntArray.length; i++) {
            if (!cIntArray[i].equals(o.cIntArray[i]))
                return false;
        }

        if (cObjectArray.length != o.cObjectArray.length) return false;
        for (int i = 0; i < cObjectArray.length; i++) {
            if (!cObjectArray[i].equals(o.cObjectArray[i]))
                return false;
        }

        if (pIntArray2D.length != o.pIntArray2D.length) return false;
        for (int i = 0; i < pIntArray2D.length; i++) {
            if (pIntArray2D[i].length != o.pIntArray2D[i].length) return false;
            for (int j = 0; j < pIntArray2D[i].length; j++) {
                if (pIntArray2D[i][j] != o.pIntArray2D[i][j])
                    return false;
            }
        }

        if (cIntArray2D.length != o.cIntArray2D.length) return false;
        for (int i = 0; i < cIntArray2D.length; i++) {
            if (cIntArray2D[i].length != o.cIntArray2D[i].length) return false;
            for (int j = 0; j < cIntArray2D[i].length; j++) {
                if (!cIntArray2D[i][j].equals(o.cIntArray2D[i][j]))
                    return false;
            }
        }

        if (cObjectArray2D.length != o.cObjectArray2D.length) return false;
        for (int i = 0; i < cObjectArray2D.length; i++) {
            if (cObjectArray2D[i].length != o.cObjectArray2D[i].length) return false;
            for (int j = 0; j < cObjectArray2D[i].length; j++) {
                if (!cObjectArray2D[i][j].equals(o.cObjectArray2D[i][j]))
                    return false;
            }
        }

        return true;
    }


    /**
     *
     */
    public static class ObjectClass {

        @SerializeField private int pInt;
        @SerializeField private Integer cInt;


        /**
         *
         */
        public ObjectClass() {}

        /**
         * @param pInt
         * @param cInt
         */
        public ObjectClass(final int pInt, final Integer cInt) {
            this.pInt = pInt;
            this.cInt = cInt;
        }


        /**
         * @param obj
         * @return
         */
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof ObjectClass o))
                return false;

            return pInt == o.pInt && cInt.equals(o.cInt);
        }
    }
}
