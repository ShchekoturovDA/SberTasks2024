package org.sber;

@Daily(day = 6)
abstract class schedule {
    abstract void execute(DataContainer dataContainer);
    abstract void fin(DataContainer dataContainer);
}
