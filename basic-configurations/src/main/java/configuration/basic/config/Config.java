package configuration.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class Config {

	@Bean
	@Profile("test")
	public EnvironmentConfig forTest() {
		return new EnvironmentConfig("I am in test");
	}

	@Bean
	@Profile("!test")
	public EnvironmentConfig forProduction() {
		return new EnvironmentConfig("I am in production");
	}

}
