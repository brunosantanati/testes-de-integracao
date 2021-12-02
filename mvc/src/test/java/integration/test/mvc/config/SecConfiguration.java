package integration.test.mvc.config;

import java.util.Collections;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class SecConfiguration {

	@Bean
	@Primary
	public UserDetailsService userDetailService() {

		User user = new User(
				"seth",
				"password",
				Collections.singletonList(
						new SimpleGrantedAuthority("ROLE_ADMIN")
						)
				);
		return new InMemoryUserDetailsManager(user);
	}

}
