package org.sber;

@Daily(day = 6)
public abstract class Schedule {
    public abstract void execute(DataContainer dataContainer);

    public abstract void fin(DataContainer dataContainer);
}
