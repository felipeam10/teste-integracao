package br.com.felipe.leilao.dao;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import br.com.felipe.leilao.model.Usuario;
import br.com.felipe.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private UsuarioDao dao;
	
	@Test
	void testeBuscaPeloUsuarioDeUsername() {
		EntityManager em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em); //para nao dar NPE temos que instanciar a classe DAO no teste. temos que passar um em como parametro para simular a injecao de dependencias
		
		Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		Usuario usuarioEncontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(usuarioEncontrado);
	}

}
