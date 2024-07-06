package sber.spring.SpringContextWithBeans;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringContextWithBeansApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void checkBeans() {
        Human human = applicationContext.getBean(Human.class);
        Human human1 = new Human(new Parrot(), new Parrot(), new Cat(), new Dog());
        Assert.assertEquals(human1, human);
    }

    @Test
    public void checkPrototype() {
        Human human = applicationContext.getBean(Human.class);
        human.getParrot2().setName("Lexiy");
        Assert.assertEquals(new Parrot(), human.getParrot1());
        Parrot testParrot = new Parrot();
        testParrot.setName("Lexiy");
        Assert.assertEquals(testParrot, human.getParrot2());
    }
}
