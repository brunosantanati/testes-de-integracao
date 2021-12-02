package integration.test.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import integration.test.mvc.config.SecConfiguration;

@SpringBootTest(classes = {SecConfiguration.class})
@AutoConfigureMockMvc
class UserSimulationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithUserDetails("seth")
	public void withUserDetails() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(content().string(Matchers.containsString("<button type=\"button\" id=\"logout-btn\" class=\"btn btn-danger\">Logout</button>")));
	}

	@Test
	@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
	public void customUserDetailsService() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(content().string(Matchers.containsString("<button type=\"button\" id=\"logout-btn\" class=\"btn btn-danger\">Logout</button>")));
	}

	@Test
	@WithMockUser(username = "withMockUser", password = "pass", roles = {"ADMIN"})
	public void withMockUser() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(content().string(Matchers.containsString("<button type=\"button\" id=\"logout-btn\" class=\"btn btn-danger\">Logout</button>")));
	}

	@Test
	public void withMockMvc() throws Exception {
		mockMvc.perform(get("/")
				.with(SecurityMockMvcRequestPostProcessors
						.user("mockMvcUser")
						.password("pass")
						.roles("ADMIN"))
				)
		.andExpect(content().string(Matchers.containsString("<button type=\"button\" id=\"logout-btn\" class=\"btn btn-danger\">Logout</button>")));
	}

}
