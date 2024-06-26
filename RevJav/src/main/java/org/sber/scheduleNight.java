package org.sber;

import lombok.NonNull;

@Daily(day = 5)
class scheduleNight extends schedule {

    @Tim(hour = 5, prior = 3, descr = "Find way")
    @Override
    void execute(@NonNull DataContainer dataContainer) {
        dataContainer.setPassLevel(0);
        dataContainer.setAge(25);
    }

    @Tim(hour = 2, prior = 20, descr = "Hard day")
    @Override
    void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setName("Default");
    }
}