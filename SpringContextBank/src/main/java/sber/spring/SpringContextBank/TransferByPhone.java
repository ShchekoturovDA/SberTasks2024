package sber.spring.SpringContextBank;

import org.springframework.stereotype.Component;

@Component
public class TransferByPhone {

    public void transact(String nickName, Double amount){
        System.out.println("Transaction of " + amount + " RUB on " + nickName + "'s account");
    }
}
