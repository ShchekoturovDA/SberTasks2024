package sber.spring.aspect.SpringAspect;

import org.springframework.stereotype.Component;

@Component
public class JustClass {

    @NotEmpty
    public String againAndAgain(String post){
        System.out.println("Again "+ post);
        return "Again "+ post;
    }

    @NotEmpty
    public void shizofrenia(Integer pulmonolog, String okulist, Boolean lore){
        System.out.println("Nevermore!");
    }
}
