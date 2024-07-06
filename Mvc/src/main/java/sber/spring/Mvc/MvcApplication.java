package sber.spring.Mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class MvcApplication{

    public static void main(String[] args) {
        SpringApplication.run(MvcApplication.class, args);
    }

}
