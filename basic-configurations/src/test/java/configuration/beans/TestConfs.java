package configuration.beans;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfs {

	@Bean
	public String moreOneBean() {
		return "More one bean for tests";
	}

}
