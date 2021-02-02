package br.com.felipe.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.felipe.leilao.model.Usuario;

@Repository
public class UsuarioDao {

	//@PersistenceContext no teste nao podemos contar com a injecao de dependencias. Ao inves de passar o @PersistenceContext no atributo
	//vamos alterar para receber este atributo como um parâmetro do construtor. 
	private EntityManager em;
	
	@Autowired //para funcionar na aplicacao temos que usar o @Autowired do sprint para que nao altere o funcionamento
	public UsuarioDao(EntityManager em) { //este construtor ja recebe o EntityManager como parametro 
		this.em = em;
	}

	public Usuario buscarPorUsername(String username) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :username", Usuario.class)
				.setParameter("username", username)
				.getSingleResult();
	}

	public void deletar(Usuario usuario) {
		em.remove(usuario);
	}

}
