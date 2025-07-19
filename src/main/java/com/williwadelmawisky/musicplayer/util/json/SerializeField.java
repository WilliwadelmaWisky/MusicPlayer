package com.williwadelmawisky.musicplayer.util.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <code>SerializeField</code> annotation marks a field as serializable.
 * Meaning that it can be accessed by the <code>Parser</code> and the <code>Writer</code>.
 * <p>
 * If the <code>SerializeField</code> attribute is given to a custom object the object must have parameterless contructor.
 *
 * @author WilliwadelmaWisky
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializeField {

}
