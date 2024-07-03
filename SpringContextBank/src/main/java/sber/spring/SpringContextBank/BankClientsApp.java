package sber.spring.SpringContextBank;

import org.springframework.stereotype.Component;

@Component
public class BankClientsApp {
    private String nickName = "Winding";

    public boolean checkNickName(String sent){
        return sent == nickName;
    }
}
