package sber.spring.SpringContextBank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringContextBankApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(SpringContextBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Application app = context.getBean(Application.class);
        app.createTransaction();
    }
}