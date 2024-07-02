package org.sber;

import lombok.NonNull;

public class ScheduleMorning extends Schedule {

    @Override
    @Timer(hour = 5, prior = 4, descr = "Newcomer reg")
    public void execute(@NonNull DataContainer dataContainer) {
        dataContainer.setPassLevel(1);
        System.out.println(dataContainer.getName() + "got lvl 1");
    }

    @Override
    @Timer(hour = 5, prior = 1, descr = "Newcomer went")
    public void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setName("Average Worker #953");
        System.out.println(dataContainer.getName() + " you are");
    }
}
