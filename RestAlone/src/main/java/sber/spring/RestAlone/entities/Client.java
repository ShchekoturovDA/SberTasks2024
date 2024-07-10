package sber.spring.RestAlone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private long id;
    private String name;
    private  String login;
    private  String password;
    private  String email;
    private Bin bin;
}
