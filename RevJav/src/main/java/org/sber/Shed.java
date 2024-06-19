package org.sber;

import lombok.NonNull;
import java.lang.annotation.*;

@Daily(day = 6)
abstract class Shed {
    abstract void xExec( DataContainer dc);
    abstract void Fin( DataContainer dc);
}

class ShedMor extends Shed{

    @Override
    @Timtim(hour=5, prior = 4, descr = "Newcomer reg")
    void xExec(@NonNull DataContainer dc) {
            dc.setPasslvl(1);
            System.out.println(dc.getName() + "got lvl 1");
    }

    @Override
    @Timtim(hour=5, prior = 1, descr = "Newcomer went")
    void Fin(@NonNull DataContainer dc) {
            dc.setName("Avarage Worker #953");
            System.out.println(dc.getName() + " you are");
    }
}

class ShedEv extends Shed{

    @Timtim(hour=5, prior = 3, descr = "Try and fail")
    @Override
    void xExec(@NonNull DataContainer dc) {
        int req = 3;
        if (dc.getPasslvl() < req) {
            dc.setAge(dc.getAge() * dc.getPasslvl());
            System.out.println(dc.getName() + " Denied");
        } else {
            dc.setAge(25);
            System.out.println(dc.getAge());
        }
    }

    @Timtim(hour=3, prior = 2, descr = "Against all")
    @Override
    void Fin(@NonNull DataContainer dc) {
        dc.setAdress("Lost hwy 65");
        dc.setName("Last Call");
    }
}

@Daily(day = 5)
class ShedNig extends Shed{
    @Timtim(hour=5, prior = 3, descr = "Find way")
    @Override
    void xExec(@NonNull DataContainer dc) {
        dc.setPasslvl(0);
        dc.setAge(25);
    }

    @Timtim(hour=2, prior = 20, descr = "Hard day")
    @Override
    void Fin(@NonNull DataContainer dc) {
        dc.setName("Default");
    }
}