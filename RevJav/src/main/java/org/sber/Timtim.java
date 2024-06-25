package org.sber;

import java.lang.annotation.*;

@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@Repeatable(Timtims.class)
public @interface Timtim {
    int hour();
    int prior();
    String descr();
}

@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@interface Timtims{
    Timtim[] value();
}
