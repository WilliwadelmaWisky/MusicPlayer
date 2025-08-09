package com.williwadelmawisky.musicplayer.util.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TestPrettyModifier {

    /**
     *
     */
    @Test
    public void testModify() {
        final PrettyModifier prettyModifier = new PrettyModifier();
        final String value = prettyModifier.modify("{FirstName:Hello,LastName:World,Friends:[Foo,Bar],Age:50,User:{Username:hello_world}}");
        final String expectedValue = """
                {\r
                \tFirstName: Hello,\r
                \tLastName: World,\r
                \tFriends: [\r
                \t\tFoo,\r
                \t\tBar\r
                \t],\r
                \tAge: 50,\r
                \tUser: {\r
                \t\tUsername: hello_world\r
                \t}\r
                }""";

        assertEquals(expectedValue, value);
    }
}
