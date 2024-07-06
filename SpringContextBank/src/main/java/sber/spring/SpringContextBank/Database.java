package sber.spring.SpringContextBank;

import org.springframework.stereotype.Component;

@Component
public class Database {

    public void writeDown(String nickName, Double amount) {
        System.out.println("Information about this transaction saved to database");
    }
}
