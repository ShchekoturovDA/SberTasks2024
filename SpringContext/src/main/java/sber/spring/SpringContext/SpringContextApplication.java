package sber.spring.SpringContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringContextApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringContextApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Human human = applicationContext.getBean(Human.class);
        System.out.println(human.getParrot2().getName());
    }
}
