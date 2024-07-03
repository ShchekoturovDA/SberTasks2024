package sber.spring.SpringContextBank;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@TestConfiguration
class SpringContextBankApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void componentLoad() {
		Application app = context.getBean(Application.class);
		Application app1 = new Application();
		Assert.assertEquals(app1.getNickName(), app.getNickName());
		Assert.assertEquals(app1.getAmount(), app.getAmount());
		app.createTransaction();
		app.setNickName("Nikolay");
		app.createTransaction();
	}

}
