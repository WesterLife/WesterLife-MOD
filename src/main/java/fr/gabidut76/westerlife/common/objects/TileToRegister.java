package fr.gabidut76.westerlife.common.objects;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TileToRegister {
    String location();
}
