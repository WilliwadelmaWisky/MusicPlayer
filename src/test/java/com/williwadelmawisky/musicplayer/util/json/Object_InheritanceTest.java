package com.williwadelmawisky.musicplayer.util.json;

/**
 *
 */
public class Object_InheritanceTest {

    @SerializeField private SuperClass cSuperClass;
    @SerializeField private InheritedClass cInheritedClass;


    /**
     *
     */
    public Object_InheritanceTest() {}

    /**
     * @param cSuperClass
     * @param cInheritedClass
     */
    public Object_InheritanceTest(final SuperClass cSuperClass, final InheritedClass cInheritedClass) {
        this.cSuperClass = cSuperClass;
        this.cInheritedClass = cInheritedClass;
    }


    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Object_InheritanceTest o))
            return false;

        return cSuperClass.equals(o.cSuperClass) && cInheritedClass.equals(o.cInheritedClass);
    }

    /**
     *
     */
    public static class SuperClass {

        @SerializeField protected int pInt;

        /**
         *
         */
        public SuperClass() {}

        /**
         * @param pInt
         */
        public SuperClass(final int pInt) {
            this.pInt = pInt;
        }


        /**
         * @param obj
         * @return
         */
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof SuperClass o))
                return false;

            return pInt == o.pInt;
        }
    }

    /**
     *
     */
    public static class InheritedClass extends SuperClass {

        @SerializeField private double pDouble;


        /**
         *
         */
        public InheritedClass() { super(); }

        /**
         * @param pInt
         * @param pDouble
         */
        public InheritedClass(final int pInt, final double pDouble) {
            super(pInt);
            this.pDouble = pDouble;
        }


        /**
         * @param obj
         * @return
         */
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof InheritedClass o))
                return false;

            return super.equals(o) && pDouble == o.pDouble;
        }
    }
}
