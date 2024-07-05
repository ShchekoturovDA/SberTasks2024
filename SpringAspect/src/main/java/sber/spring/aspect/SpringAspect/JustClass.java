package sber.spring.aspect.SpringAspect;

import org.springframework.stereotype.Component;

@Component
public class JustClass {

    @NotEmpty
    public String againAndAgain(Object post){
        System.out.println("Again "+ post);
        return "Again "+ post;
    }

    @NotEmpty
    public String shizofrenia(Integer pulmonolog, String okulist, Boolean lore){
        System.out.println("Nevermore!");
        return pulmonolog + okulist + lore;
    }
}
