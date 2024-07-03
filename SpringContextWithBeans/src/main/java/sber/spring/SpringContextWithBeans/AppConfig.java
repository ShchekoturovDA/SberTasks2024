package sber.spring.SpringContextWithBeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Scope("prototype")
    @Bean
    public Parrot parrot(){
        return new Parrot();
    }

    @Bean
    public Cat cat(){
        return new Cat();
    }

    @Bean
    public Dog dog(){
        return new Dog();
    }

    @Bean
    public Human human(){
        return new Human(parrot(), parrot(), cat(), dog());
    }
}
