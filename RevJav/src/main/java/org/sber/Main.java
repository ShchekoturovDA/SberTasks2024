package org.sber;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        otdel test = new otdel();
        DataContainer dc = new DataContainer("John", 55, "Fedotovo & Babushkina", 1.5, 3);

        test.exec(6, 5, dc);
    }
}