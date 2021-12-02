package integracao.rest.agenda;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

	private String nome = "Chefe";

	private String ddd = "0y";

	private String telefone = "9xxxxxxx9";

	@Test
	public void salvarContatoDeveRetornarMensagemDeErro() {
		Contato contato = new Contato(nome, null, null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<List<String>> resposta = 
				testRestTemplate.exchange("/agenda/inserir",HttpMethod.POST,httpEntity
						, new ParameterizedTypeReference<List<String>>() {});
		
		Assert.assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		Assert.assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		Assert.assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void inserirDeveSalvarContato() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = 
				testRestTemplate.exchange("/agenda/inserir",HttpMethod.POST,httpEntity, Contato.class);
		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		Assert.assertNotNull(resultado.getId());
		Assert.assertEquals(contato.getNome(), resultado.getNome());
		Assert.assertEquals(contato.getDdd(), resultado.getDdd());
		Assert.assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void inserirDeveSalvarContatoComPostForEntity() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = 
				testRestTemplate.postForEntity("/agenda/inserir",httpEntity, Contato.class);
		
		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
		Contato resultado = resposta.getBody();
		Assert.assertNotNull(resultado.getId());
		Assert.assertEquals(contato.getNome(), resultado.getNome());
		Assert.assertEquals(contato.getDdd(), resultado.getDdd());
		Assert.assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void inserirContatoDeveSalvarContatoPostForObject() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		Contato resposta = testRestTemplate.postForObject("/agenda/inserir",httpEntity, Contato.class);

		Assert.assertNotNull(resposta.getId());
		Assert.assertEquals(contato.getNome(), resposta.getNome());
		Assert.assertEquals(contato.getDdd(), resposta.getDdd());
		Assert.assertEquals(contato.getTelefone(), resposta.getTelefone());
		contatoRepository.deleteAll();
	}

}