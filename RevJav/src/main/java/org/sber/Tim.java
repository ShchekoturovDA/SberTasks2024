package org.sber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@Repeatable(Tims.class)
public @interface Tim {

    int hour();
    int prior();
    String descr();
}

