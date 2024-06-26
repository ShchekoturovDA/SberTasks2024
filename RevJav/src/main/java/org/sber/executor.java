package org.sber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class executor {

    public void chooseAction(int date, int hour, DataContainer dataContainer) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Class> subClasses = new ArrayList<>();
        subClasses.add(scheduleMorning.class);
        subClasses.add(scheduleEvening.class);
        subClasses.add(scheduleNight.class);
        subClasses = subClasses.stream().filter(x -> ((Daily)x.getAnnotation(Daily.class)).day() == date).collect(Collectors.toList());
        for(Class subClass : subClasses){
            Object classExemplar = subClass.newInstance();
            List<Method> methods = List.of(subClass.getDeclaredMethods());
            methods = methods.stream().filter(x -> (x.getAnnotation(Tim.class)).hour() == hour).sorted(Comparator.comparingInt(x -> (x.getAnnotation(Tim.class)).prior())).toList();
            for(Method method : methods){
                method.invoke(classExemplar, dataContainer);
                System.out.println(dataContainer.toString());
            }
        }
    }

}
