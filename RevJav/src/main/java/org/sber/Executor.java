package org.sber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Executor {

    public void chooseAction(int date, int hour, DataContainer dataContainer) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Class> subClasses = new ArrayList<>();
        subClasses.add(ScheduleMorning.class);
        subClasses.add(ScheduleEvening.class);
        subClasses.add(ScheduleNight.class);
        subClasses = subClasses.stream().filter(x -> ((Daily) x.getAnnotation(Daily.class)).day() == date).collect(Collectors.toList());
        for (Class subClass : subClasses) {
            Object classExemplar = subClass.newInstance();
            List<Method> methods = List.of(subClass.getDeclaredMethods());
            methods = methods.stream()
                    .filter(x -> (x.getAnnotation(Timer.class)).hour() == hour)
                    .sorted(Comparator.comparingInt(x -> (x.getAnnotation(Timer.class)).prior()))
                    .toList();
            for (Method method : methods) {
                method.invoke(classExemplar, dataContainer);
                System.out.println(dataContainer.toString());
            }
        }
    }
}
