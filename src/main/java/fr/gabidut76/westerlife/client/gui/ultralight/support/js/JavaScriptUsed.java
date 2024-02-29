package fr.gabidut76.westerlife.client.gui.ultralight.support.js;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.METHOD,})
public @interface JavaScriptUsed {
}

