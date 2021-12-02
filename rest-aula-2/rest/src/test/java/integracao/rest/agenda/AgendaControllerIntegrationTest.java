package integracao.rest.agenda;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
	public void deveMostrarTodosContatosUsandoString() {
		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/agenda/",HttpMethod.GET,null, String.class);

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));

		String result = "[{\"id\":"+contato.getId()+",\"ddd\":\"0y\","
				+ "\"telefone\":\"9xxxxxxx9\",\"nome\":\"Chefe\"}]";
		Assert.assertEquals(result, resposta.getBody());
	}

	@Test
	public void deveMostrarTodosContatosUsandoList() {
		ParameterizedTypeReference<List<Contato>> tipoRetorno =
				new ParameterizedTypeReference<List<Contato>>() {};

				ResponseEntity<List<Contato>> resposta =
						testRestTemplate.exchange("/agenda/",HttpMethod.GET,null, tipoRetorno);

				Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
				Assert.assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
				Assert.assertEquals(1, resposta.getBody().size());
				Assert.assertEquals(contato, resposta.getBody().get(0));
	}

	@Test
	public void deveMostrarUmContato() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/contato/{id}",HttpMethod.GET,null
						, Contato.class,contato.getId() );

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON));
		Assert.assertEquals(contato, resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontrado() {

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/contato/{id}",HttpMethod.GET,null, Contato.class,100 );

		Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}


}