package br.com.livraria.test;

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

import br.com.livraria.DAO.JPAUtil;
import br.com.livraria.modelo.Cliente;
import br.com.livraria.modelo.Compra;
import br.com.livraria.modelo.Livro;

public class TestMainNew {

	public static void main(String[] args) {

		EntityManager em = new JPAUtil().getLivrariaEntityManager();

		Livro livro1 = em.getReference(Livro.class, 16);
		Livro livro2 = em.getReference(Livro.class, 17);
		List<Livro> livros = new ArrayList<>();
		livros.add(livro1);
		livros.add(livro2);

		CriteriaBuilder cBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cQuery = cBuilder.createQuery(Cliente.class);

		// SELECT c.cliente FROM Compra c join c.livros l WHERE l = :pLivro

		List<Cliente> clientes = new ArrayList<>();
		List<Cliente> clientesComLivro = new ArrayList<>();
		
		Root<Compra> fromCompra = cQuery.from(Compra.class);
		Join<Compra, Livro> joinLivro = fromCompra.join("livros", JoinType.INNER);

		for (Livro livro : livros) {

			Predicate livroEqual = cBuilder.equal(joinLivro, livro);

			if (clientes.isEmpty()) {
				cQuery.select(fromCompra.get("cliente")).where(livroEqual).distinct(true);
				TypedQuery<Cliente> typedQuery = em.createQuery(cQuery);
				clientes = typedQuery.getResultList();
			} else {
				Path<Cliente> clientePath = fromCompra.get("cliente");
				Predicate predicates = cBuilder.conjunction();
				for (Cliente cliente : clientes) {
					Predicate clienteEqual = cBuilder.equal(clientePath, cliente);
					predicates = cBuilder.and(livroEqual, clienteEqual);

					cQuery.select(fromCompra.get("cliente")).where(predicates).distinct(true);

					TypedQuery<Cliente> typedQuery = em.createQuery(cQuery);
			
					clientesComLivro.add(typedQuery.getSingleResult());
				}
				
				if (clientesComLivro.isEmpty())
					clientes = clientesComLivro;

			}

			if (clientes.isEmpty())
				break;
		}

		if (clientes.isEmpty()) {
			System.out.println("Nenhum cliente encontrado!");
		} else {
			for (Cliente cliente : clientes) {
				System.out.println(cliente);
			}
		}

	}

}
