package configuration.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import configuration.basic.stubs.WorldServiceImplStub;
import configuration.basic.world.HelloService;

@SpringBootTest(classes = {WorldServiceImplStub.class})
class StubIntegrationTest {

	@Autowired
	private HelloService helloService;

	@Test
	public void otherTest() {
		String result = helloService.getHelloWorld();
		Assertions.assertTrue(result.equals("Hello Stub"));
	}

}
