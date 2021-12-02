package configuration.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import configuration.basic.world.HelloService;
import configuration.basic.world.WorldService;

@SpringBootTest
class MockIntegrationTest {

	@Autowired
	private HelloService helloService;

	@MockBean
	private WorldService worldService;

	@Test
	public void someTest() {
		Mockito.when(worldService.getWorld()).thenReturn("You");
		String result = helloService.getHelloWorld();
		Assertions.assertTrue(result.equals("Hello You"));
	}


}
