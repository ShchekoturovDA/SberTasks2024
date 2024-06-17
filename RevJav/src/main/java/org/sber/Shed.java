package org.sber;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

@Daily(day = 6)
public class Shed {
}

class ShedMor extends Shed{
    public void newCom(@NotNull DataContainer rook, String name){
        rook.setName(name);
        rook.setPasslvl(1);
        System.out.println(rook.getName());
    }
}

class ShedEv extends Shed{
    public void Check(@NotNull DataContainer work){
        int req = 3;
        if (work.getPasslvl() < req) {
            System.out.println(work.getName() + " Denied");
        } else {
            System.out.println(work.getAge());
        }
    }
}

@Daily(day = 5)
class ShedNig extends Shed{
    public void Block(@NotNull DataContainer lost){
        System.out.println("Ride the " + lost.getAdress());
    }
}