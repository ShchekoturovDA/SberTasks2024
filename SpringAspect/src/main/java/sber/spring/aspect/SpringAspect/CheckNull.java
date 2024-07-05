package sber.spring.aspect.SpringAspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect
public class CheckNull {

    @Before("@annotation(NotEmpty)")
    public void checking(JoinPoint joinPoint){
        Object [] arguments = joinPoint.getArgs();
        for (Object argument : arguments) {
            if (argument == null){
                throw new NullPointerException("Object mustn't have null value or be empty!");
            } else if (argument.getClass() == String.class){
                if( ((String) argument).isEmpty()){
                    throw new NullPointerException("Object mustn't have null value or be empty!");
                }
            } else if (argument instanceof Collection){
                if( ((Collection) argument).isEmpty()){
                    throw new NullPointerException("Object mustn't have null value or be empty!");
                }
            }
        }
    }
}
