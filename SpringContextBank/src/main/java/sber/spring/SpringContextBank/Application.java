package sber.spring.SpringContextBank;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Application {
    private String nickName = "Winding";
    private Double amount = 5684.54;
    @Autowired
    private BankClientsApp bankClientsApp;
    @Autowired
    private TransferByPhone transferByPhone;
    @Autowired
    private Database database;


    public void createTransaction(){
        System.out.println("WELCOME TO OUR APPLICATION");
        if (bankClientsApp.checkNickName(nickName)){
            System.out.println(nickName + " registered");
            transferByPhone.transact(nickName, amount);
            database.writeDown(nickName, amount);
        } else {
            System.out.println(nickName + " has nothing to do with our bank!");
        }

    }

}
