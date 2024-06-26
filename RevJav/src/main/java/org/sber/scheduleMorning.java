package org.sber;

import lombok.NonNull;

class scheduleMorning extends schedule {

    @Override
    @Tim(hour=5, prior = 4, descr = "Newcomer reg")
    void execute(@NonNull DataContainer dataContainer) {
        dataContainer.setPassLevel(1);
        System.out.println(dataContainer.getName() + "got lvl 1");
    }

    @Override
    @Tim(hour=5, prior = 1, descr = "Newcomer went")
    void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setName("Average Worker #953");
        System.out.println(dataContainer.getName() + " you are");
    }
}
