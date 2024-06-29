package org.sber;

import lombok.NonNull;

public class ScheduleEvening extends Schedule {

    @Timer(hour = 5, prior = 3, descr = "Try and fail")
    @Override
    public void execute(@NonNull DataContainer dataContainer) {
        if (dataContainer.getPassLevel() < 3) {
            dataContainer.setAge(dataContainer.getAge() * dataContainer.getPassLevel());
            System.out.println(dataContainer.getName() + " Denied");
        } else {
            dataContainer.setAge(25);
            System.out.println(dataContainer.getAge());
        }
    }

    @Timer(hour = 3, prior = 2, descr = "Against all")
    @Override
    public void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setAddress("Lost hwy 65");
        dataContainer.setName("Last Call");
    }
}
