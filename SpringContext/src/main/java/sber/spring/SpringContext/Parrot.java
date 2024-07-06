package sber.spring.SpringContext;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class Parrot {
    private String name = "Georgiy";
}
