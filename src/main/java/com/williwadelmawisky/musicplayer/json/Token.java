package com.williwadelmawisky.musicplayer.json;

/**
 * The <code>Token</code> represents a since token genereated by the <code>Tokenizer</code>.
 * <p>
 * The <code>Token</code> holds information about the string value and the type of the token in JSON context.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class Token {

    public final String Value;
    public final Type TokenType;


    /**
     * <code>Type</code> of the token in JSON context.
     * <ul>
     *     <li>TEXT: Represents a text value.</li>
     *     <li>OBJECT_START: Represents an object start character.</li>
     *     <li>OBJECT_END: Represents an object end character.</li>
     *     <li>ARRAY_START: Represents an array start character.</li>
     *     <li>ARRAY_END: Represents an array end character.</li>
     *     <li>COLON: Represents a colon.</li>
     *     <li>COMMA: Represents a comma.</li>
     * </ul>
     *
     * @author WilliwadelmaWisky
     * @since 1.0
     */
    public enum Type {
        TEXT,
        OBJECT_START,
        OBJECT_END,
        ARRAY_START,
        ARRAY_END,
        COLON,
        COMMA;


        /**
         * Automatically determine the token type from the character.
         * @param ch The character to determine the type from.
         * @return The type for a token.
         */
        static Type getType(final char ch) {
            return switch (ch) {
                case '{' -> OBJECT_START;
                case '}' -> OBJECT_END;
                case '[' -> ARRAY_START;
                case ']' -> ARRAY_END;
                case ':' -> COLON;
                case ',' -> COMMA;
                default -> TEXT;
            };
        }
    }


    /**
     * Constructs a token for a given character. The type of the token is automatically determined from based on the character.
     * @param ch The character to construct the token from.
     */
    public Token(final char ch) {
        this(String.valueOf(ch), Type.getType(ch));
    }

    /**
     * Constructs a token for a given text. The type of the token will be text.
     * @param value The text value to construct the token from.
     */
    public Token(final String value) {
        this(value, Type.TEXT);
    }

    /**
     * Constructs a token for a given text and a type.
     * @param value Value of the token.
     * @param tokenType Type of the token.
     */
    private Token(final String value, final Type tokenType) {
        Value = value;
        TokenType = tokenType;
    }
}
