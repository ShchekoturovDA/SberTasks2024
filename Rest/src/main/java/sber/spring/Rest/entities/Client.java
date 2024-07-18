package sber.spring.Rest.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private long id;
    private String name;
    private String login;
    private String password;
    private String email;
    private int binId;
}
