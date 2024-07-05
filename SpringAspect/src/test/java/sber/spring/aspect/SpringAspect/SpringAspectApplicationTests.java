package sber.spring.aspect.SpringAspect;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;

@SpringBootTest
class SpringAspectApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        JustClass js = applicationContext.getBean(JustClass.class);
        Assert.assertEquals("Again Yarik", js.againAndAgain("Yarik"));
        Assert.assertEquals("5klosefalse", js.atLast(5, "klose", false));
        try {
            js.atLast(5, "klose", null);
        } catch (Exception e) {
            Assert.assertEquals("Object mustn't have null value or be empty!", e.getMessage());
        }
        try {
            js.againAndAgain(new LinkedList());
        } catch (Exception e) {
            Assert.assertEquals("Object mustn't have null value or be empty!", e.getMessage());
        }
        try {
            js.atLast(5, new String(), true);
        } catch (Exception e) {
            Assert.assertEquals("Object mustn't have null value or be empty!", e.getMessage());
        }
    }
}
