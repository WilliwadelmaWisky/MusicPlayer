package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 */
public class TestTokenizer {

    /**
     *
     */
    @Test
    public void testNext() {
        final Tokenizer tokenizer = new Tokenizer("{ \"Name\": \"Yes\", \"Age\": 35, \"Friends\": [ \"Maybe\", \"No\" ]}");
        assertEquals("{", tokenizer.next().Value);
        assertEquals("Name", tokenizer.next().Value);
        assertEquals(":", tokenizer.next().Value);
        assertEquals("Yes", tokenizer.next().Value);
        assertEquals(",", tokenizer.next().Value);
        assertEquals("Age", tokenizer.next().Value);
        assertEquals(":", tokenizer.next().Value);
        assertEquals("35", tokenizer.next().Value);
        assertEquals(",", tokenizer.next().Value);
        assertEquals("Friends", tokenizer.next().Value);
        assertEquals(":", tokenizer.next().Value);
        assertEquals("[", tokenizer.next().Value);
        assertEquals("Maybe", tokenizer.next().Value);
        assertEquals(",", tokenizer.next().Value);
        assertEquals("No", tokenizer.next().Value);
        assertEquals("]", tokenizer.next().Value);
        assertEquals("}", tokenizer.next().Value);
        assertNull(tokenizer.next());
    }
}
