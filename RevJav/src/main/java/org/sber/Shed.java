package org.sber;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Daily(day = 6)
abstract class Shed {
    abstract void xExec( DataContainer dc);
    abstract void Fin( DataContainer dc);
}

class ShedMor extends Shed{

    @Override
    @Timtim(hour=8, prior = 4, descr = "Newcomer reg")
    void xExec( DataContainer dc) {
            dc.setPasslvl(1);
            System.out.println(dc.getName() + "got lvl 1");
    }

    @Override
    @Timtim(hour=10, prior = 1, descr = "Newcomer went")
    void Fin( DataContainer dc) {
            dc.setName("Avarage Worker #953");
            System.out.println(dc.getName() + " you are");
    }
}

class ShedEv extends Shed{

    @Timtim(hour=16, prior = 3, descr = "Try and fail")
    @Override
    void xExec( DataContainer dc) {
        int req = 3;
        if (dc.getPasslvl() < req) {
            dc.setAge(dc.getAge() * dc.getPasslvl());
            System.out.println(dc.getName() + " Denied");
        } else {
            dc.setAge(25);
            System.out.println(dc.getAge());
        }
    }

    @Timtim(hour=18, prior = 2, descr = "Against all")
    @Override
    void Fin( DataContainer dc) {
        dc.setAdress("Lost hwy 65");
        dc.setName("Last Call");
    }
}

@Daily(day = 5)
class ShedNig extends Shed{
    @Timtim(hour=19, prior = 3, descr = "Find way")
    @Override
    void xExec(DataContainer dc) {
        dc.setPasslvl(0);
        dc.setAge(25);
    }

    @Timtim(hour=22, prior = 2, descr = "Hard day")
    @Override
    void Fin(DataContainer dc) {
        dc = null;
    }
}