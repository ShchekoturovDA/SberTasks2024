package sber.spring.SpringContextBank;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BankClientsApp {

    private String nickName = "Winding";

    public boolean checkNickName(String sent) {
        return sent == nickName;
    }
}
