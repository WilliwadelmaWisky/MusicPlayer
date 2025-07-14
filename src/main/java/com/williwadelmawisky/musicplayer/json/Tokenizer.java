package com.williwadelmawisky.musicplayer.json;

/**
 * The <code>Tokenizer</code> provides a method to read a string token by token.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class Tokenizer {

    private static final char[] SPECIAL_CHARACTERS = new char[] { '{', '}', '[', ']', ':', ',' };

    public final String TargetString;
    private int _index;


    /**
     * Constructs a tokenizer with a given JSON string. The start index will be set to zero.
     * @param s A JSON string to tokenize.
     */
    public Tokenizer(final String s) {
        this(s, 0);
    }

    /**
     * Constructs a tokenizer with a given JSON string and a start index.
     * @param s A JSON string to tokenize.
     * @param startIndex The start index of the tokenization process. The minimum value is zero.
     */
    public Tokenizer(final String s, int startIndex) {
        TargetString = s;
        _index = Math.max(startIndex, 0);
    }


    /**
     * Get the current read index of the tokenizer.
     * @return The current index.
     */
    public int currentIndex() { return _index; }


    /**
     * Reads a next token. Returns null if there are no characters left to read.
     * @return The next token.
     * @throws InvalidSyntaxException Thrown if a syntax error is found (<i>missing a closing quote etc.</i>).
     */
    public Token next() throws InvalidSyntaxException {
        if (_index >= TargetString.length())
            return null;

        while (isIgnoredCharacter(TargetString.charAt(_index))) {
            _index++;
            if (_index >=  TargetString.length())
                return null;
        }

        for (char ch : SPECIAL_CHARACTERS) {
            if (TargetString.charAt(_index) != ch)
                continue;

            _index++;
            return new Token(ch);
        }

        if (TargetString.charAt(_index) == '\"') {
            _index++;
            final int startIndex = _index;
            while (TargetString.charAt(_index) != '\"') {
                _index++;
                if (_index >=  TargetString.length())
                    throw new InvalidSyntaxException("no closing quote found");
            }

            _index++;
            final String value = TargetString.substring(startIndex, _index);
            return new Token(value);
        }

        if (isTextCharacter(TargetString.charAt(_index))) {
            final int startIndex = _index;
            _index++;
            while (isTextCharacter(TargetString.charAt(_index))) {
                _index++;
                if (_index >=  TargetString.length())
                    break;
            }

            final String value = TargetString.substring(startIndex, _index);
            return new Token(value);
        }

        throw new InvalidSyntaxException(String.format("illegal character found (%s = %d)", TargetString.charAt(_index), Character.codePointAt(TargetString, _index)));
    }


    /**
     * Checks if the given character can be ignored.
     * @param ch The character to check.
     * @return True if the character can be ignored.
     */
    private boolean isIgnoredCharacter(final char ch) {
        for (char specialCharacter : SPECIAL_CHARACTERS) {
            if (specialCharacter == ch)
                return false;
        }

        return !Character.isLetterOrDigit(ch) && ch != '\"';
    }

    /**
     * Checks if the given character is a text character (<i>letter, number, dot, dash or underscore</i>).
     * @param ch The character to check.
     * @return True if the character is text character.
     */
    public boolean isTextCharacter(final char ch) {
        return Character.isLetterOrDigit(ch)
                || ch == '.'
                || ch == '_'
                || ch == '-';
    }
}
