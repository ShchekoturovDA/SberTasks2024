package org.sber;

import lombok.NonNull;

class scheduleEvening extends schedule {

    @Tim(hour=5, prior = 3, descr = "Try and fail")
    @Override
    void execute(@NonNull DataContainer dataContainer) {
        if (dataContainer.getPassLevel() < 3) {
            dataContainer.setAge(dataContainer.getAge() * dataContainer.getPassLevel());
            System.out.println(dataContainer.getName() + " Denied");
        } else {
            dataContainer.setAge(25);
            System.out.println(dataContainer.getAge());
        }
    }

    @Tim(hour=3, prior = 2, descr = "Against all")
    @Override
    void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setAddress("Lost hwy 65");
        dataContainer.setName("Last Call");
    }
}
