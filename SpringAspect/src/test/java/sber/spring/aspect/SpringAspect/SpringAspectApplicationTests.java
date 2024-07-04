package sber.spring.aspect.SpringAspect;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringAspectApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void contextLoads(){
		JustClass js = applicationContext.getBean(JustClass.class);
			Assert.assertEquals("Again Yarik", js.againAndAgain("Yarik"));
	}

}
