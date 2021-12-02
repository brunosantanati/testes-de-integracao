package integration.test.mvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import integration.test.mvc.cadidate.Candidate;

@SpringBootTest
@AutoConfigureMockMvc
class MvcIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenAnonymousUserAccessMainPageWithoutCandidates() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(content().string(containsString("<h2>No candidates yet!</h2>")))
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(content().encoding("UTF-8"))
		.andExpect(model().attribute("candidates", empty()))
		.andExpect(model().attribute("userName", emptyString()))
		.andExpect(view().name("index"))
		.andExpect(status().isOk());
	}

	@Test
	@Sql(statements = "INSERT INTO candidate (id, name, score) VALUES (1, 'Mark', 5)")
	@Sql(statements = "INSERT INTO candidate (id, name, score) VALUES (2, 'Bolt', 5)")
	@Sql(statements = "DELETE FROM candidate", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void givenAnonymousUserAccessMainPageWithCandidates() throws Exception {
		Candidate mark = new Candidate(1, "Mark", 5);

		mockMvc.perform(get("/"))
		.andExpect(model().attribute("candidates", hasSize(2)))
		.andExpect(model().attribute("candidates", hasItem(mark)))
		.andExpect(model().attribute("candidates", contains(new Candidate(2, "Bolt", 5), mark)))
		.andExpect(model().attribute("userName", emptyString()))
		.andExpect(content().string(stringContainsInOrder(Arrays.asList("Bolt", "Mark"))))
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(content().encoding("UTF-8"))
		.andExpect(view().name("index"))
		.andExpect(status().isOk());
	}

}
