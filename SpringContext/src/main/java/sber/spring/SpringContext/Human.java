package sber.spring.SpringContext;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Human {

    private String name = "Vasil'";
    private Parrot parrot1;
    private Parrot parrot2;
    private Cat cat;
    private Dog dog;

    public Human(Parrot parrot1, Parrot parrot2, Cat cat, Dog dog) {
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.cat = cat;
        this.dog = dog;
    }
}
