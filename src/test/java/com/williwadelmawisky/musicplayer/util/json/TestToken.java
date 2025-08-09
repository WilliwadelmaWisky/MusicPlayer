package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestToken {

    /**
     *
     */
    @Test
    public void testConstructor() {
        final Token token = new Token("Hello");
        assertEquals("Hello", token.Value);
    }
}
