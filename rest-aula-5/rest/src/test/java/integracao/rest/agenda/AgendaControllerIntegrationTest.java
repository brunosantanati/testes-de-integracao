package integracao.rest.agenda;

import java.util.List;
import java.util.Optional;

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
	public void alterarDeveRetornarMensagemDeErro() {
		contato.setDdd(null);
		contato.setTelefone(null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<List<String>> resposta = 
				testRestTemplate.exchange("/agenda/alterar/{id}",HttpMethod.PUT
						,httpEntity, new ParameterizedTypeReference<List<String>>() {},contato.getId());
		
		Assert.assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		Assert.assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		Assert.assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void alterarDeveAlterarContato() {
		contato.setNome("Novo Chefe");
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = 
				testRestTemplate.exchange("/agenda/alterar/{id}",HttpMethod.PUT,httpEntity
						, Contato.class,contato.getId());
		
		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
		Contato resultado = resposta.getBody(); 
		Assert.assertEquals(contato.getId(), resultado.getId());
		Assert.assertEquals(ddd, resultado.getDdd());
		Assert.assertEquals(telefone, resultado.getTelefone());
		Assert.assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void alterarDeveAlterarContatoComPut() {
		contato.setNome("Novo Chefe");
		testRestTemplate.put("/agenda/alterar/{id}",contato,contato.getId());

		Contato resultado = contatoRepository.findById(contato.getId()).get();
		Assert.assertEquals(ddd, resultado.getDdd());
		Assert.assertEquals(telefone, resultado.getTelefone());
		Assert.assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void removerDeveExcluirContato() {
		ResponseEntity<Contato> resposta = 
				testRestTemplate.exchange("/agenda/remover/{id}",HttpMethod.DELETE,null
						, Contato.class,contato.getId());
		
		Assert.assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

	@Test
	public void removerDeveExcluirContatoComDelete() {
		testRestTemplate.delete("/agenda/remover/"+contato.getId());
		
		Optional<Contato> resultado = contatoRepository.findById(contato.getId());
		Assert.assertEquals(Optional.empty(), resultado);
	}

}