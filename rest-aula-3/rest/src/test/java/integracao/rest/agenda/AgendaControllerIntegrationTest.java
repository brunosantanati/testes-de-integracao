package integracao.rest.agenda;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.rest.contatos.Contato;
import integracao.rest.contatos.ContatoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgendaControllerIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	private String nome = "Chefe";

	private String ddd = "0y";

	private String telefone = "9xxxxxxx9";

	@Before
	public void start() {
		contato = new Contato(nome, ddd, telefone);
		contatoRepository.save(contato);
	}

	@After
	public void end() {
		contatoRepository.deleteAll();
	}

	@Test
	public void deveMostrarUmContatoComGetForEntity() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,contato.getId());

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		Assert.assertEquals(contato, resposta.getBody());
	}

	@Test
	public void deveMostrarUmContatoComGetForObject() {
		Contato resposta =
				testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class,contato.getId());
		Assert.assertEquals(contato, resposta);
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradoComGetForEntity() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,100);

		Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradogetForObject() {
		Contato resposta = testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class,100);
		Assert.assertNull(resposta);
	}

}