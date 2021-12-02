package configuration.beans;

import org.springframework.context.annotation.Bean;

public class BeanForTest {

	@Bean
	public String beanForTests() {
		return "Use on tests only";
	}

}
