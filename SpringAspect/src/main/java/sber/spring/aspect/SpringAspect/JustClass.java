package sber.spring.aspect.SpringAspect;

import org.springframework.stereotype.Component;

@Component
public class JustClass {

    @NotEmpty
    public String againAndAgain(Object post) {
        System.out.println("Again " + post);
        return "Again " + post;
    }

    @NotEmpty
    public String atLast(Integer value, String name, Boolean isLast) {
        System.out.println("Nevermore!");
        return value + name + isLast;
    }
}
