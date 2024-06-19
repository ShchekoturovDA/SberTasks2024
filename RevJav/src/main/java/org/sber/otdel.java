package org.sber;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class otdel {
    public void exec(int date,int hour, DataContainer dc) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Class> subs = new ArrayList<>();
        subs.add(ShedMor.class);
        subs.add(ShedEv.class);
        subs.add(ShedNig.class);
        subs = subs.stream().filter(x -> ((Daily)x.getAnnotation(Daily.class)).day() == date).collect(Collectors.toList());
        for(Class sub : subs){
            Object s = sub.newInstance();
            List<Method> ms = List.of(sub.getDeclaredMethods());
//            System.out.println(ms);
            ms = ms.stream().filter(x -> (x.getAnnotation(Timtim.class)).hour() == hour).sorted(Comparator.comparingInt(x -> (x.getAnnotation(Timtim.class)).prior())).toList();
            for(Method m: ms){
                m.invoke(s, dc);
                System.out.println(dc.toString());
            }
        }
    }

}
