package br.com.livraria.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.livraria.modelo.Cliente;
import br.com.livraria.modelo.Compra;
import br.com.livraria.modelo.Livro;

public class ClienteDAO extends DAOImp<Cliente> {

	public ClienteDAO(EntityManager em) {
		super(em);
	}

	public List<Cliente> filterByLivros(List<Livro> livros) {

		Livro livro1 = super.em.getReference(Livro.class, 16);
		
		CriteriaBuilder cBuilder = super.em.getCriteriaBuilder();
		CriteriaQuery<Compra> cQuery = cBuilder.createQuery(Compra.class);
	
		//SELECT c.cliente FROM Compra c join c.livros l WHERE l = :pLivro
		
		Root<Compra> fromCompra = cQuery.from(Compra.class);
		Join<Compra, Livro> joinLivro = fromCompra.join("livros", JoinType.INNER);
		Predicate livroEqual = cBuilder.equal(joinLivro, livro1);
		cQuery.multiselect(fromCompra, joinLivro).where(livroEqual);
		
		
		
		
		//cQuery.select(fromCompra)
		
		return null;
	}

}
