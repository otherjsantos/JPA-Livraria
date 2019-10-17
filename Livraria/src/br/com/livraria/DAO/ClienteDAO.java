package br.com.livraria.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
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
		
		CriteriaBuilder cBuilder = super.em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cQuery = cBuilder.createQuery(Cliente.class);
		
		Root<Compra> fromCompra = cQuery.from(Compra.class);
		Join<Compra, Livro> joinLivro = fromCompra.join("livros", JoinType.INNER);
		
		List<Cliente> clientes = new ArrayList<>();
		List<Cliente> clientesComLivro = new ArrayList<>();
		
		for (Livro livro : livros) {
			
			Predicate livroEqual = cBuilder.equal(joinLivro, livro);
			
			if (clientes.isEmpty()) {
				
				cQuery.select(fromCompra.get("cliente")).where(livroEqual).distinct(true);
				TypedQuery<Cliente> typedQuery = super.em.createQuery(cQuery);
				clientes = typedQuery.getResultList();
				
			} else {
				
				Path<Cliente> clientePath = fromCompra.get("cliente");
				Predicate predicates = cBuilder.conjunction();
				
				for (Cliente cliente : clientes) {
					
					Predicate clienteEqual = cBuilder.equal(clientePath, cliente);
					predicates = cBuilder.and(livroEqual, clienteEqual);
					
					cQuery.select(clientePath).where(predicates);
					TypedQuery<Cliente> typedQuery = super.em.createQuery(cQuery);
					
					try {
						clientesComLivro.add(typedQuery.getSingleResult());
					} catch (Exception e) {
						continue;
					}
				}
				if (clientesComLivro.isEmpty()) {
					clientes.clear();
				} else {
					clientes.clear();
					clientes.addAll(clientesComLivro);
					clientesComLivro.clear();
				}
			}
			if (clientes.isEmpty())
				break;
		}
		return clientes;
	}
}
