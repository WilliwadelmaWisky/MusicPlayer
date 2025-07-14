package com.williwadelmawisky.musicplayer.json;

/**
 * The <code>PrettyModifier</code> provides a method to format a JSON string to be easier to read by adding whitespaces and linebreaks.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
public class PrettyModifier {

    /**
     * Constructs a pretty modifier .
     */
    public PrettyModifier() {}


    /**
     * Modifies a JSON string to an easier to read format.
     * @param s Initial JSON string.
     * @return formatted JSON string.
     */
    public String modify(final String s) {
        final StringBuilder stringBuilder = new StringBuilder(s);
        modify(stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Modifies a JSON string to an easier to read format
     * @param stringBuilder A string builder containing the initial JSON string. The changes are made to the string builder.
     */
    public void modify(final StringBuilder stringBuilder) {
        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == '\"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (inQuotes)
                continue;

            switch (stringBuilder.charAt(i)) {
                case '{', '[' -> {
                    stringBuilder.insert(i + 1, "\r\n");
                    i += 2;

                    indentLevel++;
                    stringBuilder.insert(i + 1, "\t".repeat(indentLevel));
                    i += indentLevel;
                }
                case '}', ']' -> {
                    stringBuilder.insert(i, "\r\n");
                    i += 2;

                    indentLevel--;
                    stringBuilder.insert(i, "\t".repeat(indentLevel));
                    i += indentLevel;
                }
                case ':' -> {
                    stringBuilder.insert(i + 1, ' ');
                    i += 1;
                }
                case ',' -> {
                    stringBuilder.insert(i + 1, "\r\n");
                    i += 2;

                    stringBuilder.insert(i + 1, "\t".repeat(indentLevel));
                    i += indentLevel;
                }
            }
        }
    }
}
