package integration.test.mvc;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CustomDetailService {

	@Bean
	public UserDetailsService customUserDetailsService() {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		User user = new User(
				"user",
				passwordEncoder.encode("user"),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

		User admin = new User(
				"admin",
				passwordEncoder.encode("admin"),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		return new InMemoryUserDetailsManager(user, admin);
	}

}
