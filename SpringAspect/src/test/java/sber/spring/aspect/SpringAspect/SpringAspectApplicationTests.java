package sber.spring.aspect.SpringAspect;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpringAspectApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        JustClass js = applicationContext.getBean(JustClass.class);
        Assert.assertEquals("Again Yarik", js.againAndAgain("Yarik"));
        Assert.assertEquals("5klosefalse", js.atLast(5, "klose", false));
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> js.againAndAgain(new LinkedList()));
        Assert.assertEquals("Object mustn't have null value or be empty!", exception1.getMessage());
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> js.atLast(5, new String(), true));
        Assert.assertEquals("Object mustn't have null value or be empty!", exception2.getMessage());
    }
}