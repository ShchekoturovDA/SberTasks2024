package sber.spring.SpringContextWithBeans;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Data
public class Human {
    private String name = "Vasil'";
    private Parrot parrot1;
    private Parrot parrot2;
    private Cat cat;
    private Dog dog;

    @Autowired
    public Human(Parrot parrot1, Parrot parrot2, Cat cat, Dog dog){
        this.parrot1 = parrot1;
        this.parrot2 = parrot2;
        this.cat = cat;
        this.dog = dog;
    }
}
