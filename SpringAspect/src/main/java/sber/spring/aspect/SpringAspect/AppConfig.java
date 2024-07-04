package sber.spring.aspect.SpringAspect;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("sber.spring.aspect.SpringAspect")
@EnableAspectJAutoProxy
public class AppConfig {
}
