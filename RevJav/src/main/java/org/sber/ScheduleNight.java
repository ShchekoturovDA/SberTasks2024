package org.sber;

import lombok.NonNull;

@Daily(day = 5)
public class ScheduleNight extends Schedule {

    @Timer(hour = 5, prior = 3, descr = "Find way")
    @Override
    public void execute(@NonNull DataContainer dataContainer) {
        dataContainer.setPassLevel(0);
        dataContainer.setAge(25);
    }

    @Timer(hour = 2, prior = 20, descr = "Hard day")
    @Override
    public void fin(@NonNull DataContainer dataContainer) {
        dataContainer.setName("Default");
    }
}