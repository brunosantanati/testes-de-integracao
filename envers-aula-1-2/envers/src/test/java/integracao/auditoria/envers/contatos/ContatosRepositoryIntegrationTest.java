package integracao.auditoria.envers.contatos;


import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContatosRepositoryIntegrationTest {

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private EntityManagerFactory entityManager;

	private Contato contato;

	@Before
	public void start() {
		contato = new Contato("Chefe", "0y", "9xxxxxxx9");
	}

	@Test
	public void auditoriaAposSalvarRetornaObjetoCorreto() {
		contatoRepository.save(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		Contato contatoVindoDaAuditoria = reader.find(Contato.class, contato.getId(), new Date());

		Assert.assertTrue("Chefe".equals(contatoVindoDaAuditoria.getNome()));
		Assert.assertEquals(contato,contatoVindoDaAuditoria);

	}

	@Test
	public void auditoriaAposSalvarRetornaObjetoCorretoComAuditQuery() {
		contatoRepository.save(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		AuditQuery query = reader.createQuery()
				.forRevisionsOfEntity(Contato.class, true, true)
				.add(AuditEntity.property("id").eq(contato.getId()))
				.add(AuditEntity.revisionType().eq(RevisionType.ADD));

		Contato contatoVindoDaAuditoria = (Contato) query.getSingleResult();

		Assert.assertTrue("Chefe".equals(contatoVindoDaAuditoria.getNome()));
		Assert.assertEquals(contato,contatoVindoDaAuditoria);

	}

	@Test
	public void auditoriaAposEdicaoRetornaObjetoCorreto() {
		contatoRepository.save(contato);

		contato.setNome("Novo Chefe");
		contatoRepository.save(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		Contato contatoVindoDaAuditoria = reader.find(Contato.class, contato.getId(), new Date());

		Assert.assertTrue("Novo Chefe".equals(contatoVindoDaAuditoria.getNome()));

	}

	@Test
	public void auditoriaAposEdicaoRetornaObjetoCorretoComAuditQuery() {
		contatoRepository.save(contato);

		contato.setNome("Novo Chefe");
		contatoRepository.save(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		AuditQuery query = reader.createQuery()
				.forRevisionsOfEntity(Contato.class, true, true)
				.add(AuditEntity.property("id").eq(contato.getId()))
				.add(AuditEntity.revisionType().eq(RevisionType.MOD));

		Contato contatoVindoDaAuditoria = (Contato) query.getSingleResult();

		Assert.assertTrue("Novo Chefe".equals(contatoVindoDaAuditoria.getNome()));

	}

	@Test
	public void auditoriaAposExclusaoRetornaObjetoCorreto() {
		contatoRepository.save(contato);
		contatoRepository.delete(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		List<Number> numerosDasRevisoes = reader.getRevisions(Contato.class, contato.getId());
		Number number = numerosDasRevisoes.get(numerosDasRevisoes.size()-1);

		Contato contatoVindoDaAuditoria = reader.find(Contato.class,Contato.class.getName(), contato.getId(), number,true);

		Assert.assertEquals(contato.getId(), contatoVindoDaAuditoria.getId());
		Assert.assertNull(contatoVindoDaAuditoria.getNome());

	}

	@Test
	public void auditoriaAposExclusaoRetornaObjetoCorretoComAuditQuery() {
		contatoRepository.save(contato);
		contatoRepository.delete(contato);

		AuditReader reader = AuditReaderFactory.get(entityManager.createEntityManager());

		AuditQuery query = reader.createQuery()
				.forRevisionsOfEntity(Contato.class, true, true)
				.add(AuditEntity.property("id").eq(contato.getId()))
				.add(AuditEntity.revisionType().eq(RevisionType.DEL));

		Contato contatoVindoDaAuditoria = (Contato) query.getSingleResult();

		Assert.assertEquals(contato.getId(), contatoVindoDaAuditoria.getId());
		Assert.assertNull(contatoVindoDaAuditoria.getNome());

	}


}