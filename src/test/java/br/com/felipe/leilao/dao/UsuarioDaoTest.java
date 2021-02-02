package br.com.felipe.leilao.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.felipe.leilao.model.Usuario;
import br.com.felipe.leilao.util.JPAUtil;
import br.com.felipe.leilao.util.builder.UsuarioBuilder;

class UsuarioDaoTest {

	private UsuarioDao dao;
	private EntityManager em;
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em); //para nao dar NPE temos que instanciar a classe DAO no teste. temos que passar um em como parametro para simular a injecao de dependencias
		em.getTransaction().begin();
	
	}
	
	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}
	
	@Test
	void deveriaEncontrarUsuarioCadastrado() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@enail.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		
		Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);
	}
	
	
	@Test
	void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@enail.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
	}
	
	@Test
	void deveriaRemoverUmUsuario() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@enail.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		dao.deletar(usuario);
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
	}

}
