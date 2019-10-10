package br.com.livraria.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.livraria.DAO.JPAUtil;
import br.com.livraria.modelo.Cliente;

public class testMain {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getLivrariaEntityManager();

		String clienteNome = "";
		String clienteCadastro = "";

		CriteriaBuilder cBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cQuery = cBuilder.createQuery(Cliente.class);

		Root<Cliente> mainPath = cQuery.from(Cliente.class);
		
		if (clienteNome.isEmpty() && clienteCadastro.isEmpty()) {
			cQuery.select(mainPath);
		} else {
			Predicate predicates = cBuilder.conjunction();
			if (!clienteNome.isEmpty()) {
				Path<String> nomePath = mainPath.get("nome");
				Predicate nomeLike = cBuilder.like(nomePath, "%" + clienteNome + "%");
				predicates = cBuilder.and(predicates, nomeLike);
			}
			if (!clienteCadastro.isEmpty()) {
				Path<String> cadastroPath = mainPath.get("cadastro");
				Predicate cadastroEqual = cBuilder.equal(cadastroPath, clienteCadastro);
				predicates = cBuilder.and(predicates, cadastroEqual);
			}
			cQuery.select(mainPath).where(predicates);
		}
		TypedQuery<Cliente> tQuery = em.createQuery(cQuery);
		List<Cliente> clientes = tQuery.getResultList();

		for (Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}
}
