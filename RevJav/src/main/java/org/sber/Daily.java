package org.sber;

import java.lang.annotation.*;

@Target(value=ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
@Inherited
public @interface Daily {
    int day();
}
