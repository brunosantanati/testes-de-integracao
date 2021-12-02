package integration.tests.web.layer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WebLayerIntegrationTest {

	@LocalServerPort
	private int port;

	private RestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void someMethod() {
		String response = restTemplate.getForObject("http://localhost:"+port+"/hello"
				, String.class);
		Assertions.assertThat(response).isEqualTo("Hello");
	}


}
