package com.williwadelmawisky.musicplayer.util.json;

/**
 *
 */
public class Object_PrimitiveTest {

    @SerializeField private boolean pBoolean;
    @SerializeField private Boolean cBoolean;
    @SerializeField private byte pByte;
    @SerializeField private Byte cByte;
    @SerializeField private short pShort;
    @SerializeField private Short cShort;
    @SerializeField private int pInt;
    @SerializeField private Integer cInt;
    @SerializeField private long pLong;
    @SerializeField private Long cLong;
    @SerializeField private float pFloat;
    @SerializeField private Float cFloat;
    @SerializeField private double pDouble;
    @SerializeField private Double cDouble;
    @SerializeField private char pChar;
    @SerializeField private Character cChar;
    @SerializeField private String cString;


    /**
     *
     */
    public Object_PrimitiveTest() {}

    /**
     * @param pBoolean
     * @param cBoolean
     * @param pByte
     * @param cByte
     * @param pShort
     * @param cShort
     * @param pInt
     * @param cInt
     * @param pLong
     * @param cLong
     * @param pFloat
     * @param cFloat
     * @param pDouble
     * @param cDouble
     * @param pChar
     * @param cChar
     * @param cString
     */
    public Object_PrimitiveTest(final boolean pBoolean, final Boolean cBoolean,
                                final byte pByte, final Byte cByte,
                                final short pShort, final Short cShort,
                                final int pInt, final Integer cInt,
                                final long pLong, final Long cLong,
                                final float pFloat, final Float cFloat,
                                final double pDouble, final Double cDouble,
                                final char pChar, final Character cChar,
                                final String cString) {
        this.pBoolean = pBoolean;
        this.cBoolean = cBoolean;
        this.pByte = pByte;
        this.cByte = cByte;
        this.pShort = pShort;
        this.cShort = cShort;
        this.pInt = pInt;
        this.cInt = cInt;
        this.pLong = pLong;
        this.cLong = cLong;
        this.pFloat = pFloat;
        this.cFloat = cFloat;
        this.pDouble = pDouble;
        this.cDouble = cDouble;
        this.pChar = pChar;
        this.cChar = cChar;
        this.cString = cString;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof Object_PrimitiveTest o))
            return false;

        return pBoolean == o.pBoolean && cBoolean.equals(o.cBoolean)
                && pByte == o.pByte && cByte.equals(o.cByte)
                && pShort == o.pShort && cShort.equals(o.cShort)
                && pInt == o.pInt && cInt.equals(o.cInt)
                && pLong == o.pLong && cLong.equals(o.cLong)
                && pFloat == o.pFloat && cFloat.equals(o.cFloat)
                && pDouble == o.pDouble && cDouble.equals(o.cDouble)
                && pChar == o.pChar && cChar.equals(o.cChar)
                && cString.equals(o.cString);
    }
}
