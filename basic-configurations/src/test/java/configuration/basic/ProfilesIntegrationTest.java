package configuration.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import configuration.basic.config.EnvironmentConfig;

@SpringBootTest
@ActiveProfiles("test2")
class ProfilesIntegrationTest {

	@Autowired
	private EnvironmentConfig environmentConfig;

	@Test
	void test() {
		environmentConfig.someMethod();
	}

}
